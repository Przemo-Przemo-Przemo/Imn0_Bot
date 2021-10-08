package bot.util

import bot.util.Algorithms
import java.util.*

object Algorithms {
    fun levenshtein(x: String, y: String): Int {
        val dp = Array(x.length + 1) { IntArray(y.length + 1) }
        for (i in 0..x.length) {
            for (j in 0..y.length) {
                if (i == 0) {
                    dp[i][j] = j
                } else if (j == 0) {
                    dp[i][j] = i
                } else {
                    dp[i][j] = min(
                        dp[i - 1][j - 1]
                                + costOfSubstitution(x[i - 1], y[j - 1]),
                        dp[i - 1][j] + 1,
                        dp[i][j - 1] + 1
                    )
                }
            }
        }
        return dp[x.length][y.length]
    }

    private fun costOfSubstitution(a: Char, b: Char): Int {
        return if (a == b) 0 else 1
    }

    private fun min(vararg numbers: Int): Int {
        return Arrays.stream(numbers)
            .min().orElse(Int.MAX_VALUE)
    }
}