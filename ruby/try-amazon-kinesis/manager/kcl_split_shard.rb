
require 'aws-sdk'
require File.expand_path("../../lib/constant", __FILE__)
require File.expand_path("../../lib/shard_utils", __FILE__)

client = Aws::Kinesis::Client.new(:region => REGIN)

shards = ShardUtils.get_shards(client, STREAM_NAME)
resp = client.split_shard({
  stream_name: STREAM_NAME,
  shard_to_split: shards[0].shard_id,
  new_starting_hash_key: "221927652340938865172815364521841099064"
})
