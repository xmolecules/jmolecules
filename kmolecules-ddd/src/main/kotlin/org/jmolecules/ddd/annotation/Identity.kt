package org.jmolecules.ddd.annotation

/**
 * Declares a field (or a getter) of a class to constitute the identity of the corresponding class. Primarily used in
 *  [AggregateRoot] [Entity] types.
 *
 * Kotlin's counterpart of the Java's [Entity](https://github.com/xmolecules/jmolecules/blob/main/jmolecules-ddd/src/main/java/org/jmolecules/ddd/annotation/Identity.java)
 *
 * @author Oliver Drotbohm
 * @author Stephan Pirnbaum
 * @author Henning Schwentner
 *
 * See also : [Domain-Driven Design Reference (Evans) - Entities](https://domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf)
 */
@Retention
@MustBeDocumented
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.PROPERTY_GETTER
)
annotation class Identity
