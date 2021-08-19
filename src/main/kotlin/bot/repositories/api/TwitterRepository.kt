package bot.repositories.api

import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.coroutines.awaitString
import bot.models.TweetsJson
import bot.models.TwitterUserJson
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration
class TwitterRepository(
    @Value("\${application.twitter.apiKey}") apiKey: String
) {
    private val fuel = FuelManager()

    init {
        fuel.baseHeaders = mapOf("Authorization" to apiKey)
    }

    suspend fun getTweets(username: String, numberOfTweets: Int): TweetsJson {
        val user = getUser(username)
        val userId = user.data.id

        var tweets = Klaxon().parse<TweetsJson>(
            try {
                fuel.get("https://api.twitter.com/2/users/${userId}/tweets", listOf("max_results" to numberOfTweets))
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
                fuel.get("https://api.twitter.com/2/users/by/username/${username}").awaitString()
            } catch (exception: Exception) {
                throw exception
            }
        )

        if (user == null) throw Exception("User is null")
        return user
    }
}