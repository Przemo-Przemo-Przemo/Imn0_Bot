package bot.commands

import bot.models.Tweet
import bot.models.TweetPik
import bot.models.TweetsJson
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import bot.repositories.api.TweetPikRepository
import bot.repositories.api.TwitterRepository
import net.dv8tion.jda.api.entities.TextChannel
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CommandScreenshoots: Command() {
    override val name = "screenshoots" // not to be mistaken with screenshots. It's a feature, not a bug (CHWALIK_ADMIN)

    @Autowired
    lateinit var twitterRepository: TwitterRepository
    @Autowired
    lateinit var tweetPikRepository: TweetPikRepository

    private val logger = LoggerFactory.getLogger(javaClass)

    override suspend fun execute(channel: TextChannel, args: List<String>) {
        val username = args[0]
        val numberOfTweets = args[1].toInt()

        val tweets: List<Tweet>
        try {
            tweets = twitterRepository.getTweets(username, numberOfTweets)
        } catch(exception: Exception) {
            logger.error(exception.message)
            channel.sendMessage("API IS SUS XDDDDD").queue()
            return
        }

        if(tweets.isEmpty()) {
            channel.sendMessage("no tweets found XD?").queue()
            return
        }

        for(tweet in tweets) {
            var tweetPik: TweetPik

            try {
                tweetPik = tweetPikRepository.getTweetPik(tweet)
            } catch(exception : Exception) {
                channel.sendMessage("A network request exception was thrown: ${exception.message}").queue()
                continue
            }

            channel.sendMessage(tweetPik.url).queue()
        }
    }
}