package bot.listeners

import bot.util.Algorithms
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component

@Component
class AmogusInMessageListener : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        val content = event.message.contentDisplay

        if(event.author.isBot || content.length > 16) {
            return
        }

        if(isSimilarToAmongus(content)) {
            event.message.reply("HAHA SUS AMOGUS ඞඞඞඞඞඞ").queue()
        }
    }

    private fun isSimilarToAmongus(string: String): Boolean {
        val editDistance = Algorithms.levenshtein(string, "among us")
        return editDistance < 5
    }
}