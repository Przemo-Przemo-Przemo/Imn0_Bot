package bot

import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class RandomMessagesArgumentsGenerator {
    val commandNameToListOfSetOfPossibleArguments: HashMap<String, List<Set<String>>> = HashMap()

    //todo: reflections test whether all commands are met
    constructor() {
        commandNameToListOfSetOfPossibleArguments["clips"] = listOf(setOf("Pokimane", "Jinnytty"))
        commandNameToListOfSetOfPossibleArguments["get"] = listOf(setOf("pokimanelol", "lolesports"), setOf("5")) //todo: change to 1 add handling in repository so minimum can be 1
        commandNameToListOfSetOfPossibleArguments["invitation"] = listOf(setOf("Imno")) //todo: randomize to guild members
        commandNameToListOfSetOfPossibleArguments["pong"] = listOf(setOf(""))
        commandNameToListOfSetOfPossibleArguments["screenshoots"] = listOf(setOf("pokimanelol", "lolesports"), setOf("5"))
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