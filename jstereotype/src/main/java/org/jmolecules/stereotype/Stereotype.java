/*
 * Copyright 2025 the original author or authors.
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
package org.jmolecules.stereotype;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Declares a type or annotation to represent a stereotypical design or software architecture element in a code base.
 *
 * @author Oliver Drotbohm
 * @since 2.0
 */
@Retention(RUNTIME)
@Target({ TYPE, ANNOTATION_TYPE })
@Documented
public @interface Stereotype {

	/**
	 * Alias of the {@link #name()} attribute.
	 */
	String value() default "";

	/**
	 * A human-readable name of the stereotype. Defaults to the simple name of the annotated type or concrete annotation
	 * respectively.
	 */
	String name() default "";

	/**
	 * The identifier of the stereotype. Defaults to the fully qualified name of the annotated type or concrete annotation
	 * respectively.
	 */
	String id() default "";

	/**
	 * The logical identifiers of the groups a stereotype belongs to.
	 */
	String[] groups() default {};

	/**
	 * A priority (lower value implies higher priority) of the stereotype.
	 */
	int priority() default 0;

	/**
	 * Configures whether the stereotype is only detected on the type or method itself or by inspecting the entire
	 * inheritance hierarchy. Defaults to {@literal true}.
	 */
	boolean inherited() default true;
}
