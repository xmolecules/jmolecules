package org.jmolecules.ddd.annotation

/**
 * Identifies a [Factory]. Factories encapsulate the responsibility of creating complex objects in general and
 * Aggregates in particular. Objects returned by the factory methods are guaranteed to be in valid state.
 *
 * Kotlin's counterpart of the Java's [Factory](https://github.com/xmolecules/jmolecules/blob/main/jmolecules-ddd/src/main/java/org/jmolecules/ddd/annotation/Factory.java)
 *
 * @author Jocelyn Ntakpe
 *
 * See also : [AggregateRoot]
 * See also : [Domain-Driven Design Reference (Evans) - Factories](https://domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf)
 */
@Retention
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
annotation class Factory
