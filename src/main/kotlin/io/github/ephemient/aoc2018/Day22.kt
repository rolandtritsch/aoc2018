package io.github.ephemient.aoc2018

import java.util.PriorityQueue

class Day22(lines: List<String>) {
    private val depth = lines[0].removePrefix("depth: ").toInt()
    private val target = lines[1].removePrefix("target: ").split(",", limit = 2).let { (x, y) ->
        IntPair(x.toInt(), y.toInt())
    }

    private var maze: MutableList<MutableList<Int>> =
        (1..target.first).accumulate(MutableList(target.second + 1) { y -> 48271 * y }) { left, x ->
            (1..target.second).accumulate(16807 * x) { up, y ->
                ((up + depth) % 20183) * ((left[y] + depth) % 20183)
            }.toMutableList().apply { if (x == target.first) this[target.second] = 0 }
        }.toMutableList()

    fun part1(): Int = (IntPair(0, 0)..target).sumBy { get(it) }

    fun part2(): Int {
        val weights = mutableMapOf<Pair<IntPair, Int>, Int>()
        val queue = PriorityQueue(compareBy<Triple<Int, IntPair, Int>> { (weight, pos, tool) ->
            weight + pos.first + pos.second + if (tool == 1) 0 else 7
        }).apply { add(Triple(0, target, 1)) }
        while (queue.isNotEmpty()) {
            val (weight, pos, tool) = queue.remove()
            if (pos.first == 0 && pos.second == 0 && tool == 1) return weight
            val key = Pair(pos, tool)
            if (weights[key]?.takeIf { it <= weight } != null) continue
            weights[key] = weight
            queue.add(Triple(weight + 7, pos, change(get(pos), tool)))
            for (pos in pos.neighbors) {
                if (get(pos) != tool) queue.add(Triple(weight + 1, pos, tool))
            }
        }
        error("no path found")
    }

    private operator fun get(pos: IntPair): Int {
        maze.getOrNull(pos.first)?.getOrNull(pos.second)?.let { return (it + depth) % 20183 % 3 }
        var h = maze.last().lastIndex
        if (h <= pos.second) {
            do h *= 2 while (h < pos.second)
            for ((x, row) in maze.withIndex()) {
                row.addAll(if (x == 0) { 48271 * row.size..48271 * h step 48271 } else {
                    (row.size..h).accumulate(row.last()) { up, y ->
                        ((up + depth) % 20183) * ((maze[x - 1][y] + depth) % 20183)
                    }.drop(1)
                })
            }
        }
        var w = maze.lastIndex
        while (w <= pos.first) w *= 2
        maze.addAll((maze.size..w).accumulate(maze.last()) { left, x ->
            (1..h).accumulate(16807 * x) { up, y ->
                ((up + depth) % 20183) * ((left[y] + depth) % 20183)
            }.toMutableList()
        }.drop(1))
        return (maze[pos.first][pos.second] + depth) % 20183 % 3
    }

    companion object {
        private val IntPair.neighbors: List<IntPair>
            get() = listOfNotNull(
                IntPair(first - 1, second).takeIf { first > 0 },
                IntPair(first, second - 1).takeIf { second > 0 },
                IntPair(first + 1, second), IntPair(first, second + 1)
            )

        private fun change(risk: Int, tool: Int) = when {
            risk == 0 && tool == 1 -> 2
            risk == 0 && tool == 2 -> 1
            risk == 1 && tool == 0 -> 2
            risk == 1 && tool == 2 -> 0
            risk == 2 && tool == 0 -> 1
            risk == 2 && tool == 1 -> 0
            else -> error("change(risk = $risk, tool = $tool)")
        }
    }
}