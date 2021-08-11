package bot.models

data class TweetsJson(var data: List<Tweet>? = null, var meta: Meta? = null)
data class Tweet(var id: String? = null, var text: String? = null)
data class Meta(var oldest_id: String? = null, var newest_id: String? = null, var result_count: Int? = null, var next_token: String? = null)
