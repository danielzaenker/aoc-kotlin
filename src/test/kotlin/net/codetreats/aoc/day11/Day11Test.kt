package net.codetreats.aoc.day11

import net.codetreats.aoc.util.Level
import net.codetreats.aoc.util.Logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day11Test {
    private lateinit var classUnderTest : Day11

    @BeforeEach
    fun setup() {
        Logger.level = Level.ERROR
        classUnderTest = Day11()
    }

    @Test
    fun testPart01() {
        assertEquals("10033566", classUnderTest.run1(false))
    }

    @Test
    fun testPart01_dummyData() {
        assertEquals("374", classUnderTest.run1(true))
    }

    @Test
    fun testPart02() {
        assertEquals("560822911938", classUnderTest.run2(false))
    }

    @Test
    fun testPart02_dummyData() {
        assertEquals("82000210", classUnderTest.run2(true))
    }
}
