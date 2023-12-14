package net.codetreats.aoc.day10

import net.codetreats.aoc.Day
import net.codetreats.aoc.common.Board
import net.codetreats.aoc.common.DataPoint
import net.codetreats.aoc.common.boardfromInput
import net.codetreats.aoc.util.Logger

class Day10 : Day<List<String>>(10) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = false

    private var countArea: Int = 0

    override fun convert(input: List<String>): List<String> = input

    private fun getNextSteps(board: Board<Char>, curPos: DataPoint<Char>): List<DataPoint<Char>> {
        val neighbors = board.neighbors(curPos.x, curPos.y).filter { it.value != '.' }
        return neighbors.filter { next ->
            val offset = DataPoint(next.x - curPos.x, next.y - curPos.y, ' ')
            when (next.value) {
                '|' -> offset.y != 0
                '-' -> offset.x != 0
                'L' -> offset.y > 0 || offset.x < 0
                'J' -> offset.y > 0 || offset.x > 0
                '7' -> offset.y < 0 || offset.x > 0
                'F' -> offset.y < 0 || offset.x < 0
                'S' -> true
                else -> false
            }
        }.filter { next ->
            val offset = DataPoint(next.x - curPos.x, next.y - curPos.y, ' ')
            when (curPos.value) {
                '|' -> offset.y != 0
                '-' -> offset.x != 0
                'L' -> offset.y < 0 || offset.x > 0
                'J' -> offset.y < 0 || offset.x < 0
                '7' -> offset.y > 0 || offset.x < 0
                'F' -> offset.y > 0 || offset.x > 0
                'S' -> true
                else -> false
            }

        }
    }

    private fun solvePath(board: Board<Char>, markerBoard: Board<Char>?, start: DataPoint<Char>): Int {
        var steps = 1
        var curPos = start
        var last = start
        while(true) {
            val next = getNextSteps(board, curPos).first { it != last }
            last = curPos
            curPos = next
            steps += 1
            markerBoard?.set(curPos.x, curPos.y, 'X')
            if (curPos == start) {
                break
            }
        }
        steps /= 2
        return steps
    }

    override fun run1(data: List<String>): String {
        val startY = data.indexOfFirst { "S" in it }
        val startX = data[startY].indexOfFirst { it == 'S' }
        val board = boardfromInput(data)
        val start = board.get(startX, startY)
        return solvePath(board, null, start).toString()
    }

    private fun floodFill(areaBoard: Board<Char>, start: DataPoint<Char>) {
        val cur = areaBoard.getOrNull(start.x, start.y)
        if (cur == null || cur.value == 'X' || cur.value == 'I') {
            return
        }
        areaBoard.set(start.x, start.y, 'I')
        countArea += 1
        areaBoard.neighbors(start.x, start.y, withDiag = true).forEach {
            floodFill(areaBoard, it)
        }

    }

    private fun getInsideDirection(cur: DataPoint<Char>, last: DataPoint<Char>, clockwise: Boolean): DataPoint<Char> {
        val offset = DataPoint(cur.x - last.x, cur.y - last.y, ' ')
        if (clockwise) {
            return when(offset) {
                DataPoint(1, 0, ' ') -> DataPoint(0, 1, ' ')
                DataPoint(0, 1, ' ') -> DataPoint(-1, 0, ' ')
                DataPoint(-1, 0, ' ') -> DataPoint(0, -1, ' ')
                DataPoint(0, -1, ' ') -> DataPoint(1, 0, ' ')
                else -> throw Exception("impossible")
            }
        } else {
            return when(offset) {
                DataPoint(1, 0, ' ') -> DataPoint(0, -1, ' ')
                DataPoint(0, 1, ' ') -> DataPoint(1, 0, ' ')
                DataPoint(-1, 0, ' ') -> DataPoint(0, 1, ' ')
                DataPoint(0, -1, ' ') -> DataPoint(-1, 0, ' ')
                else -> throw Exception("impossible")
            }
        }

    }


    override fun run2(data: List<String>): String {
        val startY = data.indexOfFirst { "S" in it }
        val startX = data[startY].indexOfFirst { it == 'S' }
        val board = boardfromInput(data)
        val areaBoard = boardfromInput(data)
        val start = board.get(startX, startY)
        solvePath(board, areaBoard, start)

        var curPos = start
        var last = start
        while(true) {
            val next = getNextSteps(board, curPos).first { it != last }
            last = curPos
            curPos = next
            val inside = getInsideDirection(curPos, last, false)
            floodFill(areaBoard, DataPoint(curPos.x + inside.x, curPos.y + inside.y, ' '))
            floodFill(areaBoard, DataPoint(last.x + inside.x, last.y + inside.y, ' '))
            if (curPos == start) {
                break
            }
        }
        return countArea.toString()
    }
}