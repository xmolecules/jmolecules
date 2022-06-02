package org.jmolecules.ddd.annotation

/**
 * Identifies a [Repository]. Repositories simulate a collection of aggregates to which aggregate instances can be
 * added and removed. They usually also expose API to select a subset of aggregates matching certain criteria. Access to
 * projections of an aggregate might be provided as well but also via a dedicated separate abstraction.
 *
 * Implementations use a dedicated persistence mechanism appropriate to the data structure and query requirements at
 * hand. However, they should make sure that no persistence mechanism specific APIs leak into client code.
 *
 * Kotlin's counterpart of the Java's [Repository](https://github.com/xmolecules/jmolecules/blob/main/jmolecules-ddd/src/main/java/org/jmolecules/ddd/annotation/Repository.java)
 *
 * @author Jocelyn Ntakpe
 *
 * See also : [AggregateRoot]
 * See also : [Domain-Driven Design Reference (Evans) - Repositories](https://domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf)
 */
@Retention
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
annotation class Repository
