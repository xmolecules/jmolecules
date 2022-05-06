package org.jmolecules.ddd.types

/**
 * Identifies an [Entity]. Entities represent a thread of continuity and identity, going through a lifecycle,
 * though their attributes may change. Means of identification may come from the outside, or it may be an arbitrary
 * identifier created by and for the system, but it must correspond to the identity distinctions in the model. The model
 * must define what it means to be the same thing.
 *
 * Kotlin's counterpart of Java's [Entity](https://github.com/xmolecules/jmolecules/blob/main/jmolecules-ddd/src/main/java/org/jmolecules/ddd/types/Entity.java)
 *
 * @author Jocelyn Ntakpe
 * @since 1.0
 *
 * See also: [Domain-Driven Design Reference (Evans) - Entities](https://domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf)
 * See also: [John Sullivan - Advancing Enterprise DDD - Reinstating the Aggregate](https://scabl.blogspot.com/2015/04/aeddd-9.html)
 */
interface Entity<T : AggregateRoot<T, *>, ID> : Identifiable<ID>
