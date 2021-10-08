package bot.listeners

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component

@Component
class ImnoWordInMessageListener : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        val content = event.message.contentDisplay

        if(containsImno(content)) {
            event.channel.sendMessage("O co chodzi?").queue()
        }
    }

    private fun containsImno(string: String): Boolean {
        val imnoVariants = listOf("ImnO", "Imn0")

        for(imnoVariant in imnoVariants) {
            if (string.contains(imnoVariant, ignoreCase = true)) {
                return true
            }
        }

        return false
    }
}