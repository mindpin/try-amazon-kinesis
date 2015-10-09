#! /usr/bin/env ruby
#
require 'aws/kclrb'
require 'base64'
require 'tmpdir'
require 'fileutils'

class RecordProcessor < Aws::KCLrb::RecordProcessorBase
  def initialize
    @close = false
    @output = $stdout
  end

  # (see Aws::KCLrb::RecordProcessorBase#init_processor)
  def init_processor(shard_id)
    @shard_id = shard_id
  end

  # (see Aws::KCLrb::RecordProcessorBase#process_records)
  def process_records(records, checkpointer)
    last_seq = nil
    records.each do |record|
      begin
        data = Base64.decode64(record['data'])
        @output.puts "process_record data: #{data} shard_id: #{@shard_id}"
        @output.flush
        last_seq = record['sequenceNumber']
      rescue => e
        # Make sure to handle all exceptions.
        # Anything you write to STDERR will simply be echoed by parent process
        STDERR.puts "#{e}: Failed to process record '#{record}'"
      end
    end
    checkpoint_helper(checkpointer, last_seq)  if last_seq
  end

  # (see Aws::KCLrb::RecordProcessorBase#shutdown)
  def shutdown(checkpointer, reason)
    checkpoint_helper(checkpointer)  if 'TERMINATE' == reason
  ensure
    # Make sure to cleanup state
    @output.close  if @close
  end

  private
  # Helper method that retries checkpointing once.
  # @param checkpointer [Aws::KCLrb::Checkpointer] The checkpointer instance to use.
  # @param sequence_number (see Aws::KCLrb::Checkpointer#checkpoint)
  def checkpoint_helper(checkpointer, sequence_number=nil)
    begin
      checkpointer.checkpoint(sequence_number)
    rescue Aws::KCLrb::CheckpointError => e
      # Here, we simply retry once.
      # More sophisticated retry logic is recommended.
      checkpointer.checkpoint(sequence_number) if sequence_number
    end
  end
end

if __FILE__ == $0
  # Start the main processing loop
  record_processor = RecordProcessor.new
  driver = Aws::KCLrb::KCLProcess.new(record_processor)
  driver.run
end
