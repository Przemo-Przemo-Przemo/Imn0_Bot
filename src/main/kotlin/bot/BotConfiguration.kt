package bot

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class BotConfiguration(
    // Dirty Secrets :>
    @Value("\${application.discord.botToken}") val discordToken: String,
    @Value("\${application.tweetpik.apiKey}") val tweetpikApiKey: String,
    @Value("\${application.twitch.clientSecret}") val twitchClientSecret: String,
    @Value("\${application.twitch.clientId}") val twitchClientId: String,
    @Value("\${application.twitter.apikey}") val twitterApiKey: String,

    // Configuration variables
    @Value("\${application.discord.mainGuildId}") val mainGuildId: String
)