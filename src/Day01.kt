fun main() {

    fun part1(input: List<String>): Int {
        return input
            .sumOf { line -> "${line.first { it.isDigit() }}${line.last { it.isDigit() }}".toInt() }
    }


    fun part2(input: List<String>): Int {
        return Day01Part2().solve(input)
    }


    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

class Day01Part2 {

    private val textNumbers = arrayOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    fun solve(input: List<String>): Int {
        return input.sumOf {
            "${it.firstDigit()}${it.lastDigit()}".toInt()
        }
    }

    private fun String.firstDigit(): Char {
        this.forEachIndexed { i, char ->
            val digit = findDigit(char, i)
            if (digit != null) {
                return digit
            }
        }
        return '0'
    }

    private fun String.lastDigit(): Char {
        for (i in length - 1 downTo 0) {
            val digit = findDigit(this[i], i)
            if (digit != null) {
                return digit
            }
        }

        return '0'
    }

    private fun String.findDigit(char: Char, i: Int): Char? {
        if (char.isDigit()) {
            return char
        }

        val sub = this.substring(i)
        val index = textNumbers.indexOfFirst { sub.startsWith(it) }
        if (index > 0) {
            return index.digitToChar()
        }
        return null
    }
}
