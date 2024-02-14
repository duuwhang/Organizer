package com.organizer

import android.content.SharedPreferences
import com.organizer.data.Folder
import com.organizer.data.Task

const val SEPARATOR = ";;"
const val DEFAULT_MESSAGE = "Add Tasks"
const val DEFAULT_TASK_STRING = "$DEFAULT_MESSAGE${SEPARATOR}0"

class PreferenceServiceImpl(private val preferences: SharedPreferences) : PreferenceService {

    private val editor = preferences.edit()

    override fun restoreTask(key: String): Task? = try {
        preferences.getString(key, null)?.split(SEPARATOR)?.let {
            key.substringAfter("task").toIntOrNull()?.let { it1 -> Task(it1, it[0], it[1] == "1") }
        }
    } catch (exception: ClassCastException) {
        null
    }

    override fun restoreTasks(folder: String): List<Task> = restoreTasks(listOf(folder))

    override fun restoreTasks(folders: List<String>): List<Task> {
        val tasks = mutableListOf<Task>()

        folders.forEach { key ->
            restoreFolder(key)?.let { folder ->
                folder.tasks.forEach { task ->
                    when (task) {
                        is Folder -> tasks.addAll(restoreTasks(task.identifier))
                        else -> restoreTask(task.identifier)?.let { tasks.add(it) }
                    }
                }
            }
        }
        return tasks.sortedBy { it.index }
    }

    override fun restoreFolder(key: String): Folder? {
        return preferences.getString(key, null)
            ?.split(SEPARATOR)
            ?.let {
                if (key == "root") Folder(
                    -1,
                    "root",
                    false,
                    it.mapNotNull { task -> restoreTaskOrFolder(task) }.toMutableList()
                ) else Folder(
                    key.substringAfter("folder").toIntOrNull() ?: return null,
                    it[0],
                    it[1] == "1",
                    it.subList(2, it.size).mapNotNull { task -> restoreTaskOrFolder(task) }
                        .toMutableList()
                )
            }
    }

    private fun restoreTaskOrFolder(key: String): Task? =
        if (key.startsWith("task"))
            restoreTask(key)
        else
            restoreFolder(key)

    override fun restoreFolders(): List<Folder> {
        val folders = mutableListOf<Folder>()

        fun search(folder: Folder) {
            folder.tasks.forEach { task ->
                if (task is Folder) {
                    folders.add(task)
                    search(task)
                }
            }
        }
        restoreFolder("root")?.let { search(it) }

        return folders.sortedBy { it.index }
    }

    override fun storeTask(task: Task) {
        storeTasks(listOf(task))
    }

    override fun storeTasks(tasks: List<Task>) {
        tasks.forEach { task ->
            if (task.name == "root" && task.index == -1) {
                editor.putString(
                    task.name,
                    (task as Folder).tasks.joinToString(SEPARATOR) { it.identifier }
                )
            } else {
                editor.putString(
                    task.identifier,
                    task.toString()
                )
            }
        }
        editor.apply()
    }

    override fun findTaskIndex(): Int = restoreFolders().findIndex()

    override fun findFolderIndex(): Int = restoreTasks().findIndex()

    private fun List<Task>.findIndex(currentIndex: Int = 0): Int =
        find { it.index == currentIndex }
            ?.let { findIndex(currentIndex + 1) }
            ?: currentIndex
}
