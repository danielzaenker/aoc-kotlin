package net.codetreats.aoc.day20

import net.codetreats.aoc.Day
import net.codetreats.aoc.common.lcm
import net.codetreats.aoc.util.Logger
import java.io.File

private abstract class Module(val next: List<String>, val color: String) {
    abstract fun update(curStep: Step): List<Step>
}
private class Broadcaster(next: List<String>) : Module(next, "green") {
    override fun update(curStep: Step): List<Step> {
        return next.map { Step(it, curStep.cur, curStep.pulse) }
    }
}

private class FlipFlop(next: List<String>, var state: Boolean = false) : Module(next, "blue") {
    override fun update(curStep: Step): List<Step> {
        if (!curStep.pulse) {
            state = !state
            return next.map { Step(it, curStep.cur, state) }
        }
        return listOf()
    }
}

private class Conjunction(next: List<String>, val states: MutableMap<String, Boolean> = mutableMapOf()) : Module(next, "red") {
    override fun update(curStep: Step): List<Step> {
        states[curStep.last] = curStep.pulse
        val nextPulse = !states.values.all { it }
        return next.map { Step(it, curStep.cur, nextPulse) }
    }
}

private data class Step(val cur: String, val last: String, val pulse: Boolean)

class Day20 : Day<List<String>>(20) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = false

    private var countLowPulse = 0L
    private var countHighPulse = 0L

    override fun convert(input: List<String>): List<String> = input

    private fun simulate(start: String, end: String, modules: Map<String, Module>): Boolean {
        val queue = mutableListOf(Step(start, "button", false))
        var result = false

        while (queue.isNotEmpty()) {
            val curStep = queue.removeFirst()
            if (curStep.cur == end) {
                result = curStep.pulse || result
                continue
            }
            val curModule = modules[curStep.cur]
            if (curStep.pulse) countHighPulse += 1 else countLowPulse += 1

            if (curModule == null) {
                continue
            }
            queue.addAll(curModule.update(curStep))
        }
        return result
    }

    // generate Graph with `dot -Tpdf day20.dot > day20.pdf`
    private fun generateDotGraphFile(modules: Map<String, Module>) {
        File("day20.dot").printWriter().use { out ->
            out.println("digraph G {")
            modules.entries.forEach { (name, module) ->
                module.next.forEach {
                    var color = module.color
                    if (name == "rx") {
                        color = "black"
                    }
                    out.println("  $name [color=$color];")
                    out.println("  $name -> $it;")
                }
            }
            out.println("}")
        }
    }

    private fun generateModules(data: List<String>): Map<String, Module> {
        val modules = mutableMapOf<String, Module>()
        data.forEach { line ->
            val (node, nextString) = line.filter { it != ' ' }.split("->")
            val next = nextString.split(",")
            if (node == "broadcaster") {
                modules["broadcaster"] = Broadcaster(next)
            } else if (node.startsWith("%")) {
                modules[node.substring(1)] = FlipFlop(next)
            } else if (node.startsWith("&")) {
                modules[node.substring(1)] = Conjunction(next)
            }
        }
        modules.entries.forEach { (name, module) ->
            module.next.forEach {
                val mod = modules[it]
                if (mod is Conjunction) {
                    mod.states[name] = false
                }
            }
        }
        return modules
    }

    override fun run1(data: List<String>): String {
        val modules = generateModules(data)

        for (i in 1..1000) {
            simulate("broadcaster", "can't stop me nooooow", modules)
        }
        return (countLowPulse * countHighPulse).toString()
    }

    override fun run2(data: List<String>): String {
        if (useDummy) {
            return ""
        }
        val modules = generateModules(data)
        //generateDotGraphFile(modules)

        // simulate each subgraph separately and check when high pulse reaches "zg"
        // (conjugate module that generates the signal for the target rx from all subgraphs)
        val foundAt = listOf("jl", "rp", "rt", "jr").map { start ->
            var found = false
            var count = 0L
            while (!found) {
                found = simulate(start, "zg", modules)
                count += 1
            }
            count
        }
        return foundAt.reduce { acc, value -> lcm(acc, value) }.toString()
    }
}