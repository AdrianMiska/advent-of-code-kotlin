package aoc2024

import println
import readInput
import kotlin.math.abs

fun main() {

    /**
     * Splits the text into two lists of integers.
     */
    fun List<String>.splitToInt(): Pair<List<Int>, List<Int>> =
        map { it.substringBefore(" ").toInt() }.sorted() to
                map { it.substringAfterLast(" ").toInt() }.sorted()


    fun part1(input: List<String>): Int {
        val (list1, list2) = input.splitToInt()
        val distances = list1.zip(list2).map { abs(it.first - it.second) }

        return distances.sum()
    }

    fun part2(input: List<String>): Int {
        val (list1, list2) = input.splitToInt()

        val counts = list1.map { candidate -> list2.count { it == candidate } }
        val similarities = list1.zip(counts).map { it.first * it.second }

        return similarities.sum()
    }


    val testInput = readInput("2024", "Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("2024", "Day01")
    part1(input).println()
    part2(input).println()
}
