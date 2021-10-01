package bot.commands

import bot.drawing.Drawing
import bot.drawing.drawOperations.DrawCenteredStringOperation
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.springframework.stereotype.Component
import java.awt.*

@Component
class CommandInvitation : Command() {
    override val name = "invitation"

    override suspend fun run(event: Event) {
        var event = event as GuildMessageReceivedEvent
        var channel = event.channel

        val invitedPersonName = event.message.contentDisplay.split(" ")[1]
        execute(channel, listOf(invitedPersonName))
    }

    override suspend fun execute(channel: TextChannel, args: List<String>) {
        val personName = args[0]

        val font = Font("Lobster", Font.PLAIN, 20)
        val color  = Color(219, 131, 173)
        val textArea = Rectangle(340, 200, 115, 30)

        val sourceImage = "Invitation.png" //TODO: refactor :<
        val invitationalImage = Drawing(sourceImage).getImage(DrawCenteredStringOperation(personName, font, color, textArea))

        channel.sendFile(invitationalImage).queue()
    }
}