/*
 * Copyright 2020 the original author or authors.
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
 * An abstract base for entities.
 * Provides implementations for {@code equals()}, {@code hashCode()}, and {@code toString()} that are based on the identity of the entity.
 */
@org.jmolecules.ddd.annotation.Entity
public abstract class AbstractEntity<R extends AggregateRoot<R, ?>, ID> implements Entity<R, ID> {

	private final ID identity;

	protected AbstractEntity(ID identity) {
		if (identity == null) {
			throw new IllegalArgumentException("identity must not be null");
		}
			
		this.identity = identity;
	}

	@Override
	public final ID getId() {
		return identity;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + identity + "]";
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		AbstractEntity other = (AbstractEntity) obj;
		if (identity == null) {
			if (other.identity != null)
				return false;
		} else if (!identity.equals(other.identity))
			return false;
		return true;
	}

	@Override
	public final int hashCode() {
		return identity.hashCode();
	}

}
