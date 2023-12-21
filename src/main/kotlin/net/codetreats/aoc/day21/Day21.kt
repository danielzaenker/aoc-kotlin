package net.codetreats.aoc.day21

import net.codetreats.aoc.Day
import net.codetreats.aoc.common.Board
import net.codetreats.aoc.common.DataPoint
import net.codetreats.aoc.common.boardfromInput
import net.codetreats.aoc.util.Logger

class Day21 : Day<List<String>>(21) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = true

    private val dp = mutableMapOf<Pair<DataPoint<Char>, Int>, Set<DataPoint<Char>>>()

    override fun convert(input: List<String>): List<String> = input

    private fun solve(cur: DataPoint<Char>, steps: Int, board: Board<Char>, totalSteps: Int = if (useDummy) 6 else 64): Set<DataPoint<Char>> {
        if (cur.value == '#') {
            return setOf()
        }
        if (steps == totalSteps) {
            board.set(cur.x, cur.y, 'O')
            return setOf(cur)
        }

        var cached = dp[Pair(cur, steps)]
        cached = null
        if (cached == null) {
            cached = board.neighbors(cur.x, cur.y).flatMap { solve(it, steps + 1, board, totalSteps) }.toSet()
            dp[Pair(cur, steps)] = cached
        }
        return cached

    }
    private fun solve2(cur: DataPoint<Char>, steps: Int, board: Board<Char>, totalSteps: Int = if (useDummy) 6 else 26501365): Set<DataPoint<Char>> {
        val curNormalized = DataPoint(Math.floorMod(cur.x, board.width), Math.floorMod(cur.y, board.height), cur.value)
        if (cur.value == '#') {
            return setOf()
        }
        if (steps == totalSteps) {
            //board.set(cur.x, cur.y, 'O')
            return setOf(cur)
        }

//        var cached = dp[Pair(curNormalized, steps)]
//        if (cached == null) {
//            cached = board.neighborsRepeated(curNormalized.x, curNormalized.y).flatMap { solve2(it, steps + 1, board, totalSteps) }.toSet()
//            dp[Pair(curNormalized, steps)] = cached
//        }
        val cached = board.neighborsRepeated(curNormalized.x, curNormalized.y).flatMap { solve2(it, steps + 1, board, totalSteps) }.toSet()
        return cached

    }

    override fun run1(data: List<String>): String {
        val board = boardfromInput(data)
        var start = DataPoint(0, 0, ' ')
        outer@ for (y in 0 until board.height) {
            for (x in 0 until board.width) {
                val cur = board.get(x, y)
                if (cur.value == 'S') {
                    start = cur
                    break@outer
                }
            }
        }

//        val result = solve(start, 0, board)
//        return result.size.toString()
        return ""
    }

    override fun run2(data: List<String>): String {
        dp.clear()
        var board = boardfromInput(data)
        var start = DataPoint(0, 0, ' ')
        outer@ for (y in 0 until board.height) {
            for (x in 0 until board.width) {
                val cur = board.get(x, y)
                if (cur.value == 'S') {
                    start = cur
                    break@outer
                }
            }
        }
        solve(start, 0, board, 6)
        println(board)
        board = boardfromInput(data)
        solve(start, 0,  board, 10)
        println(board)
        board = boardfromInput(data)
        solve(start, 0,  board, 14)
        println(board)

        //val result = solve2(start, 0, board, 10)
        //return result.size.toString()
        return ""
    }
}