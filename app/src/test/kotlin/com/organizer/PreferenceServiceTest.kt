package com.organizer

import android.content.SharedPreferences
import com.organizer.data.Folder
import com.organizer.data.Task
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PreferenceServiceTest {

    private lateinit var preferenceService: PreferenceService

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private val rootString = "folder0${SEPARATOR}folder2${SEPARATOR}task3"

    private val task0 = Task(0, "TaskMessage", true)
    private val task1 = Task(1, "otherTaskMessage", true)
    private val task2 = Task(2, "testMessage", false)
    private val task3 = Task(3, "anotherMessage", false)

    private val folder0 = Folder(0, "FolderMessage", false)
    private val folder1 = Folder(1, "otherFolderMessage", true, mutableListOf(task0, task1))
    private val folder2 = Folder(2, "tesMessage", false, mutableListOf(task2, folder1))
    private val root = Folder(-1, "root", false, mutableListOf(folder0, folder2, task3))

    @BeforeEach
    fun init() {
        editor = mockk(relaxed = true)
        sharedPreferences = mockk(relaxed = true) {
            every { edit() } answers { mockk() }
            every { getString(any(), any()) } answers { secondArg() }
            every { getString("root", any()) } answers { rootString }
            every { getString("task0", any()) } answers { task0.toString() }
            every { getString("task1", any()) } answers { task1.toString() }
            every { getString("task2", any()) } answers { task2.toString() }
            every { getString("task3", any()) } answers { task3.toString() }
            every { getString("folder0", any()) } answers { folder0.toString() }
            every { getString("folder1", any()) } answers { folder1.toString() }
            every { getString("folder2", any()) } answers { folder2.toString() }
            every { edit() } answers { editor }
        }

        preferenceService = PreferenceServiceImpl(sharedPreferences)
    }

    @Test
    fun restoreTask_exists() {
        val task = preferenceService.restoreTask("task0")
        val otherTask = preferenceService.restoreTask("task1")

        assertNotNull(task)
        assertEquals(task0, task)
        assertNotNull(otherTask)
        assertEquals(task1, otherTask)
    }

    @Test
    fun restoreTask_doesNotExist() {
        val task = preferenceService.restoreTask("wrong")
        val otherTask = preferenceService.restoreTask("task10")

        assertNull(task)
        assertNull(otherTask)
    }

    @Test
    fun restoreTasks_root() {
        val expectedTasks = listOf(task0, task1, task2, task3)
        var tasks = preferenceService.restoreTasks(listOf("root"))

        assertIterableEquals(expectedTasks, tasks)


        tasks = preferenceService.restoreTasks("root")

        assertIterableEquals(expectedTasks, tasks)


        tasks = preferenceService.restoreTasks()

        assertIterableEquals(expectedTasks, tasks)
    }

    @Test
    fun restoreTasks_foldersExist() {
        var tasks = preferenceService.restoreTasks("folder0")

        assertTrue(tasks.isEmpty())


        tasks = preferenceService.restoreTasks("folder1")

        assertIterableEquals(listOf(task0, task1), tasks)


        tasks = preferenceService.restoreTasks("folder2")

        assertIterableEquals(listOf(task0, task1, task2), tasks)
    }

    @Test
    fun restoreTasks_folderDoesNotExist() {
        var tasks = preferenceService.restoreTasks("folder10")

        assertTrue(tasks.isEmpty())


        tasks = preferenceService.restoreTasks("wrong")

        assertTrue(tasks.isEmpty())
    }

    @Test
    fun restoreFolder_exists() {
        val folder = preferenceService.restoreFolder("folder0")

        assertNotNull(folder)
        assertEquals(folder0, folder)


        val otherFolder = preferenceService.restoreFolder("folder1")

        assertNotNull(otherFolder)
        assertEquals(folder1, otherFolder)
    }

    @Test
    fun restoreFolder_doesNotExist() {
        val folder = preferenceService.restoreFolder("folder10")

        assertNull(folder)


        val otherFolder = preferenceService.restoreFolder("wrong")

        assertNull(otherFolder)
    }

    @Test
    fun restoreFolders() {
        val allFolders = preferenceService.restoreFolders()

        assertIterableEquals(listOf(folder0, folder1, folder2), allFolders)
    }

    @Test
    fun storeTask() {
        preferenceService.storeTask(task0)

        verify {
            editor.putString("task0", task0.toString())
        }
        verify(exactly = 1) { editor.apply() }
    }

    @Test
    fun storeTasks() {
        preferenceService.storeTasks(listOf(task0, task1, task2))

        verify {
            editor.putString("task0", task0.toString())
            editor.putString("task1", task1.toString())
            editor.putString("task2", task2.toString())
        }
        verify(exactly = 1) { editor.apply() }
    }

    @Test
    fun storeFolder() {
        preferenceService.storeTask(folder0)

        verify {
            editor.putString("folder0", folder0.toString())
        }
        verify(exactly = 1) { editor.apply() }
    }

    @Test
    fun storeFolders() {
        preferenceService.storeTasks(listOf(folder0, folder1))

        verify {
            editor.putString("folder0", folder0.toString())
            editor.putString("folder1", folder1.toString())
        }
        verify(exactly = 1) { editor.apply() }
    }

    @Test
    fun storeRoot() {
        preferenceService.storeTask(root)

        verify {
            editor.putString("root", rootString)
        }
        verify(exactly = 1) { editor.apply() }
    }
}
