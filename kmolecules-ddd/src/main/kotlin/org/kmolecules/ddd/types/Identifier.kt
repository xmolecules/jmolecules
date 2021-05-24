package org.kmolecules.ddd.types

/**
 * Marker interface for identifiers. Exists primarily to easily identify types that are supposed to be identifiers
 * within the code base and let the compiler verify the correctness of declared relationships.
 *
 * Kotlin's counterpart of Java's [Identifier](https://github.com/xmolecules/jmolecules/blob/main/jmolecules-ddd/src/main/java/org/jmolecules/ddd/types/Identifier.java)
 *
 * @author Jocelyn Ntakpe
 * @since 1.3
 * @see Identifiable
 */
interface Identifier
