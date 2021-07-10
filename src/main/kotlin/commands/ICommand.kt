package commands

import net.dv8tion.jda.api.events.Event

interface ICommand {
    suspend fun run(event: Event)
}