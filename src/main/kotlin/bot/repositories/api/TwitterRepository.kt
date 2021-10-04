package bot.repositories.api

import bot.BotConfiguration
import bot.models.Tweet
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.coroutines.awaitString
import bot.models.TweetsJson
import bot.models.TwitterUserJson
import org.springframework.context.annotation.Configuration
import java.lang.Integer.max
import java.lang.NullPointerException

@Configuration
class TwitterRepository(private val config: BotConfiguration) {
    private val fuel = FuelManager()

    init {
        val apiKey = config.twitterApiKey
        fuel.baseHeaders = mapOf("Authorization" to "Bearer $apiKey")
    }

    suspend fun getTweets(username: String, numberOfTweets: Int): List<Tweet> {
        val user = getUser(username)
        val userId = user.data.id

        //minimum tweets needed are 5, therefore
        val tweets = Klaxon().parse<TweetsJson>(
            fuel.get("https://api.twitter.com/2/users/${userId}/tweets", listOf("max_results" to max(numberOfTweets, 5)))
                .awaitString()
        ) ?: throw NullPointerException("Tweets are null")

        if(tweets.data == null) return emptyList()
        return tweets.data!!.take(numberOfTweets)
    }

    private suspend fun getUser(username: String): TwitterUserJson {
        var user = Klaxon().parse<TwitterUserJson>(
            fuel.get("https://api.twitter.com/2/users/by/username/${username}").awaitString()
        ) ?: throw NullPointerException("User is null")

        return user
    }
}