class ShardUtils
  class << self
    def get_shards(client, stream_name)
      shards = []
      has_more_shard = true
      exclusive_start_shard_id = nil

      while has_more_shard do
        resp = client.describe_stream({
          stream_name: stream_name,
          exclusive_start_shard_id: exclusive_start_shard_id
        })
        _shards = resp.stream_description.shards
        shards += _shards

        has_more_shard = resp.stream_description.has_more_shards
        exclusive_start_shard_id = _shards.last.shard_id
      end

      p "shares size: #{shards.count}"
      shards
    end

    def shard_info(shard)
      p "shard_id: #{shard.shard_id}"
      p "starting_hash_key: #{shard.hash_key_range.starting_hash_key}"
      p "ending_hash_key: #{shard.hash_key_range.ending_hash_key}"
    end
  end
end
