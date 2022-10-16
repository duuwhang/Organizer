package com.organizer

import android.content.SharedPreferences

class PreferenceService(private val preferences: SharedPreferences) : PreferenceServiceInterface {

    private val editor = preferences.edit()

    override fun restoreTask(key: String): Pair<String, Boolean>? {
        val task = preferences.getString(key, null)?.split(";;")
        return task?.let { it.first() to (it.last() == "1") }
    }

    override fun restoreTasks(folder: String): Map<String, Pair<String, Boolean>> {
        TODO("Not yet implemented")
    }

    override fun restoreTasks(folders: List<String>): Map<String, Pair<String, Boolean>> {
        val tasks = mutableMapOf<String, Pair<String, Boolean>>()

        folders.forEach { key ->
            preferences.getString(key, null)
                ?.let { folder ->
                    folder.split(";;").apply { subList(2, size - 1) }.forEach { task ->
                        preferences.getString(task, "Add Tasks;;0")!!.split(";;").also {
                            tasks[task] = it[0] to (it[1] == "1")
                        }
                    }
                }
        }
        return tasks
    }

    override fun restoreFolder(key: String): Pair<String, Boolean>? {
        val folder = preferences.getString(key, null)?.split(";;")
        return folder?.let { it.first() to (it.last() == "1") }
    }

    override fun restoreFolders(): Map<String, Pair<String, Boolean>> {
        val folders = mutableMapOf<String, Pair<String, Boolean>>()

        preferences.getInt("folderCount", 0).downTo(0).forEach { index ->
            restoreFolder("folder$index")?.let { folders["folder$index"] = it }
        }
        return folders
    }
}
