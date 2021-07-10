package repositories

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.json
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.coroutines.awaitString
import models.Tweet
import models.TweetPik

class TweetPikRepository {
    init {
        FuelManager.instance.baseHeaders = mapOf("Authorization" to "002b6c10-de27-45e5-88b9-6c4f029e89cc",
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
                Fuel.post("https://tweetpik.com/api/images")
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