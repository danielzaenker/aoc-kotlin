package net.codetreats.aoc.day06

import net.codetreats.aoc.Day
import net.codetreats.aoc.util.Logger
import java.math.BigDecimal
import java.math.MathContext
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

class Day06 : Day<List<String>>(6) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = true

    private var times: List<Long> = listOf()
    private var distanceRecords: List<Long> = listOf()

    override fun convert(input: List<String>): List<String>  {
        val (inputTimes, inputDistanceRecords) = input.filter { it.isNotBlank() }.map { it.split(":").last().split(" ").filter { it.isNotBlank() }.map { it.toLong() } }
        times = inputTimes
        distanceRecords = inputDistanceRecords
        return input
    }

    private fun distance(press: Long, totalTime: Long): Long {
        return (totalTime - press) * press
    }

    private fun beatRecordCount(record: Long, time: Long): Long {
        var beat = 0L
        for (press in 1..time) {
            if (distance(press, time) > record) {
                beat += 1
            } else if (beat > 0) {
                // quadratic function -> after we drop under the record the second time we will never reach it again
                break
            }
        }
        return beat
    }

    override fun run1(data: List<String>): String {
        val beatRecord = distanceRecords.zip(times) { record, time ->
            beatRecordCount(record, time)
        }
        return beatRecord.reduce { acc, i -> acc * i }.toString()
    }

    private fun curveIntersection(time: Long, distanceRecord: Long) : Long {
        val x1 = (time + sqrt((time * time - 4 * distanceRecord).toDouble())) / 2
        val x2 = (time - sqrt((time * time - 4 * distanceRecord).toDouble())) / 2
        val (start, end) = listOf(x1, x2).sorted()
        return (floor(end) - ceil(start) + 1).toLong()
    }

    override fun run2(data: List<String>): String {
        val time = times.joinToString("").toLong()
        val distanceRecord = distanceRecords.joinToString("").toLong()
        //return curveIntersection(time, distanceRecord).toString()
        return beatRecordCount(distanceRecord, time).toString()
    }
}