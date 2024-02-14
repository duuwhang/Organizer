package com.organizer.data

import com.organizer.DEFAULT_MESSAGE
import com.organizer.SEPARATOR
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

@Suppress("EqualsOrHashCode")
open class Task(
    val index: Int,
    var name: String = DEFAULT_MESSAGE,
    completed: Boolean = false
) {

    var completed: Boolean = completed
        set(value) {
            field = value
            observers.forEach { it.invoke(value) }
        }

    private val observers: MutableSet<(Boolean) -> Unit> = mutableSetOf()

    fun observeCompleted(observer: (Boolean) -> Unit) {
        observers.add(observer)
    }

    val identifier = javaClass.simpleName.lowercase() + index

    override fun equals(other: Any?): Boolean {
        if (other == null || !other::class.isInstance(this)) return false
        other::class.memberProperties.forEach { property ->
            this::class.memberProperties.filter { property.name == it.name }.forEach {
                it.isAccessible = true
                if (it.getter.call(this) != property.getter.call(other)) return false
            }
        }
        return true
    }

    override fun toString() = name + SEPARATOR + completed.compareTo(false)
}
