package net.codetreats.aoc.day05

import net.codetreats.aoc.Day
import net.codetreats.aoc.util.Logger

data class NumberRange(var from: Long, var to: Long, var shift: Long = 0L) {
    fun split(at: NumberRange): List<NumberRange> {
        val splits = listOf(at.from, at.to).filter { it in from+1..<to }
        return when(splits.size) {
            1 -> listOf(NumberRange(from, splits[0]), NumberRange(splits[0], to))
            2 -> listOf(NumberRange(from, splits[0]), NumberRange(splits[0], splits[1]), NumberRange(splits[1], to))
            else -> listOf(this)
        }
    }
}

class Day05 : Day<List<List<NumberRange>>>(5) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = false

    private var seeds: List<Long> = listOf()
    private var seedRanges: List<NumberRange> = listOf()
    override fun convert(input: List<String>): List<List<NumberRange>> {
        val maps = mutableListOf<MutableList<NumberRange>>()
        var curList = mutableListOf<NumberRange>()
        input.forEach { line ->
            if ("seeds: " in line) {
                seeds = line.split(":").last().split(" ").filter { it.isNotBlank() }.map { it.toLong() }
                seedRanges = seeds.chunked(2).map { NumberRange(it.first(), it.first() + it.last()) }
            } else {
                if (line.contains("map:")) {
                    if (curList.isNotEmpty()) {
                        maps.add(curList)
                    }
                    curList = mutableListOf()
                } else if (line.isNotBlank()) {
                    val (dest, source, length) = line.split(" ").filter { it.isNotBlank() }.map { it.toLong() }
                    curList.add(NumberRange(source, source + length, dest - source))
                }
            }
        }
        if (curList.isNotEmpty()) {
            maps.add(curList)
        }
        return maps
    }

    override fun run1(data: List<List<NumberRange>>): String {
        val locations = seeds.map { seed ->
            var location = seed
            data.forEach { list ->
                val toShift = list.find { range -> location >= range.from && location < range.to }
                if (toShift != null) {
                    location += toShift.shift
                }
            }
            location
        }
        return locations.min().toString()
    }

    override fun run2(data: List<List<NumberRange>>): String {
        val locations = seedRanges.map { seedRange ->
            var locations = setOf(seedRange)
            data.forEach { list ->
                locations = locations.map { loc ->
                    list.map { l ->
                        loc.split(l)
                    }
                }.flatten().flatten().toSet()

                locations = locations.map { loc ->
                    val toShift = list.find { range -> loc.from >= range.from && loc.to <= range.to }
                    if (toShift != null) {
                        NumberRange(loc.from + toShift.shift, loc.to + toShift.shift)
                    } else {
                        loc
                    }
                }.toSet()
            }
            locations
        }

        //still kind of buggy, filter > 0 shouldn't be necessary ...
        return locations.minOf { it.filter {it.from > 0}.minOf { it.from } }.toString()
    }
}