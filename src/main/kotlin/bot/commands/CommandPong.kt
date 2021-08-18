package bot.commands

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.File

@Component
class CommandPong : Command() {
    override val name = "pong"

//    override suspend fun run(event: Event) {
//        var event = event as GuildMessageReceivedEvent
//        var channel = event.channel
//
//        execute(channel, listOf())
//    }

    override suspend fun execute(channel: TextChannel, args: List<String>) {
        var file = File("C:\\Users\\pysiak\\Downloads\\imno.jpg")
        channel.sendFile(file).queue()
    }
}
