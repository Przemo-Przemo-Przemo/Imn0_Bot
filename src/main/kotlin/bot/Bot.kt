package bot

import bot.commands.*
import bot.repositories.db.IOProductsLinkNew
import bot.repositories.db.OProductsLinkNew
import com.github.kittinunf.fuel.core.FuelManager
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
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.awt.Font
import java.awt.GraphicsEnvironment
import java.io.File
import java.time.Instant
import javax.annotation.PostConstruct
import kotlin.random.Random

fun main() {
    Bot().main()
}

@SpringBootApplication
@EnableScheduling
@ConfigurationPropertiesScan
class Bot : ListenerAdapter() {
    val prefix = "XD?"
    var data : HashMap<String, Command> = HashMap()

    val log = LoggerFactory.getLogger(javaClass)

    lateinit var jda: JDA
    @Autowired
    lateinit var commands: List<Command>
    @Autowired
    lateinit var oProductsLinkNewRepository: IOProductsLinkNew
    @Autowired
    lateinit var randomMessagesArgumentsGenerator: RandomMessagesArgumentsGenerator
    @Value("\${application.discord.botToken}") lateinit var botToken: String

    fun main() {
        runApplication<Bot>()
    }

    @PostConstruct
    fun startup() {
        val builder = JDABuilder.createLight(botToken)
            .setActivity(Activity.playing("prefix - $prefix"))

        initializeCommands()
        initializeFonts()
        scheduleRandomMessages()

        builder.addEventListeners(this)
        jda = builder.build().awaitReady()
    }

    /**
        schedules to mongo times when new random messages shall be sent
     */
    fun scheduleRandomMessages() {
        val count = 10
        val timeRangeInSeconds = 100L

        for(i in 0..count) {
            val randomCommandWithRandomArguments = randomMessagesArgumentsGenerator.randomCommandWithRandomArguments()
            val toSave = OProductsLinkNew(null, Instant.now().plusSeconds((timeRangeInSeconds/count)*i), randomCommandWithRandomArguments.first, randomCommandWithRandomArguments.second)
            oProductsLinkNewRepository.save(toSave)
        }
    }

    @Scheduled(fixedRate = 5000)
    fun chwalik_admin() {
        log.info("Executing chwalik_admin method...")
        val chwalik_admin_oTimesIntegratedReturnProductPageTenWhereOfferIdIsGreaterThanBKP_FIX566 = oProductsLinkNewRepository.findByTimeWhenToSendMessageBefore(Instant.now())

        for(time in chwalik_admin_oTimesIntegratedReturnProductPageTenWhereOfferIdIsGreaterThanBKP_FIX566) {
            runBlocking {
                val channel = jda.getTextChannelById(857395035237253131)
                if(channel != null) data[time.commandName]?.execute(channel, time.args)
                oProductsLinkNewRepository.deleteById(time.id)
            }
        }
   }

    fun initializeCommands() {
        for(command in commands) {
            data[command.name] = command
        }
    }

    fun initializeFonts() {
        val lobster = Font.createFont(Font.TRUETYPE_FONT, File("D:\\Lobster-Regular.ttf"))
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(lobster)
    }

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        GlobalScope.launch { onGuildMessageReceivedAsync(event) }
    }

    private suspend fun onGuildMessageReceivedAsync(event: GuildMessageReceivedEvent) {
        val message = event.message
        val author = message.author

        if(author.isBot) return

        if(message.contentRaw.startsWith(prefix)) {
            val prefixWithCommand = message.contentRaw.split(' ')[0]
            val commandName = prefixWithCommand.substringAfterLast(prefix)

            val command = data[commandName]

            if(command == null) {
                event.channel.sendMessage("Taka komenda nie istnieje XDDDDDDD???").queue()
            }

            else {
                command.run(event)
            }
        }
    }
}