package bot.repositories.api

import bot.BotConfiguration
import com.beust.klaxon.*
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.coroutines.awaitString
import kotlinx.coroutines.runBlocking
import bot.models.*
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import java.lang.NullPointerException

@Configuration
class TwitchRepository(private val config: BotConfiguration) {
    private var logger = LoggerFactory.getLogger(javaClass)
    private val fuel = FuelManager()

    init {
        runBlocking {
            val clientId = config.twitterClientId
            val clientSecret = config.twitterClientSecret

            val appAccessToken = getAppAccessToken(clientId, clientSecret)

            fuel.baseHeaders = mapOf(
                "Authorization" to "Bearer $appAccessToken",
                "Client-Id" to clientId
            )
        }
    }

    suspend fun getAppAccessToken(clientId: String, clientSecret: String): String {
        val params = listOf(
            "client_id" to clientId,
            "client_secret" to clientSecret,
            "grant_type" to "client_credentials"
        )

        val appAccessTokenJson =
                fuel.post("https://id.twitch.tv/oauth2/token", params)
                    .awaitString()

        val parser = Parser.default()
        val accessTokenJsonObject = parser.parse(appAccessTokenJson.reader()) as JsonObject
        val appAccessToken = accessTokenJsonObject["access_token"]
            ?: throw NullPointerException("Returned App Access Token is Null")

        return appAccessToken as String
    }

    suspend fun getClips(broadcasterId: String): List<TwitchClip>  {
        var clips = Klaxon().parse<TwitchClipsJson>(
            fuel.get("https://api.twitch.tv/helix/clips", listOf("broadcaster_id" to broadcasterId))
                .awaitString()
        ) ?: throw NullPointerException("Clips are null")

        return clips.data
    }

    suspend fun getUser(username: String): TwitchUser {
        var user = Klaxon().parse<TwitchUserJson>(
            fuel.get("https://api.twitch.tv/helix/users", listOf("login" to username))
                .awaitString()
        ) ?: throw NullPointerException("UserId is null")

        return user.data.first()
    }
}