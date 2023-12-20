package net.codetreats.aoc.day20

import net.codetreats.aoc.util.Level
import net.codetreats.aoc.util.Logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day20Test {
    private lateinit var classUnderTest : Day20

    @BeforeEach
    fun setup() {
        Logger.level = Level.ERROR
        classUnderTest = Day20()
    }

    @Test
    fun testPart01() {
        assertEquals("825896364", classUnderTest.run1(false))
    }

    @Test
    fun testPart01_dummyData() {
        assertEquals("11687500", classUnderTest.run1(true))
    }

    @Test
    fun testPart02() {
        assertEquals("243566897206981", classUnderTest.run2(false))
    }
}
