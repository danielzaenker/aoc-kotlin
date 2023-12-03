package net.codetreats.aoc.day03

import net.codetreats.aoc.util.Level
import net.codetreats.aoc.util.Logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day03Test {
    private lateinit var classUnderTest : Day03

    @BeforeEach
    fun setup() {
        Logger.level = Level.ERROR
        classUnderTest = Day03()
    }

    @Test
    fun testPart01() {
        assertEquals("520019", classUnderTest.run1(false))
    }

    @Test
    fun testPart01_dummyData() {
        assertEquals("4361", classUnderTest.run1(true))
    }

    @Test
    fun testPart02() {
        assertEquals("75519888", classUnderTest.run2(false))
    }

    @Test
    fun testPart02_dummyData() {
        assertEquals("467835", classUnderTest.run2(true))
    }
}
