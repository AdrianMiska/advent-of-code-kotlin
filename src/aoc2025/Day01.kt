package aoc2025

import println
import readInput

enum class Direction {
    Left, Right;

    companion object {
        fun fromChar(c: Char): Direction {
            return when (c) {
                'L' -> Left
                'R' -> Right
                else -> throw IllegalArgumentException("Invalid direction")
            }
        }
    }
}

fun main() {


    class Safe(
        var position: Int = 50,
        var zeroPositionCount: Int = 0,
        var zeroTransitCount: Int = 0
    ) {

        fun turn(direction: Direction, steps: Int): Int {

            (1..steps).forEach { _ ->
                when (direction) {
                    Direction.Left -> position -= 1
                    Direction.Right -> position += 1
                }
                if (position == 100) position = 0
                if (position == -1) position = 99

                if (position == 0) {
                    zeroTransitCount++
                }
            }

            if (position == 0) {
                zeroPositionCount++
            }

            return position
        }

    }

    fun solution(input: List<String>): Pair<Int, Int> {
        val safe = Safe()

        for (line in input) {

            if (line.isEmpty()) {
                continue
            }

            val direction = line.first().let { Direction.fromChar(it) }
            val steps = line.drop(1).toInt()
            safe.turn(direction, steps)

        }
        return Pair(safe.zeroPositionCount, safe.zeroTransitCount)
    }


    val testInput = readInput("2025", "Day01_test")
    check(solution(testInput) == Pair(3, 6))

    val input = readInput("2025", "Day01")
    solution(input).println()

}