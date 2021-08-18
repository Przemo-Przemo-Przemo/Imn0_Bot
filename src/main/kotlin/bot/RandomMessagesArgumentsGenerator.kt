package bot

import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class RandomMessagesArgumentsGenerator {
    val commandNameToListOfSetOfPossibleArguments: HashMap<String, List<Set<String>>> = HashMap()

    //todo: reflections test whether all commands are met
    constructor() {
        commandNameToListOfSetOfPossibleArguments["clips"] = listOf(setOf("Pokimane", "Jinnytty"))
        commandNameToListOfSetOfPossibleArguments["get"] = listOf(setOf("pokimane", "LoL Esports"), setOf("5"))
        commandNameToListOfSetOfPossibleArguments["invitation"] = listOf(setOf("Imno")) //todo: randomize to guild members
        commandNameToListOfSetOfPossibleArguments["pong"] = listOf(setOf(""))
        commandNameToListOfSetOfPossibleArguments["screenshoots"] = listOf(setOf("pokimane", "LoL Esports"), setOf("5"))
    }

    fun randomArguments(commandName: String): List<String> {
        val arguments = commandNameToListOfSetOfPossibleArguments[commandName]
        if(arguments == null) return listOf("fuck off")

        val randomArguments: MutableList<String> = mutableListOf()
        for(argument in arguments) {
            randomArguments.add(argument.random())
        }

        return randomArguments
    }
}