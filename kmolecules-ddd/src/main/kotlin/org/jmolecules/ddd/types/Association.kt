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
 * An association to an [AggregateRoot].
 *
 * Kotlin's counterpart of Java's [Association](https://github.com/xmolecules/jmolecules/blob/main/jmolecules-ddd/src/main/java/org/jmolecules/ddd/types/Association.java)
 *
 * @author Jocelyn Ntakpe
 * @since 1.0
 * See also: [John Sullivan - Advancing Enterprise DDD - Reinstating the Aggregate](https://scabl.blogspot.com/2015/04/aeddd-9.html)
 */
interface Association<T : AggregateRoot<T, ID>, ID : Identifier> : Identifiable<ID> {

    companion object {

        /**
         * Creates an [Association] pointing to the [Identifier] of the given [AggregateRoot].
         *
         * @param T the concrete [AggregateRoot] type.
         * @param ID the concrete [Identifier] type.
         * @param aggregate used to create the association
         * @return an [Association] pointing to the [Identifier] of the given [AggregateRoot].
         * @since 1.3
         */
        @JvmStatic
        fun <T : AggregateRoot<T, ID>, ID : Identifier> forAggregate(aggregate: T): Association<T, ID> {
            return SimpleAssociation { aggregate.id }
        }

        /**
         * Creates an [Association] pointing to the given [Identifier].
         *
         * @param T the concrete [AggregateRoot] type.
         * @param ID the concrete [Identifier] type.
         * @param identifier used to create the association
         * @return an [Association] pointing to the given [Identifier].
         * @since 1.3
         */
        @JvmStatic
        fun <T : AggregateRoot<T, ID>, ID : Identifier> forId(identifier: ID): Association<T, ID> {
            return SimpleAssociation { identifier }
        }
    }

    /**
     * Returns whether the current [Association] points to the same [AggregateRoot] as the given one. Unlike
     * [equals] and [hashCode] that also check for type equality of the [Association]
     * itself, this only compares the target [Identifier] instances.
     *
     * @param other association compared with the current instance
     * @return whether the current [Association] points to the same [AggregateRoot] as the given one.
     * @since 1.3
     */
    fun pointsToSameAggregateAs(other: Association<*, ID>): Boolean = id == other.id
}
