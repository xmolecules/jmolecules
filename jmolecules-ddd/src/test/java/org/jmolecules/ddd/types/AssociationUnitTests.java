/*
 * Copyright 2021 the original author or authors.
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

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Association}.
 *
 * @author Oliver Drotbohm
 */
public class AssociationUnitTests {

	@Test // #48
	void createsAssociationFromIdentifier() {

		SampleIdentifier id = new SampleIdentifier();

		assertThat(Association.forId(id).getId()).isEqualTo(id);
	}

	@Test // #48
	void createsAssoctionFromAggregate() {

		SampleIdentifier identifier = new SampleIdentifier();
		SampleAggregate aggregate = new SampleAggregate(identifier);

		assertThat(Association.forAggregate(aggregate).getId()).isEqualTo(identifier);
	}

	@Test // #48
	void rejectsNullIdentifier() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> Association.forId(null));
	}

	@Test // #48
	void rejectsNullAggregate() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> Association.forAggregate(null));
	}

	@Test // #48
	void associationsEqualIfTheyPointToTheSameId() {

		SampleIdentifier identifier = new SampleIdentifier();
		SampleAggregate aggregate = new SampleAggregate(identifier);

		assertThat(Association.forId(identifier)).isEqualTo(Association.forAggregate(aggregate));
	}

	@Test // #48
	void pointsToSameAggregate() {

		SampleIdentifier identifier = new SampleIdentifier();
		Association<?, SampleIdentifier> simpleAssociation = Association.forId(identifier);
		SampleAssociation sampleAssociation = new SampleAssociation(identifier);

		assertThat(simpleAssociation.pointsToSameAggregateAs(sampleAssociation));
		assertThat(sampleAssociation.pointsToSameAggregateAs(simpleAssociation));
	}

	@Test // #48
	void rejectsNullReferenceAggregate() {

		assertThatIllegalArgumentException()
				.isThrownBy(() -> Association.forId(new SampleIdentifier()).pointsToSameAggregateAs(null));
	}

	static class SampleIdentifier implements Identifier {}

	static class SampleAggregate implements AggregateRoot<SampleAggregate, SampleIdentifier> {

		private final SampleIdentifier id;

		SampleAggregate(SampleIdentifier id) {
			this.id = id;
		}

		/*
		 * (non-Javadoc)
		 * @see org.jmolecules.ddd.types.Identifiable#getId()
		 */
		@Override
		public SampleIdentifier getId() {
			return id;
		}
	}

	static class SampleAssociation implements Association<SampleAggregate, SampleIdentifier> {

		SampleIdentifier id;

		public SampleAssociation(SampleIdentifier id) {
			this.id = id;
		}

		/*
		 * (non-Javadoc)
		 * @see org.jmolecules.ddd.types.Identifiable#getId()
		 */
		@Override
		public SampleIdentifier getId() {
			return id;
		}
	}
}
