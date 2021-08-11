package bot.commands

import bot.models.TweetPik
import bot.models.TweetsJson
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import bot.repositories.api.TweetPikRepository
import bot.repositories.api.TwitterRepository
import net.dv8tion.jda.api.entities.TextChannel
import org.springframework.stereotype.Component

@Component
class CommandScreenshoots: Command() {
    override val name = "screenshoots" // not to be mistaken with screenshots. It's a feature, not a bug (CHWALIK_ADMIN)

    override suspend fun execute(channel: TextChannel, args: List<String>) {
        val username = args[0]
        val numberOfTweets = args[1].toInt()

        var tweets: TweetsJson
        try {
            tweets = TwitterRepository().getTweets(username, numberOfTweets)
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
                var tweetPik: TweetPik

                try {
                    tweetPik = TweetPikRepository().getTweetPik(tweet)
                }

                catch(exception : Exception) {
                    channel.sendMessage("A network request exception was thrown: ${exception.message}").queue()
                    continue
                }

                channel.sendMessage(tweetPik.url).queue() //temporary?
            }
        }
    }
}