/*
 * Copyright 2020 the original author or authors.
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
package org.jmolecules.archunit;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Association;
import org.jmolecules.ddd.types.Entity;
import org.jmolecules.ddd.types.Identifier;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.EvaluationResult;

/**
 * Unit tests for JMoleculesRules.
 *
 * @author Oliver Drotbohm
 */
@AnalyzeClasses(packages = "org.jmolecules")
class JMoleculesRulesUnitTest {

	@ArchTest
	void detectsViolations(JavaClasses classes) {

		EvaluationResult result = JMoleculesRules.all().evaluate(classes);

		List<String> invalidProperties = Arrays.asList("invalid", //
				"invalidInCollection", //
				"invalidInMap", //
				"invalidAggregate", //
				"invalidAggregateInCollection", //
				"invalidAggregateInMap", //
				"invalidAnnotatedAggregate");

		assertThat(result.hasViolation()).isTrue();
		assertThat(result.getFailureReport().getDetails()).hasSize(invalidProperties.size());

		result.handleViolations((objects, message) -> {
			assertThat(objects).allSatisfy(it -> {
				assertThat(it).isInstanceOfSatisfying(JavaField.class, field -> {
					assertThat(field.getName()).isIn(invalidProperties);
				});
			});
		});
	}

	static class SampleIdentifier implements Identifier {}

	static abstract class SampleAggregate implements AggregateRoot<SampleAggregate, SampleIdentifier> {

		SampleEntity valid;

		OtherEntity invalid;
		Collection<OtherEntity> invalidInCollection;
		Map<String, OtherEntity> invalidInMap;

		OtherAggregate invalidAggregate;
		Collection<OtherAggregate> invalidAggregateInCollection;
		Map<String, OtherAggregate> invalidAggregateInMap;

		AnnotatedAggregate invalidAnnotatedAggregate;

		Association<OtherAggregate, SampleIdentifier> association;
	}

	static abstract class SampleEntity implements Entity<SampleAggregate, SampleIdentifier> {}

	static abstract class OtherAggregate implements AggregateRoot<OtherAggregate, SampleIdentifier> {}

	static abstract class OtherEntity implements Entity<OtherAggregate, SampleIdentifier> {}

	@org.jmolecules.ddd.annotation.AggregateRoot
	static class AnnotatedAggregate {}
}
