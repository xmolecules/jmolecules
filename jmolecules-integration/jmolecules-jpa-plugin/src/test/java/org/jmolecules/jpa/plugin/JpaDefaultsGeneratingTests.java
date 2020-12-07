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
package org.jmolecules.jpa.plugin;

import static org.assertj.core.api.Assertions.*;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.junit.jupiter.api.Test;

/**
 * @author Oliver Drotbohm
 */
public class JpaDefaultsGeneratingTests {

	@Test
	public void defaultsAggregateType() throws Exception {

		// Defaults @Entity
		assertThat(SampleAggregate.class.getDeclaredAnnotations())
				.filteredOn(it -> it.annotationType().equals(Entity.class))
				.hasSize(1);

		// Adds default constructor
		assertThat(SampleEntity.class.getDeclaredConstructors()).isNotNull();

		// Defaults @EmbeddedId
		assertThat(SampleAggregate.class.getDeclaredField("id").getAnnotations())
				.filteredOn(it -> it.annotationType().equals(EmbeddedId.class))
				.hasSize(1);

		// Defaults OneToOne annotation
		assertThat(SampleAggregate.class.getDeclaredField("entity").getAnnotation(OneToOne.class))
				.isNotNull()
				.satisfies(it -> assertThat(it.cascade()).containsExactly(CascadeType.ALL));

		// Defaults collection of entities to @OneToMany(cascade = CascadeType.ALL)
		assertThat(SampleAggregate.class.getDeclaredField("listOfEntity").getAnnotation(OneToMany.class))
				.isNotNull()
				.satisfies(it -> assertThat(it.cascade()).containsExactly(CascadeType.ALL));
	}

	@Test
	void defaultsForEntity() {

		// Defaults @Entity
		assertThat(SampleEntity.class.getDeclaredAnnotations())
				.filteredOn(it -> it.annotationType().equals(Entity.class))
				.hasSize(1);

		// Adds default constructor
		assertThat(SampleEntity.class.getDeclaredConstructors()).isNotNull();
	}

	@Test
	void defaultsIdentifier() {

		assertThat(Serializable.class).isAssignableFrom(SampleAggregateIdentifier.class);
		assertThat(SampleAggregateIdentifier.class.getAnnotations())
				.filteredOn(it -> it.annotationType().equals(Embeddable.class))
				.hasSize(1);
	}
}
