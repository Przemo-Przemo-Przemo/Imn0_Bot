package models

data class TwitchUserJson(var data: List<TwitchUser>)
data class TwitchUser(var id: String)
data class TwitchClipsJson(var data: List<TwitchClip>)
data class TwitchClip(var id: String, var url: String, var title: String)