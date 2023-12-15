package net.codetreats.aoc.day15

import net.codetreats.aoc.Day
import net.codetreats.aoc.util.Logger
import java.util.*

class Day15 : Day<List<String>>(15) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = false

    override fun convert(input: List<String>): List<String> = input

    private fun hash(cur: String): Int {
        var result = 0
        for (c in cur) {
            if (c == ' ') {
                continue
            }
            result += c.code
            result *= 17
            result %= 256
        }
        return result
    }

    override fun run1(data: List<String>): String {
        return data.first().split(",").sumOf { hash(it) }.toString()
    }

    private fun run2Maps(data: List<String>): String {
        val boxes = MutableList(256) { mutableMapOf<String, Int>() }
        data.first().split(",").forEach { instruction ->
            if (instruction.endsWith("-")) {
                val label = instruction.substring(0, instruction.length-1)
                val hash = hash(label)
                boxes[hash].remove(label)
            } else {
                val (label, focal) = instruction.split("=")
                val hash = hash(label)
                boxes[hash][label] = focal.toInt()
            }
        }
        return boxes.flatMapIndexed { boxIndex, box ->
            box.entries.mapIndexed { slotIndex, slot ->
                (boxIndex + 1) * (slotIndex + 1) * slot.value
            }
        }.sum().toString()
    }

    // just for fun without maps because we are implementing one ;)
    private fun run2Lists(data: List<String>): String {
        val boxes = MutableList(256) { LinkedList<Pair<String, Int>>() }
        data.first().split(",").forEach { instruction ->
            if (instruction.endsWith("-")) {
                val label = instruction.substring(0, instruction.length-1)
                val hash = hash(label)
                val idx = boxes[hash].indexOfFirst { it.first == label }
                if (idx >= 0) {
                    boxes[hash].removeAt(idx)
                }
            } else {
                val (label, focal) = instruction.split("=")
                val hash = hash(label)
                val idx = boxes[hash].indexOfFirst { it.first == label }
                val pair = Pair(label, focal.toInt())
                if (idx >= 0) {
                    boxes[hash][idx] = pair
                } else {
                    boxes[hash].add(pair)
                }
            }
        }
        return boxes.flatMapIndexed { boxIndex, box ->
            box.mapIndexed { slotIndex, slot ->
                (boxIndex + 1) * (slotIndex + 1) * slot.second
            }
        }.sum().toString()
    }

    override fun run2(data: List<String>): String {
        return run2Maps(data)
        //return run2Lists(data)
    }
}