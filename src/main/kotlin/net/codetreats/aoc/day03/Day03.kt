package net.codetreats.aoc.day03

import net.codetreats.aoc.Day
import net.codetreats.aoc.common.Board
import net.codetreats.aoc.common.DataPoint
import net.codetreats.aoc.common.boardfromInput
import net.codetreats.aoc.util.Logger

class Day03 : Day<List<String>>(3) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = false

    override fun convert(input: List<String>): List<String> = input

    override fun run1(data: List<String>): String {
        val schematic = boardfromInput(data)
        val curNumber = mutableListOf<Char>()
        val numbers = mutableListOf<Long>()
        var isPartNumber = false
        data.forEachIndexed { y, line ->
            line.forEachIndexed { x, symbol ->
                if (symbol.isDigit()) {
                    curNumber.add(symbol)
                    val neighbors = schematic.neighbors(x, y, withDiag = true, withSelf = false)
                    val adjSymbol = neighbors.find { it.value != '.' && !it.value.isDigit() }
                    if (adjSymbol != null) {
                       isPartNumber = true
                    }
                } else {
                    if (isPartNumber) {
                        numbers.add(curNumber.joinToString("").toLong())
                    }
                    isPartNumber = false
                    curNumber.clear()

                }
            }
        }

        return numbers.sum().toString()
    }

    override fun run2(data: List<String>): String {
        val schematic = boardfromInput(data)
        val curNumber = mutableListOf<Char>()
        val gears = mutableMapOf<DataPoint<Char>, MutableList<Long>>()
        var currentGear : DataPoint<Char>? = null
        data.forEachIndexed { y, line ->
            line.forEachIndexed { x, symbol ->
                if (symbol.isDigit()) {
                    curNumber.add(symbol)
                    val neighbors = schematic.neighbors(x, y, withDiag = true, withSelf = false)
                    val adjSymbol = neighbors.find { it.value == '*' }
                    if (adjSymbol != null) {
                        currentGear = adjSymbol
                    }
                } else {
                    if (currentGear != null) {
                        val list = gears.getOrDefault(currentGear, mutableListOf())
                        list.add(curNumber.joinToString("").toLong())
                        gears[currentGear!!] = list
                    }
                    currentGear = null
                    curNumber.clear()

                }
            }
        }
        return gears.values.filter { it.size == 2 }.sumOf { it.reduce { acc, l -> acc * l } }.toString()
    }
}