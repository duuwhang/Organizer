package com.organizer

import com.organizer.data.Task
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TaskTest {

    private val message = "testMessage"
    private val completed = true
    private val task0 = Task(0)
    private val task5 = Task(5, message, completed)

    @Test
    fun constructTask_default() {
        assertEquals(0, task0.index)
        assertEquals("Add Tasks", task0.name)
        assertEquals(false, task0.completed)
    }

    @Test
    fun constructTask() {
        assertEquals(5, task5.index)
        assertEquals(message, task5.name)
        assertEquals(completed, task5.completed)
    }

    @Test
    fun taskEquals_same() {
        val sameTask = Task(task0.index, task0.name, task0.completed)

        assertEquals(task0, sameTask)
    }

    @Test
    fun taskEquals_different() {
        assertNotEquals(task0, task5)
    }

    @Test
    fun taskToString_default() {
        assertEquals("Add Tasks${SEPARATOR}0", task0.toString())
    }

    @Test
    fun taskToString() {
        assertEquals("testMessage${SEPARATOR}1", task5.toString())
    }

    @Test
    fun taskIdentifier_default() {
        assertEquals("task0", task0.identifier)
    }

    @Test
    fun taskIdentifier() {
        assertEquals("task5", task5.identifier)
    }
}
