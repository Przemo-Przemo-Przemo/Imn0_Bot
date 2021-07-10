package commands

import com.beust.klaxon.Klaxon
import models.Tweet
import models.TweetsJson
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import repositories.TwitterRepository

class CommandGet : ICommand {
    override suspend fun run(event: Event) {
        val event = event as GuildMessageReceivedEvent
        val channel = event.channel

        val username = event.message.contentRaw.split(' ')[1]
        val numberOfTweets = event.message.contentRaw.split(' ')[2].toInt()

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
                channel.sendMessage("https://twitter.com/trigomemetry/status/${tweet.id}").queue() //temporary?
            }
        }
    }
}