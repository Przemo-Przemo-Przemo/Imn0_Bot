package repositories

import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.coroutines.awaitString
import models.TweetsJson
import models.TwitterUserJson

class TwitterRepository {
    init {
        FuelManager.instance.baseHeaders = mapOf("Authorization" to "Bearer AAAAAAAAAAAAAAAAAAAAAITqQwEAAAAAGrEbxOEtPdn1C7yvmM6hxeBT%2F8s%3Daew59HgCnTeHDp5FrEuB28FOW3RSYrNseFJ6EGCX42F8qlJz1M")
    }

    suspend fun getTweets(username: String, numberOfTweets: Int): TweetsJson {
        val user = getUser(username)
        val userId = user.data.id

        var tweets = Klaxon().parse<TweetsJson>(
            try {
                Fuel.get("https://api.twitter.com/2/users/${userId}/tweets", listOf("max_results" to numberOfTweets))
                    .awaitString()
            } catch (exception: Exception) {
                throw exception
            }
        )

        if(tweets == null) throw Exception("Tweets are null")
        return tweets
    }

    private suspend fun getUser(username: String): TwitterUserJson {
        var user = Klaxon().parse<TwitterUserJson>(
            try {
                Fuel.get("https://api.twitter.com/2/users/by/username/${username}").awaitString()
            } catch (exception: Exception) {
                throw exception
            }
        )

        if (user == null) throw Exception("User is null")
        return user
    }
}