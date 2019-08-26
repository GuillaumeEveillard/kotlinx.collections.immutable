/*
 * Copyright 2016-2018 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kotlinx.collections.immutable.implementations.persistentOrderedMap

internal class PersistentOrderedMapBuilderEntries<K, V>(private val builder: PersistentOrderedMapBuilder<K, V>)
    : MutableSet<MutableMap.MutableEntry<K, V>>, AbstractMutableSet<MutableMap.MutableEntry<K, V>>() {
    override fun add(element: MutableMap.MutableEntry<K, V>): Boolean {
        throw UnsupportedOperationException()
    }

    override fun clear() {
        builder.clear()
    }

    override fun iterator(): MutableIterator<MutableMap.MutableEntry<K, V>> {
        return PersistentOrderedMapBuilderEntriesIterator(builder)
    }

    override fun remove(element: MutableMap.MutableEntry<K, V>): Boolean {
        // TODO: Eliminate this check after KT-30016 gets fixed.
        if ((element as Any?) !is MutableMap.MutableEntry<*, *>) return false
        return builder.remove(element.key, element.value)
    }

    override val size: Int
        get() = builder.size

    override fun contains(element: MutableMap.MutableEntry<K, V>): Boolean {
        // TODO: Eliminate this check after KT-30016 gets fixed.
        if ((element as Any?) !is MutableMap.MutableEntry<*, *>) return false
        return builder[element.key]?.let { candidate -> candidate == element.value }
                ?: (element.value == null && builder.containsKey(element.key))
    }
}

internal class PersistentOrderedMapBuilderKeys<K, V>(private val builder: PersistentOrderedMapBuilder<K, V>) : MutableSet<K>, AbstractMutableSet<K>() {
    override fun add(element: K): Boolean {
        throw UnsupportedOperationException()
    }

    override fun clear() {
        builder.clear()
    }

    override fun iterator(): MutableIterator<K> {
        return PersistentOrderedMapBuilderKeysIterator(builder)
    }

    override fun remove(element: K): Boolean {
        if (builder.containsKey(element)) {
            builder.remove(element)
            return true
        }
        return false
    }

    override val size: Int
        get() = builder.size

    override fun contains(element: K): Boolean {
        return builder.containsKey(element)
    }
}

internal class PersistentOrderedMapBuilderValues<K, V>(private val builder: PersistentOrderedMapBuilder<K, V>) : MutableCollection<V>, AbstractMutableCollection<V>() {
    override val size: Int
        get() = builder.size

    override fun contains(element: V): Boolean {
        return builder.containsValue(element)
    }

    override fun add(element: V): Boolean {
        throw UnsupportedOperationException()
    }

    override fun clear() {
        builder.clear()
    }

    override fun iterator(): MutableIterator<V> {
        return PersistentOrderedMapBuilderValuesIterator(builder)
    }
}