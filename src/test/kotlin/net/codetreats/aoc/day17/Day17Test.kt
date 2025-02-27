package net.codetreats.aoc.day17

import net.codetreats.aoc.util.Level
import net.codetreats.aoc.util.Logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day17Test {
    private lateinit var classUnderTest : Day17

    @BeforeEach
    fun setup() {
        Logger.level = Level.ERROR
        classUnderTest = Day17()
    }

    @Test
    fun testPart01() {
        assertEquals("1263", classUnderTest.run1(false))
    }

    @Test
    fun testPart01_dummyData() {
        assertEquals("102", classUnderTest.run1(true))
    }

    @Test
    fun testPart02() {
        assertEquals("1411", classUnderTest.run2(false))
    }

    @Test
    fun testPart02_dummyData() {
        assertEquals("94", classUnderTest.run2(true))
    }
}
