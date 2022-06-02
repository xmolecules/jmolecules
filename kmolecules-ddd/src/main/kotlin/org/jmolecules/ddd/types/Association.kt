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
