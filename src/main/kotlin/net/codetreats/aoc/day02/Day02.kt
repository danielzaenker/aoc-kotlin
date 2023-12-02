package net.codetreats.aoc.day02

import net.codetreats.aoc.Day
import net.codetreats.aoc.util.Logger
import kotlin.math.max

class Day02 : Day<List<String>>(2) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = false

    override fun convert(input: List<String>): List<String> = input

    override fun run1(data: List<String>): String {
        val maxColor = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14,
        )
        var possible = 0
        data.forEach { line ->
            val split = line.split(":")
            val id = split.first().split(" ").last().toInt()
            val turns = split.last().split(";")
            val isImpossible = turns.any { turn ->
                val cubes = turn.split(",")
                cubes.any { cube ->
                    val color = cube.split(" ").filter { it.isNotEmpty() }
                    color.first().toInt() > maxColor[color.last()]!!
                }
            }
            if (!isImpossible) {
                possible += id
            }
        }
        return possible.toString()
    }

    override fun run2(data: List<String>): String {
        var sum = 0
        data.forEach { line ->
            val split = line.split(":")
            val turns = split.last().split(";")
            val minColor = mutableMapOf(
                "red" to 0,
                "green" to 0,
                "blue" to 0,
            )
            turns.forEach { turn ->
                val cubes = turn.split(",")
                cubes.forEach { cube ->
                    val color = cube.split(" ").filter { it.isNotEmpty() }
                    val curMin = minColor[color.last()]!!
                    minColor[color.last()] = max(color.first().toInt(), curMin)
                }
            }
            val power =  minColor["red"]!! * minColor["green"]!! * minColor["blue"]!!
            sum += power
        }
        return sum.toString()
    }
}