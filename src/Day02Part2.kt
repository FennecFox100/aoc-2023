fun main() {

    val input = readInput("Day02")
    Day02Part2().solve(input).println()

}

class Day02Part2 {


    private data class RGB(var red: Int, var green: Int, var blue: Int) {
        fun computePower() = red * green * blue
    }

    fun solve(input: List<String>): Int {
        return input
            .map { it.substringAfter(':') }
            .sumOf { it.computeGamePower() }
    }

    private fun String.computeGamePower(): Int {
        return this.split(";")
            .map { it.parseNbColors() }
            .reduce { acc, it ->
                acc.apply {
                    red = kotlin.math.max(red, it.red)
                    green = kotlin.math.max(green, it.green)
                    blue = kotlin.math.max(blue, it.blue)
                }
            }
            .computePower()
    }

    private fun String.parseNbColors(): RGB {
        return this.split(",").associate {
            val (nb, color) = it.trim().split(" ")
            color to nb.toInt()
        }.toRGB()
    }

    private fun Map<String, Int>.toRGB(): RGB {
        return RGB(
            this.getOrDefault("red", 1),
            this.getOrDefault("green", 1),
            this.getOrDefault("blue", 1),
        )
    }
}

