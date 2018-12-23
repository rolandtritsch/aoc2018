package io.github.ephemient.aoc2018

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State

@State(Scope.Thread)
open class Day14Bench {
    private lateinit var lines: List<String>

    @Setup
    fun prepare() {
        lines = javaClass.classLoader.getResourceAsStream("day14.txt").bufferedReader().readLines()
    }

    @Benchmark
    fun part1(): String = Day14(lines).part1()

    @Benchmark
    fun part2(): Int? = Day14(lines).part2()
}