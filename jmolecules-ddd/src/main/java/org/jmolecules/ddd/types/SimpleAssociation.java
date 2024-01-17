/*
 * Copyright 2021-2024 the original author or authors.
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

import java.util.function.Supplier;

/**
 * Simple implementation of {@link Association} to effectively only define {@link #equals(Object)} and
 * {@link #hashCode()} on {@link Association}'s static factory methods.
 *
 * @author Oliver Drotbohm
 * @since 1.2
 * @see Association#forId(Identifier)
 * @see Association#forAggregate(AggregateRoot)
 */
class SimpleAssociation<T extends AggregateRoot<T, ID>, ID extends Identifier> implements Association<T, ID> {

	private final Supplier<ID> identifier;

	/**
	 * Creates a new {@link SimpleAssociation} for the given identifier.
	 *
	 * @param identifier must not be {@literal null}.
	 */
	SimpleAssociation(Supplier<ID> identifier) {

		if (identifier == null) {
			throw new IllegalArgumentException("Identifier must not be null!");
		}

		this.identifier = identifier;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jmolecules.ddd.types.Identifiable#getId()
	 */
	@Override
	public ID getId() {
		return identifier.get();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getId().toString();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (obj == null || !obj.getClass().equals(this.getClass())) {
			return false;
		}

		return ((Association<?, ?>) obj).getId().equals(identifier.get());
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
}
