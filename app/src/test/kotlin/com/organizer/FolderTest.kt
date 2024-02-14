package com.organizer

import com.organizer.data.Folder
import com.organizer.data.Task
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class FolderTest {

    private val message = "testMessage"
    private val completed = true
    private val task0 = Task(0)
    private val task1 = Task(1, message, completed)
    private val tasks = mutableListOf(task0, task1)
    private val folder0 = Folder(0)
    private val folder5 = Folder(5, message, completed, tasks)

    @Test
    fun constructFolder_default() {
        assertEquals(0, folder0.index)
        assertEquals("Add Tasks", folder0.name)
        assertEquals(false, folder0.completed)
        assertIterableEquals(emptyList<Task>(), folder0.tasks)
    }

    @Test
    fun constructFolder() {
        assertEquals(5, folder5.index)
        assertEquals(message, folder5.name)
        assertEquals(completed, folder5.completed)
        assertIterableEquals(tasks, folder5.tasks)
    }

    @Test
    fun folderEquals_same() {
        val sameFolder = Folder(folder0.index, folder0.name, folder0.completed, folder0.tasks)

        assertEquals(folder0, sameFolder)
    }

    @Test
    fun folderEquals_different() {
        assertNotEquals(folder0, folder5)
    }

    @Test
    fun folderToString_default() {
        assertEquals("Add Tasks${SEPARATOR}0", folder0.toString())
    }

    @Test
    fun folderToString() {
        assertEquals(
            "testMessage${SEPARATOR}1${SEPARATOR}task0${SEPARATOR}task1",
            folder5.toString()
        )
    }

    @Test
    fun folderIdentifier_default() {
        assertEquals("folder0", folder0.identifier)
    }

    @Test
    fun folderIdentifier() {
        assertEquals("folder5", folder5.identifier)
    }
}
