package bot.commands

import bot.repositories.db.AccountProducts
import bot.repositories.db.IAccountProductsRepository
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import sun.security.krb5.Config

@Component
class CommandSetChannel : Command() {
    override val name = "set_channel"

    @Autowired
    private lateinit var accountProductsRepository: IAccountProductsRepository

    override suspend fun run(event: Event) {
        var event = event as GuildMessageReceivedEvent
        var channel = event.channel

        val mentionedChannel = event.message.mentionedChannels[0]

        if(mentionedChannel == null) {
            event.channel.sendMessage("YOU HAVE TO SPECIFY THE CHANNEL~!!!!!!!").queue()
            return
        }

        execute(channel, listOf(mentionedChannel.id))
    }

    override suspend fun execute(channel: TextChannel, args: List<String>) {
        val channelId = args[0] // /set_channel #channel

        //var config = accountProductsRepository.findFirst()
        
   //     if(config == null) {
   //         config = AccountProducts(null, channelId)
   //     }

   //     config.pingsSourceChannelId = channelId
    //    accountProductsRepository.save(config)
    }
}