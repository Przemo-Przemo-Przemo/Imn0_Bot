package bot.commands

import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import bot.repositories.api.TwitchRepository
import net.dv8tion.jda.api.entities.TextChannel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CommandClips : Command() {
    override val name = "clips"

    @Autowired
    lateinit var twitchRepository: TwitchRepository

    override suspend fun execute(channel: TextChannel, args: List<String>) {
        val username = args[0]

        val userId = twitchRepository.getUser(username).id
        val bestClip = twitchRepository.getClips(userId)[0]

        channel.sendMessage(bestClip.url).queue()
    }
}
