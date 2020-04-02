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
package org.jddd.archunit;

import static com.tngtech.archunit.base.DescribedPredicate.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import org.jddd.core.types.AggregateRoot;
import org.jddd.core.types.Association;
import org.jddd.core.types.Entity;
import org.springframework.core.ResolvableType;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.properties.CanBeAnnotated;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.CompositeArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

/**
 * A set of ArchUnit rules that allow verification of domain models. In short the rules here verify:
 * <ul>
 * <li>Aggregates only refer to entities that are declared to be part of it.</li>
 * <li>References to other aggregates are established via {@link Association}s or idetifier references.</li>
 * </ul>
 * Those rules are mostly driven by what's been presented by John Sullivan in his blog post
 * <a href="http://scabl.blogspot.com/2015/04/aeddd-9.html">here</a>.
 *
 * @author Oliver Drotbohm
 * @see http://scabl.blogspot.com/2015/04/aeddd-9.html
 */
public class JDddRules {

	/**
	 * An {@link ArchRule} that's composed of all other rules declared in this class.
	 *
	 * @return
	 * @see #entitiesShouldBeDeclaredForUseInSameAggregate()
	 * @see #aggregateReferencesShouldBeViaIdOrAssociation()
	 */
	public static ArchRule all() {

		return CompositeArchRule //
				.of(entitiesShouldBeDeclaredForUseInSameAggregate()) //
				.and(aggregateReferencesShouldBeViaIdOrAssociation());
	}

	/**
	 * An {@link ArchRule} that verifies that fields that implement {@link Entity} within a type implementing
	 * {@link AggregateRoot} declare the aggregate type as the owning aggregate.
	 * <p />
	 * <code>
	 * class Customer implements AggregateRoot<Customer, CustomerId> { … }
	 * class Address implements Entity<Customer, AddressId> { … }
	 *
	 * class LineItem implements Entity<Order, LineItemId> { … }
	 * class Order implements AggregateRoot<Order, OrderId> {
	 *
	 *   List<LineItem> lineItems; // valid
	 *   Address shippingAddress; // invalid as Address is declared to belong to Customer
	 * }
	 * </code>
	 *
	 * @return will never be {@literal null}.
	 */
	public static ArchRule entitiesShouldBeDeclaredForUseInSameAggregate() {

		return ArchRuleDefinition.fields() //
				.that(areAssignableTo(Entity.class).and(not(areAssignableTo(AggregateRoot.class)))) //
				.should(beDeclaredToBeUsedWithDeclaringAggregate()); //
	}

	/**
	 * An {@link ArchRule} that ensures that one {@link AggregateRoot} does not reference another via the remote
	 * AggregateRoot type but rather via their identifier type or an explicit {@link Association} type.
	 * <p />
	 * <code>
	 * class Customer implements AggregateRoot<Customer, CustomerId> { … }
	 *
	 * class Order implements AggregateRoot<Order, OrderId> {
	 *
	 *   Customer customer; // invalid
	 *   CustomerId customerId; // valid
	 *   Association<Customer> customer; // valid
	 * }
	 * </code>
	 *
	 * @return will never be {@literal null}.
	 */
	public static ArchRule aggregateReferencesShouldBeViaIdOrAssociation() {

		return ArchRuleDefinition.fields() //
				.that(areAssignableTo(AggregateRoot.class))
				.or(hasFieldTypeAnnotatedWith(org.jddd.core.annotation.AggregateRoot.class)) //
				.should(new ShouldUseIdReferenceOrAssociation());
	}

	private static IsDeclaredToUseTheSameAggregate beDeclaredToBeUsedWithDeclaringAggregate() {
		return new IsDeclaredToUseTheSameAggregate();
	}

	private static IsAssignableTypeField areAssignableTo(Class<?> type) {
		return new IsAssignableTypeField(type);
	}

	private static FieldTypeIsAnnotatedWith hasFieldTypeAnnotatedWith(Class<? extends Annotation> type) {
		return new FieldTypeIsAnnotatedWith(type);
	}

