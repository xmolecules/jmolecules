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

/**
 * An association to an {@link AggregateRoot}.
 *
 * @author Christian Stettler
 * @author Henning Schwentner
 * @author Stephan Pirnbaum
 * @author Martin Schimak
 * @author John Sullivan
 * @author Oliver Drotbohm
 * @see <a href="https://scabl.blogspot.com/2015/04/aeddd-9.html">John Sullivan - Advancing Enterprise DDD - Reinstating
 *      the Aggregate</a>
 */
public interface Association<T extends AggregateRoot<T, ID>, ID extends Identifier> extends Identifiable<ID> {

	/**
	 * Creates an {@link Association} pointing to the {@link Identifier} of the given {@link AggregateRoot}.
	 *
	 * @param <T> the concrete {@link AggregateRoot} type.
	 * @param <ID> the concrete {@link Identifier} type.
	 * @param aggregate must not be {@literal null}.
	 * @return an {@link Association} pointing to the {@link Identifier} of the given {@link AggregateRoot}, will never be
	 *         {@literal null}.
	 * @since 1.2
	 */
	static <T extends AggregateRoot<T, ID>, ID extends Identifier> Association<T, ID> forAggregate(T aggregate) {

		if (aggregate == null) {
			throw new IllegalArgumentException("Aggregate must not be null!");
		}

		return new SimpleAssociation<>(() -> aggregate.getId());
	}

	/**
	 * Creates an {@link Association} pointing to the given {@link Identifier}.
	 *
	 * @param <T> the concrete {@link AggregateRoot} type.
	 * @param <ID> the concrete {@link Identifier} type.
	 * @param identifier must not be {@literal null}.
	 * @return an {@link Association} pointing to the given {@link Identifier}, will never be {@literal null}.
	 * @since 1.2
	 */
	static <T extends AggregateRoot<T, ID>, ID extends Identifier> Association<T, ID> forId(ID identifier) {

		if (identifier == null) {
			throw new IllegalArgumentException("Identifier must not be null!");
		}

		return new SimpleAssociation<>(() -> identifier);
	}

	/**
	 * Returns whether the current {@link Association} points to the same {@link AggregateRoot} as the given one. Unlike
	 * {@link #equals(Object)} and {@link #hashCode()} that also check for type equality of the {@link Association}
	 * itself, this only compares the target {@link Identifier} instances.
	 *
	 * @param other must not be {@literal null}.
	 * @return whether the current {@link Association} points to the same {@link AggregateRoot} as the given one.
	 * @since 1.2
	 */
	default boolean pointsToSameAggregateAs(Association<?, ID> other) {

		if (other == null) {
			throw new IllegalArgumentException("Other association must not be null!");
		}

		return getId().equals(other.getId());
	}

	/**
	 * Returns whether the current {@link Association} points to the {@link AggregateRoot} with the given
	 * {@link Identifier}. Unlike {@link #equals(Object)} and {@link #hashCode()} that also check for type equality of the
	 * {@link Association} itself, this only compares the target {@link Identifier} instances.
	 *
	 * @param identifier
	 * @return whether the current {@link Association} points to the {@link AggregateRoot} with the given
	 *         {@link Identifier}.
	 * @since 1.4
	 */
	default boolean pointsTo(ID identifier) {

		if (identifier == null) {
			throw new IllegalArgumentException("Identifier must not be null!");
		}

		return getId().equals(identifier);
	}

	/**
	 * Returns whether the current {@link Association} points to the given {@link AggregateRoot}. Unlike
	 * {@link #equals(Object)} and {@link #hashCode()} that also check for type equality of the {@link Association}
	 * itself, this only compares the target {@link Identifier} instances.
	 *
	 * @param aggregate must not be {@literal null}.
	 * @return whether the current {@link Association} points to the given {@link AggregateRoot}.
	 * @since 1.4
	 */
	default boolean pointsTo(T aggregate) {

		if (aggregate == null) {
			throw new IllegalArgumentException("Aggregate must not be null!");
		}

		return pointsTo(aggregate.getId());
	}
}
