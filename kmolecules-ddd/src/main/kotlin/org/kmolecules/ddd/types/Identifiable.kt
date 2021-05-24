package org.kmolecules.ddd.types

/**
 * An identifiable type, i.e. anything that exposes an [Identifier].
 *
 * Kotlin's counterpart of Java's [Identifiable](https://github.com/xmolecules/jmolecules/blob/main/jmolecules-ddd/src/main/java/org/jmolecules/ddd/types/Identifiable.java)
 *
 * @author Jocelyn Ntakpe
 * @since 1.3
 */
interface Identifiable<ID> {

    /**
     * Identifier
     */
    val id: ID
}
