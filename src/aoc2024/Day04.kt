package aoc2024

import println
import readInput
import windowed2D

fun main() {


    fun <T> List<List<T>>.transpose(): List<List<T>> {
        val rowCount = size
        val colCount = maxOf { it.size }
        return List(colCount) { colIndex ->
            List(rowCount) { rowIndex ->
                this[rowIndex][colIndex]
            }
        }
    }

    fun List<List<Char>>.checkXMas(): Boolean{
        if(this[1][1] != 'A') return false

        var masCount = 0

        if(this[0][0] == 'M' && this[2][2] == 'S') masCount += 1
        if(this[0][0] == 'S' && this[2][2] == 'M') masCount += 1
        if(this[0][2] == 'M' && this[2][0] == 'S') masCount += 1
        if(this[0][2] == 'S' && this[2][0] == 'M') masCount += 1

        return masCount == 2
    }

    fun <T> List<List<T>>.diagonals(): List<List<T>> {
        val rowCount = size
        val colCount = maxOf { it.size }

        val startIndices = emptyList<Pair<Int, Int>>().toMutableList()

        for (x in 0..rowCount) {
            startIndices.add(Pair(x, 0))
        }
        for (y in 1..colCount) {
            startIndices.add(Pair(0, y))
        }

        val diagonals = startIndices.map { (xStart, yStart) ->

            val diagonal = emptyList<T>().toMutableList()

            var x = xStart
            var y = yStart

            while (true) {
                this.getOrNull(x)?.getOrNull(y)?.let {
                    diagonal.add(it)
                    x += 1
                    y += 1
                } ?: break
            }

            return@map diagonal

        }

        return diagonals
    }

    fun part1(input: List<String>): Int {
        val rows = input.map { it.toList() }
        val columns = rows.transpose()
        val diagonals1 = rows.diagonals()
        val diagonals2 = rows.reversed().diagonals()

        return listOf(rows, columns, diagonals1, diagonals2)
            .flatMap { lists -> lists.map { String(it.toCharArray()) } }
            .sumOf { Regex("XMAS").findAll(it).count() +  Regex("SAMX").findAll(it).count()}
    }

    fun part2(input: List<String>): Int {
        val matrix = input.map { it.toList() }
        return matrix.windowed2D(3).count { it.checkXMas() }
    }


    val testInput = readInput("2024", "Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput("2024", "Day04")
    part1(input).println()
    part2(input).println()
}
