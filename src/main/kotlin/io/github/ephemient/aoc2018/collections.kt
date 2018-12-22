package io.github.ephemient.aoc2018

inline fun <T> MutableIterable<T>.removeFirst(
    predicate: (T) -> Boolean = { true }
): T? {
    val iterator = iterator()
    while (iterator.hasNext()) {
        val next = iterator.next()
        if (predicate(next)) {
            iterator.remove()
            return next
        }
    }
    return null
}

inline fun <K, V> MutableMap<K, V>.removeFirst(
    predicate: (K, V) -> Boolean = { _, _ -> true }
): Map.Entry<K, V>? {
    val iterator = iterator()
    while (iterator.hasNext()) {
        val (key, value) = iterator.next()
        if (predicate(key, value)) {
            iterator.remove()
            return SimpleMapEntry<K, V>(key, value)
        }
    }
    return null
}

data class SimpleMapEntry<K, V>(override val key: K, override val value: V) : Map.Entry<K, V>