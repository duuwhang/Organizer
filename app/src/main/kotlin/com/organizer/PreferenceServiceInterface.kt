package com.organizer

interface PreferenceServiceInterface {
    fun restoreTask(key: String): Pair<String, Boolean>?
    fun restoreTasks(folders: List<String> = listOf("root")): Map<String, Pair<String, Boolean>>
    fun restoreTasks(folder: String): Map<String, Pair<String, Boolean>>
    fun restoreFolder(key: String): Pair<String, Boolean>?
    fun restoreFolders(): Map<String, Pair<String, Boolean>>
}
