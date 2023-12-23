package net.codetreats.aoc.day23

import net.codetreats.aoc.util.Level
import net.codetreats.aoc.util.Logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day23Test {
    private lateinit var classUnderTest : Day23

    @BeforeEach
    fun setup() {
        Logger.level = Level.ERROR
        classUnderTest = Day23()
    }

    @Test
    fun testPart01() {
        assertEquals("2326", classUnderTest.run1(false))
    }

    @Test
    fun testPart01_dummyData() {
        assertEquals("94", classUnderTest.run1(true))
    }

    @Test
    fun testPart02() {
        assertEquals("6574", classUnderTest.run2(false))
    }

    @Test
    fun testPart02_dummyData() {
        assertEquals("154", classUnderTest.run2(true))
    }
}
