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
import java.util.Collections;

/**
 * Interface to be implemented by {@link AggregateRoot}s that expose domain events for deferred publication by the
 * infrastructure.
 * <p>
 * This interface defines the infrastructure-facing contract for reading and clearing domain events. How events are
 * registered internally is the aggregate's own responsibility — Java interfaces cannot express non-public methods, so
 * event registration is intentionally left out to preserve DDD encapsulation.
 * <p>
 * The infrastructure (e.g., a {@link Repository} or Transaction Interceptor) calls {@link #getDomainEvents()} after
 * the transaction commits, publishes the events, and then calls {@link #clearDomainEvents()}.
 *
 * @param <T> the concrete {@link AggregateRoot} type.
 * @param <E> the domain event base type.
 * @author Amir Youssef
 * @see <a href="https://www.domainlanguage.com/ddd/reference/">Domain-Driven Design Reference (Evans) - Domain
 * Events</a>
 * @see DomainEvents
 * @see <a href="https://vaughnvernon.co/?p=838">Vaughn Vernon - Modeling Aggregates with Domain Events</a>
 */
@Stereotype(priority = 20)
public interface HasDomainEvents<T extends AggregateRoot<T, ?>, E> {

    /**
     * Returns all domain events that have been registered since the last {@link #clearDomainEvents()} call.
     *
     * @return a collection of registered events, never {@literal null}.
     */
    default Collection<E> getDomainEvents() {
        return Collections.emptyList();
    }

    /**
     * Clears all domain events currently held. Typically invoked by the infrastructure after events have been published.
     */
    default void clearDomainEvents() {
    }

}
