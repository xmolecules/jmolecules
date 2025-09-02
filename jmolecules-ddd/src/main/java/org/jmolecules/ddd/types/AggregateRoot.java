/*
 * Copyright 2020-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jmolecules.ddd.types;

import org.jmolecules.stereotype.Stereotype;

/**
 * Identifies an aggregate root, i.e. the root entity of an aggregate. An aggregate forms a cluster of consistent rules
 * usually formed around a set of entities by defining invariants based on the properties of the aggregate that have to
 * be met before and after operations on it. Aggregates usually refer to other aggregates by their identifier.
 * References to aggregate internals should be avoided and at least not considered strongly consistent (i.e. a reference
 * held could possibly have been gone or become invalid at any point in time). They also act as scope of consistency,
 * i.e. changes on a single aggregate are expected to be strongly consistent while changes across multiple ones should
 * only expect eventual consistency.
 *
 * @author Christian Stettler
 * @author Henning Schwentner
 * @author Stephan Pirnbaum
 * @author Martin Schimak
 * @author John Sullivan
 * @author Oliver Drotbohm
 * @see <a href="https://domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf">Domain-Driven Design
 *      Reference (Evans) - Aggregates</a>
 * @see <a href="https://scabl.blogspot.com/2015/04/aeddd-9.html>John Sullivan - Advancing Enterprise DDD - Reinstating
 *      the Aggregate</a>
 */
@Stereotype(priority = 0)
public interface AggregateRoot<T extends AggregateRoot<T, ID>, ID extends Identifier> extends Entity<T, ID> {

}
