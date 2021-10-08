package bot.util

import kotlinx.coroutines.CompletableDeferred
import net.dv8tion.jda.api.requests.RestAction

object JDAUtil {
    suspend fun <T> RestAction<T>.await(): T {
        val deferred = CompletableDeferred<T>()

        this.queue {
            deferred.complete(it)
        }

        return deferred.await()
    }
}