package bot

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class BotConfiguration(
    // Secrets
    @Value("\${application.discord.botToken}") val discordToken: String,
    @Value("\${application.tweetpik.apiKey}") val tweetpikApiKey: String,
    @Value("\${application.twitter.clientSecret}") val twitterClientSecret: String,
    @Value("\${application.twitter.clientId}") val twitterClientId: String,
    @Value("\${application.twitter.apikey}") val twitterApiKey: String,

    // Configuration variables
    // TODO: Add some parameters to customize the behavior of the bot.
    //      E.g. delay between sending tweets or id of the channel where it will send them
)