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
import java.lang.NullPointerException

@Configuration
class TweetPikRepository(
    @Value("\${application.tweetpik.apiKey}") var apiKey: String,
) {
    private val fuel = FuelManager()

    init {
        fuel.baseHeaders = mapOf(
            "Authorization" to apiKey,
            "Content-Type" to "application/json"
        )
    }

    suspend fun getTweetPik(tweet: Tweet): TweetPik {
        val tweetId = tweet.id

        val json = json { // TODO (NEVER): CHECK THIS SHIT          p. s. never teach primates javascript
            obj(
                "tweetId" to tweetId
            )
        }

        var tweetPik = Klaxon().parse<TweetPik>(
                fuel.post("https://tweetpik.com/api/images")
                    .body(json.toJsonString())
                    .awaitString()
        )

        if(tweetPik == null) throw NullPointerException("TweetPik is null")
        return tweetPik
    }
}