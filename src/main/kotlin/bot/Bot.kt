package bot

import bot.commands.*
import bot.repositories.db.IOProductsLinkNew
import bot.repositories.db.OProductsLinkNew
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.Scheduled
import java.awt.Font
import java.awt.GraphicsEnvironment
import java.io.File
import java.time.Instant
import javax.annotation.PostConstruct

fun main() {
    Bot().main()
}

@SpringBootApplication
@ConfigurationPropertiesScan
class Bot : ListenerAdapter() {
    val prefix = "XD?"
    var data : HashMap<String, Command> = HashMap()

    val log = LoggerFactory.getLogger(javaClass)

    lateinit var jda: JDA
    @Autowired
    lateinit var commands: List<Command>
    @Autowired
    lateinit var oProductsLinkNewRepository: IOProductsLinkNew;

    fun main() {
        runApplication<Bot>()
    }

    @PostConstruct
    fun startup() {
        var token = "ODU3MzM4Mjk0Mjc4MTYwNDM0.YNOIgQ.R2HqdtEVIkVWZfOufp64NsLk6Z0"
        var builder = JDABuilder.createLight(token)
            .setActivity(Activity.playing("prefix - $prefix"))

        initializeCommands()
        initializeFonts()
        scheduleRandomMessages()

        builder.addEventListeners(this)
        jda = builder.build().awaitReady()

        penisMusic()
    }

    /**
        schedules to mongo times when new random messages shall be sent
     */
    fun scheduleRandomMessages() {
        val min = 0
        val max = 9

        val count = max-min+1
        val timeRangeInSeconds = 100L

        for(i in min..max) {
            val toSave = OProductsLinkNew(null, Instant.now().plusSeconds((timeRangeInSeconds/count)*i), "pong")
            oProductsLinkNewRepository.save(toSave)
        }
    }

    @Scheduled(fixedRate = 5000)
    fun penisMusic() {
        log.info("Executing penis method...")
        var chwalik_admin_oTimesIntegratedReturnProductPageTenWhereOfferIdIsGreaterThanBKP_FIX566 = oProductsLinkNewRepository.findByTimeWhenToSendMessageBefore(Instant.now())

        for(time in chwalik_admin_oTimesIntegratedReturnProductPageTenWhereOfferIdIsGreaterThanBKP_FIX566) {
            runBlocking {
                val channel = jda.getTextChannelById(857395035237253131)
                if(channel != null) data[time.commandName]?.execute(channel, listOf())
            }
        }
    }

    fun initializeCommands() {
        for(command in commands) {
            data[command.name] = command
        }
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