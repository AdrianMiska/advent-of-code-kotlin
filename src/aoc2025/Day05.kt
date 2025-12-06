package aoc2025

import println
import readInput
import kotlin.math.max
import kotlin.math.min

fun main() {

    fun LongRange.overlaps(other: LongRange): Boolean =
        (other.first in this || other.last in this
                || this.first in other || this.last in other)

    fun LongRange.merge(other: LongRange): LongRange {
        check(overlaps(other)) { "Ranges do not Overlap" }
        return min(this.first, other.first)..max(this.last, other.last)
    }

    fun mergeRanges(ranges: List<LongRange>): List<LongRange> {
        return ranges
            .sortedBy { it.first }
            .fold(listOf()) { l, a ->
                when {
                    l.isEmpty() -> listOf(a)
                    a.overlaps(l.last()) -> listOf(*l.dropLast(1).toTypedArray(), a.merge(l.last()))
                    else -> listOf(*l.toTypedArray(), a)
                }
            }
    }

    fun getFreshRanges(input: List<String>): List<LongRange> = input
        .filter { it.matches(Regex("""^\d+-\d+$""")) }
        .map { it.split("-") }
        .map { it[0].toLong()..it[1].toLong() }

    fun part1(input: List<String>): Int {
        val freshRanges = getFreshRanges(input)
        val ingredients = input.filter { it.matches(Regex("""^\d+$""")) }.map { it.toLong() }

        val freshIngredients = ingredients.filter { freshRanges.any { range -> it in range } }
        return freshIngredients.count()
    }

    fun part2(input: List<String>): Long {
        val freshRanges = getFreshRanges(input)
        return mergeRanges(freshRanges).sumOf { range ->
            range.last - range.first + 1
        }
    }

    val testInput = readInput("2025", "Day05_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 14L)

    val input = readInput("2025", "Day05")
    part1(input).println()
    part2(input).println()
}