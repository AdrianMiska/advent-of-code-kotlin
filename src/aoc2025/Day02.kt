package aoc2025

import println
import readInput

fun main() {

    fun getRange(range: String): LongRange {
        val ends = range.split("-")
        return ends.first().toLong()..ends.last().toLong()
    }

    fun isRepeatedTwice(n: Number): Boolean {

        val stringRep = n.toString()
        if (stringRep.length % 2 != 0) return false

        val split1 = stringRep.slice(0..stringRep.lastIndex / 2)
        val split2 = stringRep.slice(stringRep.length / 2..stringRep.lastIndex)

        return split1 == split2
    }

    fun isRepeatedAtLeastTwice(n: Number): Boolean {
        val stringRep = n.toString()

        for (seqLen in 1..stringRep.length) {
            val seq = stringRep.substring(0, seqLen)
            val chunked = stringRep.chunked(seqLen)
            if (chunked.any { it != seq }) continue
            if (chunked.count { it == seq } >= 2) return true
        }

        return false
    }

    fun part1(input: String): Number {
        val ranges = input.split(",").map { getRange(it) }
        val invalidIds = ranges.flatMap { range -> range.filter { isRepeatedTwice(it) } }

        return invalidIds.sum()
    }

    fun part2(input: String): Number {
        val ranges = input.split(",").map { getRange(it) }
        val invalidIds = ranges.flatMap { range -> range.filter { isRepeatedAtLeastTwice(it) } }

        invalidIds.println()

        return invalidIds.sum()
    }


    val testInput = readInput("2025", "Day02_test").first()
    check(part1(testInput) == 1227775554L)
    check(part2(testInput) == 4174379265L)

    val input = readInput("2025", "Day02").first()
    part1(input).println()
    part2(input).println()
}