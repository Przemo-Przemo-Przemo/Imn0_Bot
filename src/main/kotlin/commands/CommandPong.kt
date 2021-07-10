package commands

import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import java.io.File

class CommandPong : ICommand {
    override suspend fun run(event: Event) {
        var event = event as GuildMessageReceivedEvent
        var channel = event.channel

        var file = File("C:\\Users\\pysiak\\Downloads\\imno.jpg")
        channel.sendFile(file).queue()
    }
}