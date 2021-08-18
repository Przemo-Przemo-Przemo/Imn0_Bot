package bot.commands

import bot.repositories.db.IAccountProductsRepository
import net.dv8tion.jda.api.entities.TextChannel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

class TrashScript {
    //    override suspend fun run(event: Event) {
//        var event = event as GuildMessageReceivedEvent
//        var channel = event.channel
//
////        event.message.contentRaw.split(' ')[1]
//        execute(channel, event.message.contentRaw.split(' '))
//    }


//    package bot.commands
//
//    import net.dv8tion.jda.api.events.Event
//    import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
//    import bot.repositories.db.IAccountProductsRepository
//    import net.dv8tion.jda.api.entities.TextChannel
//    import org.springframework.beans.factory.annotation.Autowired
//    import org.springframework.stereotype.Component
//
//    @Component
//    class CommandChwalikAdmin(@Autowired private val accountProductsRepository: IAccountProductsRepository): Command() {
//
//        override val name = "chwalik_admin"
//
//        override suspend fun execute(channel: TextChannel, args: List<String>) {
//            val accountProductName = args[1]
//
////        var accountProduct = accountProductsRepository.findFirstByName(accountProductName)
//
////        channel.sendMessage(accountProduct.meme).queue()
//        }
//    }
}