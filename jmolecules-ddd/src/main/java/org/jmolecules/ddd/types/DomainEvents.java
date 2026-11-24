/*
 * Copyright 2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jmolecules.ddd.types;

import org.jmolecules.stereotype.Stereotype;

import java.util.Collection;

/**
 * A collection of domain events to be used as a private field inside an {@link AggregateRoot}. Provides encapsulated
 * event registration that does not leak into the aggregate's public API.
 * <p>
 * This type follows the same pattern as {@link Association} and its package-private {@code SimpleAssociation}
 * implementation — a public interface with a static factory method hiding the concrete implementation.
 *
 * <pre>{@code
 * class Order implements AggregateRoot<Order, OrderId>, HasDomainEvents<Order, OrderEvent> {
 *     private final DomainEvents<OrderEvent> events = DomainEvents.create();
 *
 *     public void place() {
 *         events.register(new OrderPlaced(this.id));
 *     }
 * }
 * }</pre>
 *
 * @param <E> the domain event base type.
 * @author Amir Youssef
 * @see HasDomainEvents
 */
@Stereotype(priority = 20)
public interface DomainEvents<E> {

	/**
	 * Creates a new {@link DomainEvents} instance.
	 *
	 * @param <E> the domain event base type.
	 * @return a new {@link DomainEvents} instance, will never be {@literal null}.
	 */
	static <E> DomainEvents<E> create() {
		return new SimpleDomainEvents<>();
	}

	/**
	 * Registers the given domain event for later publication.
	 *
	 * @param event must not be {@literal null}.
	 */
	void register(E event);

	/**
	 * Returns all domain events that have been registered since the last {@link #clear()} call.
	 *
	 * @return a collection of registered events, never {@literal null}.
	 */
	Collection<E> getAll();

	/**
	 * Clears all domain events currently held.
	 */
	void clear();
}
