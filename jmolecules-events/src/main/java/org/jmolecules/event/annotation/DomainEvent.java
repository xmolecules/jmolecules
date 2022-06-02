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
 * A domain event is a full-fledged part of the domain model, a representation of something that happened in the domain.
 * It allows making the events that the domain experts want to track or be notified of explicit, or which are associated
 * with state change in the other model objects.
 *
 * @author Christian Stettler
 * @author Henning Schwentner
 * @author Stephan Pirnbaum
 * @author Martin Schimak
 * @author Oliver Drotbohm
 * @see <a href="https://domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf">Domain-Driven Design
 *      Reference (Evans) - Domain Events</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface DomainEvent {

	/**
	 * An identifier for the namespace of the event to group multiple events and let clients express their interest in all
	 * events of a specific namespace. If not set, external tooling may default this to the fully-qualified package name
	 * of the annotated type.
	 *
	 * @since 1.1
	 */
	String namespace() default "";

	/**
	 * An identifier for the name of the event used to abstract away from the type system and to guard against
	 * refactorings. If not set, external tooling may default this to the simple class name of the annotated type.
	 *
	 * @since 1.1
	 */
	String name() default "";

	/**
	 * Optional revision of the event. May be used in order to implement event upcasting.
	 * @see <a href="https://leanpub.com/esversioning/read#leanpub-auto-define-a-version-of-an-event>
	 * Versioning in an Event Sourced (Greg Young) - Define a Version of Event</a>
	 * @since 1.6
	 */
	String revision() default "";
}
