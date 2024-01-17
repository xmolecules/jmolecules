/*
 * Copyright 2020-2024 the original author or authors.
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
 * Identifies a domain event handler, i.e. logic to process a {@link DomainEvent}.
 *
 * @author Christian Stettler
 * @author Henning Schwentner
 * @author Stephan Pirnbaum
 * @author Martin Schimak
 * @author Oliver Drotbohm
 * @since 1.1
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
public @interface DomainEventHandler {

	/**
	 * Optional identification of the namespace of the domain event handled by this handler. This information may be used
	 * for easier linkage between event and handler by external tools and refers to {@link DomainEvent#namespace()}. When
	 * leaving the default value, it is assumed that the method signature makes clear what event is consumed. If the
	 * handler takes care of all events of a specific namespace, the value of this field needs to be set to the respective
	 * namespace and the {@link DomainEventHandler#name()} needs to be set accordingly. If the handler doesn't care about
	 * the namespace, the value may be set to the '*' (asterisk) placeholder.
	 */
	String namespace() default "";

	/**
	 * Optional identification of the name of the domain event handled by this handler. This information may be used for
	 * easier linkage between event and handler by external tools and refers to {@link DomainEvent#name()}. When leaving
	 * the default value, it is assumed that the method signature makes clear what event is consumed. If the handler takes
	 * care of all events of a specific namespace, the value of this field needs to be set to the '*' asterisk
	 * placeholder.
	 */
	String name() default "";
}
