/*
 * Copyright 2022-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jmolecules.ddd.types

/**
 * Simple implementation of [Association] to effectively only define [equals] and
 * [hashCode] on [Association]'s static factory methods.
 *
 * Kotlin's equivalent of Java's [SimpleAssociation](https://github.com/xmolecules/jmolecules/blob/main/jmolecules-ddd/src/main/java/org/jmolecules/ddd/types/SimpleAssociation.java)
 *
 * @author Jocelyn Ntakpe
 * @since 1.0
 * @see Association.forId
 * @see Association.forAggregate
 */
class SimpleAssociation<T : AggregateRoot<T, ID>, ID : Identifier>(private val identifier: () -> ID) :
        Association<T, ID> {

    override val id: ID
        get() = identifier()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SimpleAssociation<*, *>

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String = id.toString()
}
