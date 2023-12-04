import kotlin.math.max
import kotlin.math.min

fun main() {

    val input = readInput("Day03")
    Day03Part2().solve(input).println()

}

class Day03Part2 {

    data class GearRatio(
        val y: Int,
        val startX: Int,
        var endX: Int = startX
    )

    data class Position(
        val x: Int,
        val y: Int,
    )

    fun solve(input: List<String>): Int {
        val matrix = input.toMatrix()
        val gears = mutableMapOf<Position, MutableList<Int>>()

        for ((y, row) in matrix.withIndex()) {
            var ratio: GearRatio? = null
            for ((x, char) in row.withIndex()) {
                if (char.isDigit()) {
                    if (ratio == null) {
                        ratio = GearRatio(y, x)
                    } else {
                        ratio.endX = x
                    }
                } else if (ratio != null) {
                    val ratioNumber = matrix.parseNumber(ratio)
                    matrix.findAdjacentGears(ratio)
                        .forEach {
                            gears.getOrPut(it) { mutableListOf() }.add(ratioNumber)
                        }
                    ratio = null
                }
            }
            if (ratio != null) {
                val ratioNumber = matrix.parseNumber(ratio)
                matrix.findAdjacentGears(ratio)
                    .forEach {
                        gears.getOrPut(it) { mutableListOf() }.add(ratioNumber)
                    }
            }
        }

        return gears
            .filter { it.value.size >= 2 }
            .values.sumOf { it.reduce { acc, i -> acc * i } }
    }

    private fun Array<CharArray>.parseNumber(num: GearRatio): Int {
        return this[num.y].slice(num.startX..num.endX).joinToString("").toInt()
    }

    private fun List<String>.toMatrix() = this.map { it.toCharArray() }.toTypedArray()

    private fun Array<CharArray>.findAdjacentGears(ratio: GearRatio): Sequence<Position> {
        return this.around(ratio)
            .filter { this[it.y][it.x].isGear() }
    }

    private fun Array<CharArray>.around(ratio: GearRatio) = sequence {
        val minX = max(ratio.startX - 1, 0)
        val maxX = min(ratio.endX + 1, this@around[ratio.y].size - 1)

        yield(Position(minX, ratio.y))
        yield(Position(maxX, ratio.y))

        val minY = ratio.y - 1
        if (minY > 0) {
            yieldAll((minX..maxX).map { Position(it, minY) })
        }

        val maxY = ratio.y + 1
        if (maxY < this@around.size - 1) {
            yieldAll((minX..maxX).map { Position(it, maxY) })
        }
    }

    private fun Char.isGear(): Boolean {
        return this == '*'
    }

}

