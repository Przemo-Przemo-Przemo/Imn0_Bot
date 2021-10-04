package bot.repositories.api

import bot.BotConfiguration
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.coroutines.awaitString
import bot.models.TweetsJson
import bot.models.TwitterUserJson
import org.springframework.context.annotation.Configuration
import java.lang.NullPointerException

@Configuration
class TwitterRepository(private val config: BotConfiguration) {
    private val fuel = FuelManager()

    init {
        val apiKey = config.twitterApiKey
        fuel.baseHeaders = mapOf("Authorization" to "Bearer $apiKey")
    }

    suspend fun getTweets(username: String, numberOfTweets: Int): TweetsJson {
        val user = getUser(username)
        val userId = user.data.id

        var tweets = Klaxon().parse<TweetsJson>(
            fuel.get("https://api.twitter.com/2/users/${userId}/tweets", listOf("max_results" to numberOfTweets))
                .awaitString()
        ) ?: throw NullPointerException("Tweets are null")

        return tweets
    }

    private suspend fun getUser(username: String): TwitterUserJson {
        var user = Klaxon().parse<TwitterUserJson>(
            fuel.get("https://api.twitter.com/2/users/by/username/${username}").awaitString()
        ) ?: throw NullPointerException("User is null")

        return user
    }
}