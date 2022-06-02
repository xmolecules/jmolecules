package org.jmolecules.ddd.annotation

/**
 * Identifies a domain [Service]. A service is a significant process or transformation in the domain that is not a
 * natural responsibility of an entity or value object, add an operation to the model as a standalone interface declared
 * as a service. Define a service contract, a set of assertions about interactions with the service. (See assertions.)
 * State these assertions in the ubiquitous language of a specific bounded context. Give the service a name, which also
 * becomes part of the ubiquitous language.
 *
 * Kotlin's counterpart of the Java's [Service](https://github.com/xmolecules/jmolecules/blob/main/jmolecules-ddd/src/main/java/org/jmolecules/ddd/annotation/Service.java)
 *
 * @author Jocelyn Ntakpe
 */
@Retention
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
annotation class Service
