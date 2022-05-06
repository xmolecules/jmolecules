package org.jmolecules.ddd.annotation

/**
 * Identifies a value object. Domain concepts that are modeled as value objects have no conceptual identity or
 * lifecycle. Implementations should be immutable, operations on it are side effect free.
 *
 * Kotlin's counterpart of the Java's [ValueObject](https://github.com/xmolecules/jmolecules/blob/main/jmolecules-ddd/src/main/java/org/jmolecules/ddd/annotation/ValueObject.java)
 *
 * @author Jocelyn Ntakpe
 *
 * See also : [Domain-Driven Design Reference (Evans) - Value objects](https://domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf)
 */
@Retention
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
annotation class ValueObject
