package repositories

import com.beust.klaxon.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.coroutines.awaitString
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import models.*

class TwitchRepository {
    private val clientSecret = "lyr1qt6sg1g7k2suuia4exbny4apc1"
    private val clientId = "kimv4iz3l99i23wj531dgwvqq68jcj"

    init {
        var appAccessToken: String = ""

        runBlocking {
            appAccessToken = getAppAccessToken()
        }

        FuelManager.instance.baseHeaders = mapOf("Authorization" to "Bearer $appAccessToken",
            "Client-Id" to "kimv4iz3l99i23wj531dgwvqq68jcj")
    }


    suspend fun getAppAccessToken(): String {
        var appAccessTokenJson =
            try {
                Fuel.post("https://id.twitch.tv/oauth2/token", listOf("client_id" to clientId, "client_secret" to clientSecret, "grant_type" to "client_credentials"))
                    .awaitString()
            } catch (exception: Exception) {
                throw exception
            }

        val appAccessToken = (Parser.default().parse(StringBuilder(appAccessTokenJson)) as JsonObject)["access_token"]
        if(appAccessToken == null) throw Exception("Returned App Access Token is Null")

        return appAccessToken as String
    }

    suspend fun getClips(broadcasterId: String): List<TwitchClip>  {
        var clips = Klaxon().parse<TwitchClipsJson>(try {
                Fuel.get("https://api.twitch.tv/helix/clips", listOf("broadcaster_id" to broadcasterId))
                    .awaitString()
            } catch (exception: Exception) {
                throw exception
            })

        if(clips == null) throw Exception("Clips are null")
        return clips.data
    }

    suspend fun getUser(username: String): TwitchUser {
        var user = Klaxon().parse<TwitchUserJson>(
            try {
                Fuel.get("https://api.twitch.tv/helix/users", listOf("login" to username))
                    .awaitString()
            } catch (exception: Exception) {
                throw exception
            })

        if(user == null) throw Exception("UserId is null")
        return user.data.first()
    }

}