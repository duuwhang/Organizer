package com.organizer.data

import com.organizer.SEPARATOR

class Folder(
    index: Int,
    name: String = "Add Tasks",
    completed: Boolean = false,
    var tasks: MutableList<Task> = mutableListOf()
) : Task(index, name, completed) {

    override fun toString() = super.toString() +
        if (tasks.isEmpty()) "" else tasks.joinToString(SEPARATOR, SEPARATOR) { it.identifier }
}
