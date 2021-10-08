package bot.commands

import bot.repositories.api.TwitchRepository
import net.dv8tion.jda.api.entities.TextChannel
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Component
class CommandClips : Command() {
    override val name = "clips"
    private var logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var twitchRepository: TwitchRepository

    override suspend fun execute(channel: TextChannel, args: List<String>) {
        val username = args[0]
        val startedAt = if(args.size >= 2) {
            try {
                //TODO: consider refactoring (never)
                //check if is date then add local time
                LocalDate.parse(args[1], DateTimeFormatter.ISO_LOCAL_DATE).toString() + "T00:00:00Z"
            }

            catch(ex: Exception) {
                logger.error(ex.message)
                channel.sendMessage("NIE WIESZ JAK SIE PISZE DATY XD?").queue()
                return
            }

        } else ""

        val userId = twitchRepository.getUser(username).id
        val bestClip = twitchRepository.getClips(userId, startedAt)[0]

        channel.sendMessage(bestClip.url).queue()
    }
}
