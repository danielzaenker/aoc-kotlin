package net.codetreats.aoc.day15

import net.codetreats.aoc.util.Level
import net.codetreats.aoc.util.Logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day15Test {
    private lateinit var classUnderTest : Day15

    @BeforeEach
    fun setup() {
        Logger.level = Level.ERROR
        classUnderTest = Day15()
    }

    @Test
    fun testPart01() {
        assertEquals("516469", classUnderTest.run1(false))
    }

    @Test
    fun testPart01_dummyData() {
        assertEquals("1320", classUnderTest.run1(true))
    }

    @Test
    fun testPart02() {
        assertEquals("221627", classUnderTest.run2(false))
    }

    @Test
    fun testPart02_dummyData() {
        assertEquals("145", classUnderTest.run2(true))
    }
}
