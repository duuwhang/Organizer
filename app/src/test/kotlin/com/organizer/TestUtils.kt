package com.organizer

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.opentest4j.AssertionFailedError

fun assertIterableNotEquals(expected: Iterable<*>, actual: Iterable<*>, message: String = "") {
    try {
        assertIterableEquals(expected, actual)
    } catch (_: AssertionFailedError) {
        return
    }

    if (message.isEmpty()) {
        throw AssertionFailedError("Iterable Contents do not differ, expected different Iterables.")
    } else {
        throw AssertionFailedError(message)
    }
}
