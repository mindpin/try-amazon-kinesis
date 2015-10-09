require 'aws-sdk'
require File.expand_path("../../lib/constant", __FILE__)

def get_partition_key(num)
   return "even" if num % 2 == 0
   return "odd"
end

kinesis = Aws::Kinesis::Client.new(:region => REGIN)

0.upto(100000) do |num|
  r = kinesis.put_record(:stream_name => STREAM_NAME,
                          :data => num.to_s,
                          :partition_key => get_partition_key(num)
                          )

  puts "Put record to shard '#{r[:shard_id]}' (#{r[:sequence_number]}): '#{num}'"
  sleep 1
end
