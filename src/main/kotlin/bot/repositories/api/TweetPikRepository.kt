package bot.repositories.api

import bot.BotConfiguration
import com.beust.klaxon.Klaxon
import com.beust.klaxon.json
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.coroutines.awaitString
import bot.models.Tweet
import bot.models.TweetPik
import org.springframework.context.annotation.Configuration
import java.lang.NullPointerException

@Configuration
class TweetPikRepository(private val config: BotConfiguration) {
    private val fuel = FuelManager()

    init {
        val apiKey = config.tweetpikApiKey

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

        val tweetPik = Klaxon().parse<TweetPik>(
                fuel.post("https://tweetpik.com/api/images")
                    .body(json.toJsonString())
                    .awaitString()
        )

        if(tweetPik == null) throw NullPointerException("TweetPik is null")
        return tweetPik
    }
}