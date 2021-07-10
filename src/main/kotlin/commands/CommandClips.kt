package commands

import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import repositories.TwitchRepository

class CommandClips : ICommand {
    override suspend fun run(event: Event) {
        var event = event as GuildMessageReceivedEvent
        var channel = event.channel

        val username = event.message.contentRaw.split(' ')[1]

        val twitchRepository = TwitchRepository()
        val userId = twitchRepository.getUser(username).id
        val bestClip = twitchRepository.getClips(userId)[0]

        channel.sendMessage(bestClip.url).queue()
    }
}
