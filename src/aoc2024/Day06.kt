package aoc2024

import get
import isInBounds
import println
import readInput

enum class GridElement {
    FREE,
    OBSTRUCTION
}

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    companion object {
        fun fromDirectionChar(char: Char): Direction {
            return when (char) {
                '^' -> UP
                'v' -> DOWN
                '<' -> LEFT
                '>' -> RIGHT
                else -> throw IllegalArgumentException("Invalid direction: $char")
            }
        }
    }
}

class Guard(var position: Pair<Int, Int>, directionChar: Char) {

    internal var direction: Direction = Direction.fromDirectionChar(directionChar)

    fun turnRight() {
        direction = when (direction) {
            Direction.UP -> Direction.RIGHT
            Direction.DOWN -> Direction.LEFT
            Direction.LEFT -> Direction.UP
            Direction.RIGHT -> Direction.DOWN
        }
    }

    fun nextPosition(): Pair<Int, Int> {
        return when (direction) {
            Direction.UP -> Pair(position.first - 1, position.second)
            Direction.DOWN -> Pair(position.first + 1, position.second)
            Direction.LEFT -> Pair(position.first, position.second - 1)
            Direction.RIGHT -> Pair(position.first, position.second + 1)
        }
    }

    fun move() {
        position = nextPosition()
    }

}

class World(initialState: List<String>) {

    private lateinit var guard: Guard

    private var height: Int
    private var width: Int

    private var grid: List<List<GridElement>>

    init {
        val rawGrid = initialState.map { it.toCharArray() }

        height = rawGrid.size
        width = rawGrid.first().size

        grid = List(height) { x ->
            List(width) { y ->
                when (rawGrid[x][y]) {
                    '.' -> GridElement.FREE
                    '#' -> GridElement.OBSTRUCTION
                    else -> {
                        guard = Guard(Pair(x, y), rawGrid[x][y])
                        GridElement.FREE
                    }
                }
            }
        }
    }

    fun simulate(): Int {
        val visited = mutableSetOf(guard.position)
        val visitedDirectional = mutableSetOf(Pair(guard.position, guard.direction))

        while (guard.nextPosition().isInBounds(height - 1, width - 1)) {
            val gridElement = grid.get(guard.nextPosition())
            when (gridElement) {
                GridElement.FREE -> guard.move()
                GridElement.OBSTRUCTION -> guard.turnRight()
            }

            if (Pair(guard.position, guard.direction) in visitedDirectional) {
                return 0
            }

            visited.add(guard.position)
            visitedDirectional.add(Pair(guard.position, guard.direction))
        }

        return visited.size
    }

}

fun main() {


    fun part1(input: List<String>): Int {
        val world = World(input)
        return world.simulate()
    }

    fun part2(input: List<String>): Int {
        val workingString = input.joinToString("\n")
        // for every free space, simulate a world where that space is an obstruction
        return Regex("\\.").findAll(workingString).map {
            World(workingString.replaceRange(it.range, "#").split("\n")).simulate()
        }.filter { it == 0 }.count()
    }


    val testInput = readInput("2024", "Day06_test")
    val input = readInput("2024", "Day06")

    check(part1(testInput) == 41)
    part1(input).println()

    check(part2(testInput) == 6)
    part2(input).println()
}