	private static class IsDeclaredToUseTheSameAggregate extends ArchCondition<JavaField> {

		private IsDeclaredToUseTheSameAggregate() {
			super("belong to aggregate the field is declared in", new Object[] {});
		}

		/*
		 * (non-Javadoc)
		 * @see com.tngtech.archunit.lang.ArchCondition#check(java.lang.Object, com.tngtech.archunit.lang.ConditionEvents)
		 */
		@Override
		public void check(JavaField item, ConditionEvents events) {

			Field field = item.reflect();
			ResolvableType type = ResolvableType.forField(field);
			ResolvableType expectedAggregateType = type.as(Entity.class).getGeneric(0);
			ResolvableType owningType = ResolvableType.forClass(field.getDeclaringClass());

			String ownerName = FormatableJavaClass.of(item.getOwner()).getAbbreviatedFullName();

			events.add(owningType.isAssignableFrom(expectedAggregateType) ? SimpleConditionEvent.satisfied(field, "Matches")
					: SimpleConditionEvent.violated(item,
							String.format("Field %s.%s is of type %s and declared to be used from aggregate %s!", ownerName,
									item.getName(), item.getRawType().getSimpleName(),
									expectedAggregateType.resolve(Object.class).getSimpleName())));
		}
	}

	private static class ShouldUseIdReferenceOrAssociation extends ArchCondition<JavaField> {

		public ShouldUseIdReferenceOrAssociation() {
			super("use id reference or Association", new Object[0]);
		}

		/*
		 * (non-Javadoc)
		 * @see com.tngtech.archunit.lang.ArchCondition#check(java.lang.Object, com.tngtech.archunit.lang.ConditionEvents)
		 */
		@Override
		public void check(JavaField field, ConditionEvents events) {

			events.add(SimpleConditionEvent.violated(field,
					String.format(
							"Field %s.%s refers to an aggregate root (%s). Rather use an identifier reference or Association!",
							FormatableJavaClass.of(field.getOwner()).getAbbreviatedFullName(), field.getName(),
							FormatableJavaClass.of(field.getRawType()).getAbbreviatedFullName())));
		}
	}

	private static class FieldTypeIsAnnotatedWith extends DescribedPredicate<JavaField> {

		private final DescribedPredicate<CanBeAnnotated> isAnnotatedWith;

		public FieldTypeIsAnnotatedWith(Class<? extends Annotation> type) {

			super("is of type annotated with %s", type.getSimpleName());

			this.isAnnotatedWith = CanBeAnnotated.Predicates.annotatedWith(type);
		}

		/*
		 * (non-Javadoc)
		 * @see com.tngtech.archunit.base.Predicate#apply(java.lang.Object)
		 */
		@Override
		public boolean apply(JavaField input) {
			return isAnnotatedWith.apply(input.getRawType());
		}
	}

	private static class IsAssignableTypeField extends DescribedPredicate<JavaField> {

		private static final ResolvableType COLLECTION_TYPE = ResolvableType.forClass(Collection.class);
		private static final ResolvableType MAP_TYPE = ResolvableType.forClass(Map.class);

		private final Class<?> type;

		private IsAssignableTypeField(Class<?> type) {
			super("are assignable to %s", new Object[] { type.getName() });
			this.type = type;
		}

		/*
		 * (non-Javadoc)
		 * @see com.tngtech.archunit.base.Predicate#apply(java.lang.Object)
		 */
		public boolean apply(JavaField input) {

			ResolvableType fieldType = ResolvableType.forField(input.reflect());
			ResolvableType domainType = unwrapDomainType(fieldType);

			return ResolvableType.forClass(type).isAssignableFrom(domainType);
		}

		private static ResolvableType unwrapDomainType(ResolvableType fieldType) {

			if (COLLECTION_TYPE.isAssignableFrom(fieldType)) {
				return fieldType.as(Collection.class).getGeneric(0);
			}

			if (MAP_TYPE.isAssignableFrom(fieldType)) {
				return fieldType.as(Map.class).getGeneric(1);
			}

			return fieldType;
		}
	}
}
