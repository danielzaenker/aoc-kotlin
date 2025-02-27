package net.codetreats.aoc.day06

import net.codetreats.aoc.util.Level
import net.codetreats.aoc.util.Logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day06Test {
    private lateinit var classUnderTest : Day06

    @BeforeEach
    fun setup() {
        Logger.level = Level.ERROR
        classUnderTest = Day06()
    }

    @Test
    fun testPart01() {
        assertEquals("131376", classUnderTest.run1(false))
    }

    @Test
    fun testPart01_dummyData() {
        assertEquals("288", classUnderTest.run1(true))
    }

    @Test
    fun testPart02() {
        assertEquals("34123437", classUnderTest.run2(false))
    }

    @Test
    fun testPart02_dummyData() {
        assertEquals("71503", classUnderTest.run2(true))
    }
}
