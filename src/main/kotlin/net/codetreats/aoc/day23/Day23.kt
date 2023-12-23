package net.codetreats.aoc.day23

import net.codetreats.aoc.Day
import net.codetreats.aoc.common.Board
import net.codetreats.aoc.common.DataPoint
import net.codetreats.aoc.common.SinglePoint
import net.codetreats.aoc.common.boardfromInput
import net.codetreats.aoc.util.Logger
import java.lang.Integer.compare


data class Node(val point: DataPoint<Char>, val next: MutableList<Pair<Int, Node>>) {
    override fun toString(): String {
        return "point: $point next: ${next.map { "(${it.first}, ${it.second.point})" }.joinToString(", ")}"
    }
}

class Day23 : Day<List<String>>(23) {
    override val logger: Logger = Logger.forDay(dayOfMonth)

    override val useDummy = true

    private val dirMap = mapOf(
        '>' to SinglePoint(1, 0),
        '<' to SinglePoint(-1, 0),
        '^' to SinglePoint(0, -1),
        'v' to SinglePoint(0, 1),
    )

    override fun convert(input: List<String>): List<String> = input

    private fun solve(cur: DataPoint<Char>, dir: SinglePoint, steps: Long, board: Board<Char>): Long {
        if (cur.value == '#') {
            return 0L
        }

        if (cur.value != '.') {
            if (dirMap[cur.value]!! != dir) {
                return 0L
            }
        }

        if (cur.y == board.height - 1) {
            return steps
        }

        return board.neighbors(cur.x, cur.y).maxOf { next ->
            val nextDir = SinglePoint(next.x - cur.x, next.y - cur.y)
            // no cycles possible, we just have to make sure not to go back
            if (nextDir.x == -dir.x && nextDir.y == -dir.y) {
                0
            } else {
                solve(next, nextDir, steps + 1, board)
            }
        }
    }


    private fun generateGraph(cur: DataPoint<Char>, node: Node, steps: Int, nodes: MutableMap<DataPoint<Char>, Node>, board: Board<Char>, visited: Board<Boolean>) {
        if (cur.value == '#') {
            return
        }

        if (cur.y == board.height - 1) {
            val end = Node(cur, mutableListOf())
            node.next.add(Pair(steps, end))
            nodes[cur] = end
        }

        var nextNode = node
        var nextSteps = steps + 1
        if (board.neighbors(cur.x, cur.y).count { it.value != '#' } > 2) {
            nextNode = nodes.getOrDefault(cur, Node(cur, mutableListOf()))
            nextSteps = 0
            nextNode.next.add(Pair(steps+1, node))
            node.next.add(Pair(steps+1, nextNode))
            nodes[cur] = nextNode
        }

        if (visited.get(cur.x, cur.y).value) {
            return
        }
        visited.set(cur.x, cur.y, true)

        board.neighbors(cur.x, cur.y).forEach { next ->
            generateGraph(next, nextNode, nextSteps, nodes, board, visited)
        }
    }

    private fun findLongestPath(cur: Node, curSteps: Int, end: DataPoint<Char>, seen: Set<DataPoint<Char>>): Int {
        if (seen.contains(cur.point)) {
            return 0
        }

        val nextSeen = seen.plus(cur.point)
        if (cur.point == end) {
            return curSteps
        }

        return cur.next.maxOf { next ->
            findLongestPath(next.second, curSteps + next.first, end, nextSeen)
        }
    }

    private fun firstNodes(cur: Node, curSteps: Int, curDepth: Int, maxDepth: Int, seen: Set<DataPoint<Char>>): List<Triple<Node, Int, Set<DataPoint<Char>>>> {
        if (curDepth >= maxDepth || cur.next.isEmpty()) {
            return listOf(Triple(cur, curSteps, seen))
        }
        if (seen.contains(cur.point)) {
            return listOf()
        }

        val nextSeen = seen.plus(cur.point)

        return cur.next.map { next ->
            firstNodes(next.second, curSteps + next.first, curDepth + 1, maxDepth, nextSeen)
        }.flatten()
    }

    override fun run1(data: List<String>): String {
        val board = boardfromInput(data)
        val start = generateSequence(board.get(0, 0)) { board.get(it.x + 1, it.y) }.first { it.value == '.' }

        val max = solve(start, SinglePoint(0, 1), 0, board)

        return max.toString()
    }


    override fun run2(data: List<String>): String {
        val board = boardfromInput(data)
        val start = generateSequence(board.get(0, 0)) { board.get(it.x + 1, it.y) }.first { it.value == '.' }
        val end = generateSequence(board.get(0, board.height - 1)) { board.get(it.x + 1, it.y) }.first { it.value == '.' }

        val startNode = Node(start, mutableListOf())
        val nodes = mutableMapOf(start to startNode)
        generateGraph(start, startNode, 0, nodes, board, Board(board.width, board.height, false))

        // sequential version
        // val maxPath = findLongestPath(startNode, 0, end, mutableSetOf())

        val starts = firstNodes(startNode, 0, 0, 12, mutableSetOf())
        val maxPath = starts.parallelStream().map { findLongestPath(it.first, it.second, end, it.third) }.max(::compare).get()

        return maxPath.toString()
    }
}