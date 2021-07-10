import commands.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Font
import java.awt.GraphicsEnvironment
import java.io.File

fun main() {
    Bot().main()
}

class Bot : ListenerAdapter() {
    val prefix = "XD?"
    var data : HashMap<String, ICommand> = HashMap()

    fun main() {
        var token = "ODU3MzM4Mjk0Mjc4MTYwNDM0.YNOIgQ.R2HqdtEVIkVWZfOufp64NsLk6Z0"
        var builder = JDABuilder.createLight(token)
            .setActivity(Activity.playing("prefix - $prefix"))

        startup()
        initializeFonts()

        builder.addEventListeners(this)
        builder.build()
    }

    fun startup() {
        data["pong"] = CommandPong()
        data["get"] = CommandGet()
        data["screenshoots"] = CommandScreenshoots()
        data["clips"] = CommandClips()
        data["invitation"] = CommandInvitation()
    }

    fun initializeFonts() {
        var lobster = Font.createFont(Font.TRUETYPE_FONT, File("D:\\Lobster-Regular.ttf"))
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(lobster)
    }
    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        GlobalScope.launch { onGuildMessageReceivedAsync(event) }
    }

    private suspend fun onGuildMessageReceivedAsync(event: GuildMessageReceivedEvent) {
        var message = event.message
        var author = message.author

        if(author.isBot) return

        if(message.contentRaw.startsWith(prefix)) {
            var prefixWithCommand = message.contentRaw.split(' ')[0]
            var commandName = prefixWithCommand.substringAfterLast(prefix)

            var command = data[commandName]

            if(command == null) {
                event.channel.sendMessage("Taka komenda nie istnieje XDDDDDDD???").queue()
            }

            else {
                command.run(event)
            }
        }
    }
}