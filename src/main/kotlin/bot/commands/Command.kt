package bot.commands

import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

abstract class Command {
    abstract val name: String

    open suspend fun run(event: Event) {
        var event = event as GuildMessageReceivedEvent
        var channel = event.channel

        var messageSplitBySpace = event.message.contentRaw.split(' ')
        var arguments = messageSplitBySpace.drop(1)

        execute(channel, arguments)
    }

    abstract suspend fun execute(channel: TextChannel, args: List<String>)
}