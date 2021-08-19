package bot.repositories.api

import com.beust.klaxon.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.coroutines.awaitString
import kotlinx.coroutines.runBlocking
import bot.models.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class TwitchRepository(
    @Value("\${application.twitch.clientSecret}") private val clientSecret: String,
    @Value("\${application.twitch.clientId}") private val clientId: String
) {
    private var logger = LoggerFactory.getLogger(javaClass)
    private val fuel = FuelManager()

    init {
        var appAccessToken = ""

        runBlocking {
            appAccessToken = getAppAccessToken(clientId, clientSecret)
        }

        fuel.baseHeaders = mapOf( //todo: fix not remembered across instances
            "Authorization" to "Bearer $appAccessToken",
            "Client-Id" to clientId
        )
    }

    suspend fun getAppAccessToken(clientId: String, clientSecret: String): String {
        val appAccessTokenJson =
            try {
                fuel.post("https://id.twitch.tv/oauth2/token", listOf("client_id" to clientId, "client_secret" to clientSecret, "grant_type" to "client_credentials"))
                    .awaitString()
            } catch (exception: Exception) {
                throw exception
            }

        val appAccessToken = (Parser.default().parse(StringBuilder(appAccessTokenJson)) as JsonObject)["access_token"]
        if(appAccessToken == null) throw Exception("Returned App Access Token is Null")

        return appAccessToken as String
    }

    suspend fun getClips(broadcasterId: String): List<TwitchClip>  {
     //   setBaseHeaders()

        var clips = Klaxon().parse<TwitchClipsJson>(try {
            fuel.get("https://api.twitch.tv/helix/clips", listOf("broadcaster_id" to broadcasterId))
                    .awaitString()
            } catch (exception: Exception) {
                throw exception
            })

        if(clips == null) throw Exception("Clips are null")
        return clips.data
    }

    suspend fun getUser(username: String): TwitchUser {
   //     setBaseHeaders()
        val a = FuelManager.instance.baseHeaders
        var user = Klaxon().parse<TwitchUserJson>(
            try {
                fuel.get("https://api.twitch.tv/helix/users", listOf("login" to username))
                    .awaitString()
            } catch (exception: Exception) {
                throw exception
            })

        if(user == null) throw Exception("UserId is null")
        return user.data.first()
    }

}