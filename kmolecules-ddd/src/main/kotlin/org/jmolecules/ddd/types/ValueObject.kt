package org.jmolecules.ddd.types

/**
 * Identifies a value object. Domain concepts that are modeled as value objects have no conceptual identity or
 * lifecycle. Implementations should be immutable, operations on it are side-effect free.
 *
 * Kotlin's counterpart of Java's [ValueObject](https://github.com/xmolecules/jmolecules/blob/main/jmolecules-ddd/src/main/java/org/jmolecules/ddd/types/ValueObject.java)
 *
 * @author Dennis Effing
 * @see [Domain-Driven Design Reference (Evans) - Value objects](https://domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf)
 * @since 1.9
 */
interface ValueObject
