package com.organizer

import com.organizer.data.Folder
import com.organizer.data.Task

interface PreferenceService {

    /**
     * Gets a task from its key.
     *
     * @param key The name of the task to retrieve from the preference.
     *
     * @return A task object from the preference or null if not found.
     */
    fun restoreTask(key: String): Task?

    fun restoreTasks(folder: String = "root"): List<Task>

    fun restoreTasks(folders: List<String>): List<Task>

    /**
     * Gets a folder from its key.
     *
     * @param key The name of the folder to retrieve from the preference.
     *
     * @return A folder object or null if not found.
     */
    fun restoreFolder(key: String): Folder?

    fun restoreFolders(): List<Folder>

    fun storeTask(task: Task)

    fun storeTasks(tasks: List<Task>)

    fun findTaskIndex(): Int

    fun findFolderIndex(): Int
}
