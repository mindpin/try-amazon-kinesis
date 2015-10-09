
require 'aws-sdk'
require File.expand_path("../../lib/constant", __FILE__)
require File.expand_path("../../lib/shard_utils", __FILE__)

client = Aws::Kinesis::Client.new(:region => REGIN)
shards = ShardUtils.get_shards(client, STREAM_NAME)

shards.each do |shard|
  ShardUtils.shard_info(shard)
end
