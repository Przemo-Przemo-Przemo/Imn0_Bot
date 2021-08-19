package bot.repositories.api

import com.beust.klaxon.Klaxon
import com.beust.klaxon.json
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.coroutines.awaitString
import bot.models.Tweet
import bot.models.TweetPik
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
class TweetPikRepository(
    @Value("\${application.tweetpik.apiKey}") var apiKey: String,
) {
    private val fuel = FuelManager()

    init {
        fuel.baseHeaders = mapOf("Authorization" to apiKey,
                                                 "Content-Type" to "application/json")
    }

    suspend fun getTweetPik(tweet: Tweet): TweetPik {
        val tweetId = tweet.id

        val json = json {
            obj(
                "tweetId" to tweetId
            )
        }

        var tweetPik = Klaxon().parse<TweetPik>(
            try {
                fuel.post("https://tweetpik.com/api/images")
                    .body(json.toJsonString())
                    .awaitString()
            } catch (exception: Exception) {
                throw exception
            }
        )

        if(tweetPik == null) throw Exception("TweetPik is null")
        return tweetPik
    }
}