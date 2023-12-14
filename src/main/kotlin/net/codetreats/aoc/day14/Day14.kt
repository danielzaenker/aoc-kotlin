package net.codetreats.aoc.day14

import net.codetreats.aoc.Day
import net.codetreats.aoc.common.Board
import net.codetreats.aoc.common.boardfromInput
import net.codetreats.aoc.util.Logger

private enum class Direction {
    NORTH, WEST, SOUTH, EAST
}

class Day14 : Day<List<String>>(14) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = false

    override fun convert(input: List<String>): List<String> = input

    private fun Board<Char>.tilt(direction: Direction): Board<Char> {
        val newBoard = Board(this.width, this.height, '.')
        when (direction) {
            Direction.NORTH -> {
                val toInsert = MutableList(this.width) { 0 }
                for (y in 0 until this.height) {
                    for (x in 0 until this.width) {
                        val cur = this.get(x, y)
                        if (cur.value == 'O') {
                            newBoard.set(x, toInsert[x], 'O')
                            toInsert[x] += 1

                        } else if (cur.value == '#') {
                            newBoard.set(x, y, '#')
                            toInsert[x] = y + 1
                        }
                    }
                }
            }

            Direction.WEST -> {
                val toInsert = MutableList(this.height) { 0 }
                for (x in 0 until this.width) {
                    for (y in 0 until this.height) {
                        val cur = this.get(x, y)
                        if (cur.value == 'O') {
                            newBoard.set(toInsert[y], y, 'O')
                            toInsert[y] += 1

                        } else if (cur.value == '#') {
                            newBoard.set(x, y, '#')
                            toInsert[y] = x + 1
                        }
                    }
                }
            }

            Direction.SOUTH -> {
                val toInsert = MutableList(this.width) { this.height - 1 }
                for (y in (this.height - 1) downTo 0) {
                    for (x in 0 until this.width) {
                        val cur = this.get(x, y)
                        if (cur.value == 'O') {
                            newBoard.set(x, toInsert[x], 'O')
                            toInsert[x] -= 1

                        } else if (cur.value == '#') {
                            newBoard.set(x, y, '#')
                            toInsert[x] = y - 1
                        }
                    }
                }
            }

            Direction.EAST -> {
                val toInsert = MutableList(this.height) { this.width - 1 }
                for (x in (this.width - 1) downTo 0) {
                    for (y in 0 until this.height) {
                        val cur = this.get(x, y)
                        if (cur.value == 'O') {
                            newBoard.set(toInsert[y], y, 'O')
                            toInsert[y] -= 1

                        } else if (cur.value == '#') {
                            newBoard.set(x, y, '#')
                            toInsert[y] = x - 1
                        }
                    }
                }
            }
        }
        return newBoard
    }

    private fun Board<Char>.getTotalLoad(): Long {
        var result = 0L
        for (y in 0..<this.height) {
            for (x in 0..<this.width) {
                if (this.get(x, y).value == 'O') {
                    result += this.height - y
                }
            }
        }
        return result

    }

    override fun run1(data: List<String>): String {
        val board = boardfromInput(data)

        val newBoard = board.tilt(Direction.NORTH)
        return newBoard.getTotalLoad().toString()
    }

    override fun run2(data: List<String>): String {
        val cycles = 1_000_000_000
        var board = boardfromInput(data)

        val cache = mutableMapOf<String, Board<Char>>()

        var firstHit = ""
        var firstIdx = 0
        var loopLength = 0
        for (cycle in 0 until cycles) {
            val key = board.toString()
            val cached = cache[key]
            if (cached != null) {
                if (firstHit.isBlank()) {
                    firstHit = key
                    firstIdx = cycle
                } else if (key == firstHit) {
                    loopLength = cycle - firstIdx
                    break
                }
                board = cached
            } else {
                for (direction in Direction.entries) {
                    board = board.tilt(direction)
                }
                cache[key] = board
            }
        }

        val equivalentCycleOffset = (cycles - firstIdx) % loopLength

        for (cycle in 0 until equivalentCycleOffset) {
            board = cache[board.toString()]!!
        }

        return board.getTotalLoad().toString()
    }
}