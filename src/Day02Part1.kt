fun main() {

    val input = readInput("Day02")
    Day02Part1().solve(input).println()

}

class Day02Part1 {

    companion object {
        private val COLOR_MAX = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14,
        )
    }

    fun solve(input: List<String>): Int {
        return input.sumOf { it.solveGame() }
    }

    private fun String.solveGame(): Int {
        val (game, sets) = this.split(":")
        return if (sets.isGamePossible()) {
            game.substringAfter("Game ").toInt()
        } else {
            0
        }
    }

    private fun String.isGamePossible(): Boolean {
        return this.split(";").all { it.isSetPossible() }
    }

    private fun String.isSetPossible(): Boolean {
        return this.split(",").all {
            val (nb, color) = it.trim().split(" ")
            nb.toInt() <= COLOR_MAX.getOrDefault(color, 0)
        }
    }
}
