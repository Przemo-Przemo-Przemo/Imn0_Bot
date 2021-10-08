package bot

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDA
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class RandomMessagesArgumentsGenerator(jda: JDA, config: BotConfiguration) {
    private val commandNameToListOfSetOfPossibleArguments: HashMap<String, List<Set<String>>> = HashMap()

    //todo: reflections test whether all commands are met
    init {
        val days = ArrayList<String>()
        for(i in 0..20) {
            days.add(LocalDate.now().minusDays(
                (1..7).random().toLong()).toString())
        }
        commandNameToListOfSetOfPossibleArguments["clips"] = listOf(
            setOf("Pokimane", "Jinnytty", "ratirl", "thebausffs", "drututt"),
            days.toSet() //TODO: make which clip to take (not always the most popular one) random as well
        )

        commandNameToListOfSetOfPossibleArguments["get"] = listOf(
            setOf("pokimanelol", "lolesports", "Aatroxcarry", "thebausffs", "drututt"),
            setOf((0..5).random().toString())
        )

        val members = jda.getGuildById(config.mainGuildId)?.loadMembers()?.get()
        val membersNames = members?.map { member -> member.user.name }
        commandNameToListOfSetOfPossibleArguments["invitation"] = listOf(
            membersNames?.toSet() ?: setOf("Imno")
        )

        commandNameToListOfSetOfPossibleArguments["pong"] = listOf(
            setOf("")
        )

        commandNameToListOfSetOfPossibleArguments["screenshoots"] = listOf(
            setOf("pokimanelol", "lolesports", "Aatroxcarry", "thebausffs", "drututt"),
            setOf((0..5).random().toString())
        )
    }

    fun randomCommandWithRandomArguments(): Pair<String, List<String>> {
        val command = commandNameToListOfSetOfPossibleArguments.keys.random()

        return Pair(command, randomArguments(command))
    }

    fun randomArguments(commandName: String): List<String> {
        val arguments = commandNameToListOfSetOfPossibleArguments[commandName] ?: return listOf("fuck off")

        val randomArguments: MutableList<String> = mutableListOf()
        for(argument in arguments) {
            randomArguments.add(argument.random())
        }

        return randomArguments
    }
}