package bot.commands

import net.dv8tion.jda.api.entities.TextChannel
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.File

@Component
class CommandHelp : Command() {
    override val name = "help"
    private val log = LoggerFactory.getLogger(javaClass)

    override suspend fun execute(channel: TextChannel, args: List<String>) {
        val helpMessagePath = "/help.txt"
        val helpMessageUri = javaClass.getResource(helpMessagePath)?.toURI()

        if(helpMessageUri == null) {
            log.error("Resource ${helpMessagePath} not found")
            return
        }

        val file = File(helpMessageUri)
        channel.sendMessage(file.readText()).queue()
    }
}
