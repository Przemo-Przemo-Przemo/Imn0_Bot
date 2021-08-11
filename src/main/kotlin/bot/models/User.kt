package bot.models

data class TwitterUserJson(val data: TwitterUser)
data class TwitterUser(val id: String, val name: String, val username: String)