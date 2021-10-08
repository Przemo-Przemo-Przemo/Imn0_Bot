package bot.commands

import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

abstract class Command {
    abstract val name: String

    open suspend fun run(event: Event) {
        val event = event as GuildMessageReceivedEvent
        val channel = event.channel

        val messageSplitBySpace = event.message.contentRaw.split(' ')
        val arguments = messageSplitBySpace.drop(1)

        execute(channel, arguments)
    }

    abstract suspend fun execute(channel: TextChannel, args: List<String>)
}