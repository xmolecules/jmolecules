/*
 * Copyright 2020-2021 the original author or authors.
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
package org.jmolecules.event.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a domain event publisher, i.e. logic to publish a {@link DomainEvent}.
 *
 * @author Christian Stettler
 * @author Henning Schwentner
 * @author Stephan Pirnbaum
 * @author Martin Schimak
 * @author Oliver Drotbohm
 * @since 1.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Documented
public @interface DomainEventPublisher {

	/**
	 * Optional identification of the domain event published by this publisher. This information may be used for easier
	 * linkage between event and publisher by external tools and refers to the combination of
	 * {@link DomainEvent#namespace()} and {@link DomainEvent#name()}, separated by '.' (dot)
	 */
	String publishes() default "";

	/**
	 * Marks the type of the publisher, i.e. if the publisher externalizes events or if it creates events for VM internal
	 * consumption such as application events.
	 */
	PublisherType type() default PublisherType.UNDEFINED;

	enum PublisherType {

		/**
		 * Expresses that the events published are intended for internal usage.
		 */
		INTERNAL,

		/**
		 * Expresses that the events published are intended for external usage.
		 */
		EXTERNAL,

		/**
		 * Expresses that the target audience of the events is undefined (default).
		 */
		UNDEFINED;
	}
}
