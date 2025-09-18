/*
 * Copyright 2022-2024 the original author or authors.
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
package org.jmolecules.ddd.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jmolecules.stereotype.Stereotype;

/**
 * An association to an {@link org.jmolecules.ddd.annotation.AggregateRoot}.
 *
 * @author Simon Zambrovski
 * @author Oliver Drotbohm
 * @see <a href="https://scabl.blogspot.com/2015/04/aeddd-9.html>John Sullivan - Advancing Enterprise DDD - Reinstating
 *      the Aggregate</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Documented
@Stereotype(priority = 0)
public @interface Association {

	/**
	 * Defines the aggregate type.
	 *
	 * @return the of the aggregate.
	 */
	Class<?> aggregateType() default void.class;
}
