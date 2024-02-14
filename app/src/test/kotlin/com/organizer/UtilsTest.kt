package com.organizer

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class UtilsTest {

    private val list = listOf("testString", "testString2", "testString3")
    private val sameList = listOf("testString", "testString2", "testString3")
    private val otherList = listOf("otherTestString", "otherTestString2", "otherTestString3")
    private val otherOrderList = listOf("testString2", "testString3", "testString")
    private val otherTypeList1 = arrayListOf("testString", "testString2", "testString3")
    private val otherTypeList2 = setOf("testString", "testString2", "testString3")
    private val otherTypeList3 = mutableListOf("testString", "testString2", "testString3")
    private val message = "testErrorMessage"

    @Test
    fun assertIterableNotEquals_different() {
        assertIterableNotEquals(list, otherList)
    }

    @Test
    fun assertIterableNotEquals_differentWithMessage() {
        assertIterableNotEquals(list, otherList, message)
    }

    @Test
    fun assertIterableNotEquals_equal() {
        assertThrows<Throwable> {
            assertIterableNotEquals(list, sameList)
        }
    }

    @Test
    fun assertIterableNotEquals_equalWithMessage() {
        val thrownException = assertThrows<Throwable> {
            assertIterableNotEquals(list, sameList, message)
        }

        assertEquals(message, thrownException.message)
    }

    @Test
    fun assertIterableNotEquals_throwsSameAsOriginal() {
        val expectedException = assertThrows<Throwable> {
            assertIterableEquals(list, otherList)
        }
        val actualException = assertThrows<Throwable> {
            assertIterableNotEquals(list, sameList)
        }

        assertEquals(expectedException.javaClass, actualException.javaClass)
        assertEquals(expectedException.cause, actualException.cause)
        assertEquals(expectedException.suppressed, actualException.suppressed)
        assertNotEquals(expectedException.message, actualException.message)
        assertNotEquals(expectedException.stackTrace, actualException.stackTrace)
    }

    @Test
    fun assertIterableNotEquals_differentOrder() {
        assertIterableNotEquals(list, otherOrderList)
    }

    @Test
    fun assertIterableNotEquals_differentOrderWithMessage() {
        assertIterableNotEquals(list, otherOrderList, message)
    }

    @Test
    fun assertIterableNotEquals_differentType() {
        assertThrows<Throwable> {
            assertIterableNotEquals(list, otherTypeList1)
        }
        assertThrows<Throwable> {
            assertIterableNotEquals(list, otherTypeList2)
        }
        assertThrows<Throwable> {
            assertIterableNotEquals(list, otherTypeList3)
        }
    }

    @Test
    fun assertIterableNotEquals_differentTypeWithMessage() {
        assertThrows<Throwable> {
            assertIterableNotEquals(list, otherTypeList1, message)
        }
        assertThrows<Throwable> {
            assertIterableNotEquals(list, otherTypeList2, message)
        }
        assertThrows<Throwable> {
            assertIterableNotEquals(list, otherTypeList3, message)
        }
    }
}
