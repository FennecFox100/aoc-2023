import kotlin.math.max
import kotlin.math.min

fun main() {

    val input = readInput("Day03")
    Day03Part1().solve(input).println()

}

class Day03Part1 {

    data class PartNumber(
        val y: Int,
        val startX: Int,
        var endX: Int = startX
    )

    fun solve(input: List<String>): Int {
        val matrix = input.toMatrix()
        var partsNumberSum = 0

        for ((y, row) in matrix.withIndex()) {
            var num: PartNumber? = null
            for ((x, char) in row.withIndex()) {
                if (char.isDigit()) {
                    if (num == null) {
                        num = PartNumber(y, x)
                    } else {
                        num.endX = x
                    }
                } else if (num != null) {
                    if (matrix.isAdjacentToASymbol(num)) {
                        partsNumberSum += matrix.parseNumber(num)
                    }
                    num = null
                }
            }
            if (num != null && matrix.isAdjacentToASymbol(num)) {
                partsNumberSum += matrix.parseNumber(num)
            }
        }

        return partsNumberSum
    }

    private fun Array<CharArray>.parseNumber(num: PartNumber): Int {
        return this[num.y].slice(num.startX..num.endX).joinToString("").toInt()
    }

    private fun List<String>.toMatrix() = this.map { it.toCharArray() }.toTypedArray()

    private fun Array<CharArray>.isAdjacentToASymbol(num: PartNumber): Boolean {
        val minX = max(num.startX - 1, 0)
        val maxX = min(num.endX + 1, this[num.y].size - 1)

        val minY = num.y - 1
        val maxY = num.y + 1

        return this[num.y][minX].isSymbol() || this[num.y][maxX].isSymbol()
                || (minY > 0 && containSymbol(minY, minX..maxX))
                || (maxY < this.size - 1 && containSymbol(maxY, minX..maxX))
    }

    private fun Array<CharArray>.containSymbol(y: Int, range: IntRange): Boolean {
        for (x in range) {
            if (this[y][x].isSymbol()) {
                return true
            }
        }
        return false
    }

    private fun Char.isSymbol(): Boolean {
        return !this.isDigit() && this != '.'
    }

}

