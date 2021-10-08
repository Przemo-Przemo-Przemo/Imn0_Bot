package bot.commands

import bot.models.Tweet
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

        val tweets: List<Tweet>
        try {
            tweets = twitterRepository.getTweets(username, numberOfTweets)
        }

        catch(exception: Exception) {
            channel.sendMessage("A network request exception was thrown: ${exception.message}").queue()
            return
        }

        if(tweets.isEmpty()) {
            channel.sendMessage("no tweets found XD?").queue()
            return
        }

        for(tweet in tweets) {
            channel.sendMessage("https://twitter.com/${username}/status/${tweet.id}").queue()
        }
    }
}

