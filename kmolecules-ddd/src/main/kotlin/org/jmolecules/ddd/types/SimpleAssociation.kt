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

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String = id.toString()
}
