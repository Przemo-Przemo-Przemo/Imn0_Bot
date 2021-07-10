package commands

import logic.drawing.Drawing
import logic.drawing.drawOperations.DrawCenteredStringOperation
import net.dv8tion.jda.api.events.Event
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import java.awt.*

class CommandInvitation : ICommand {
    override suspend fun run(event: Event) {
        val event = event as GuildMessageReceivedEvent
        val channel = event.channel

        val font = Font("Lobster", Font.PLAIN, 20)
        val color  = Color(219, 131, 173)
        val textArea = Rectangle(340, 200, 115, 30)

        val sourceImage = "Invitation.png" //TODO: refactor :>
        val invitationalImage = Drawing(sourceImage).getImage(DrawCenteredStringOperation(event.author.name, font, color, textArea))

        channel.sendFile(invitationalImage).queue()
    }


}