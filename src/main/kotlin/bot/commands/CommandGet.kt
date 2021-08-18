package bot.commands

import bot.models.TweetsJson
import bot.repositories.api.TwitchRepository
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import bot.repositories.api.TwitterRepository
import net.dv8tion.jda.api.entities.TextChannel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
@Component
class CommandGet : Command() {
    override val name = "get"

    @Autowired
    lateinit var twitterRepository: TwitterRepository

    override suspend fun execute(channel: TextChannel, args: List<String>) {
        val username = args[0]
        val numberOfTweets = args[1].toInt()

        var tweets: TweetsJson
        try {
            tweets = twitterRepository.getTweets(username, numberOfTweets)
        }

        catch(exception: Exception) {
            channel.sendMessage("A network request exception was thrown: ${exception.message}").queue()
            return
        }

        if (tweets != null) {
            if(tweets.meta?.result_count == 0) {
                channel.sendMessage("no tweets found XD?").queue()
                return
            }

            for(tweet in tweets.data!!) {
                channel.sendMessage("https://twitter.com/trigomemetry/status/${tweet.id}").queue() //temporary?
            }
        }
    }
}

