package net.codetreats.aoc.day13

import net.codetreats.aoc.util.Level
import net.codetreats.aoc.util.Logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day13Test {
    private lateinit var classUnderTest : Day13

    @BeforeEach
    fun setup() {
        Logger.level = Level.ERROR
        classUnderTest = Day13()
    }

    @Test
    fun testPart01() {
        assertEquals("34918", classUnderTest.run1(false))
    }

    @Test
    fun testPart01_dummyData() {
        assertEquals("405", classUnderTest.run1(true))
    }

    @Test
    fun testPart02() {
        assertEquals("33054", classUnderTest.run2(false))
    }

    @Test
    fun testPart02_dummyData() {
        assertEquals("400", classUnderTest.run2(true))
    }
}
