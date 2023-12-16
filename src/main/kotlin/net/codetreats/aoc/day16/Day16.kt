package net.codetreats.aoc.day16

import net.codetreats.aoc.Day
import net.codetreats.aoc.common.Board
import net.codetreats.aoc.common.Point
import net.codetreats.aoc.common.SinglePoint
import net.codetreats.aoc.common.boardfromInput
import net.codetreats.aoc.util.Logger
import kotlin.math.max

class Day16 : Day<List<String>>(16) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = false

    override fun convert(input: List<String>): List<String> = input

    private operator fun Point.plus(other: Point): Point {
        return SinglePoint(this.x + other.x, this.y + other.y)
    }

    private fun move(start: Point, dir: Point, board: Board<Char>, energized: Map<SinglePoint, Board<Boolean>>) {
        if (start.x !in 0..<board.width || start.y !in 0..<board.height) {
            return
        }
        val cur = board.get(start.x, start.y)
        if (energized[dir]!!.get(start.x, start.y).value) {
            return
        }
        energized[dir]!!.set(start.x, start.y, true)
        when(cur.value) {
            '.' -> move(start + dir, dir, board, energized)
            '/' -> {
                val next = mapOf(
                    SinglePoint(1, 0) to SinglePoint(0, -1),
                    SinglePoint(0, 1) to SinglePoint(-1, 0),
                    SinglePoint(-1, 0) to SinglePoint(0, 1),
                    SinglePoint(0, -1) to SinglePoint(1, 0),
                )
                move(start + next[dir]!!, next[dir]!!, board, energized)
            }
            '\\' -> {
                val next = mapOf(
                    SinglePoint(1, 0) to SinglePoint(0, 1),
                    SinglePoint(0, 1) to SinglePoint(1, 0),
                    SinglePoint(-1, 0) to SinglePoint(0, -1),
                    SinglePoint(0, -1) to SinglePoint(-1, 0),
                )
                move(start + next[dir]!!, next[dir]!!, board, energized)
            }
            '-' -> {
                when (dir) {
                    SinglePoint(1, 0), SinglePoint(-1, 0) -> {
                        move(start + dir, dir, board, energized)
                    }
                    SinglePoint(0, 1), SinglePoint(0, -1) -> {
                        val right = SinglePoint(1, 0)
                        move(start + right, right, board, energized)
                        val left = SinglePoint(-1, 0)
                        move(start + left, left, board, energized)
                    }
                }
            }
            '|' -> {
                when (dir) {
                    SinglePoint(0, 1), SinglePoint(0, -1) -> {
                        move(start + dir, dir, board, energized)
                    }
                    SinglePoint(1, 0), SinglePoint(-1, 0) -> {
                        val down = SinglePoint(0, 1)
                        move(start + down, down, board, energized)
                        val up = SinglePoint(0, -1)
                        move(start + up, up, board, energized)
                    }
                }
            }
            else -> throw Exception("impossible")
        }

    }

    private fun solve(start: Point, dir: Point, board: Board<Char>): Int {
        val energized = mapOf (
            SinglePoint(1, 0) to Board<Boolean>(board.width, board.height, false),
            SinglePoint(0, 1) to Board<Boolean>(board.width, board.height, false),
            SinglePoint(-1, 0) to Board<Boolean>(board.width, board.height, false),
            SinglePoint(0, -1) to Board<Boolean>(board.width, board.height, false),
        )
        move(start, dir, board, energized)
        var result = 0
        for (x in 0..<board.width) {
            for (y in 0..<board.height) {
                if (energized.values.any { it.get(x, y).value }) {
                    result += 1
                }
            }
        }
        return result

    }

    override fun run1(data: List<String>): String {
        val board = boardfromInput(data)
        return solve(SinglePoint(0, 0), SinglePoint(1, 0), board).toString()
    }

    override fun run2(data: List<String>): String {
        val board = boardfromInput(data)

        val horizontal = (0..<board.width).toList().parallelStream().map { x ->
            val r1 = solve(SinglePoint(x, 0), SinglePoint(0, 1), board)
            val r2 = solve(SinglePoint(x, board.height - 1), SinglePoint(0, -1), board)
            max(r1, r2)
        }.toList()

        val vertical = (0..<board.height).toList().parallelStream().map { y ->
            val r1 = solve(SinglePoint(0, y), SinglePoint(1, 0), board)
            val r2 = solve(SinglePoint(board.width - 1, y), SinglePoint(-1, 0), board)
            max(r1, r2)
        }.toList()

        return listOf(horizontal, vertical).flatten().max().toString()
    }
}