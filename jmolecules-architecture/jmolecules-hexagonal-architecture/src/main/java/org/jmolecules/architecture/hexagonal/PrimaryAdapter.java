/*
 * Copyright 2022 the original author or authors.
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
package org.jmolecules.architecture.hexagonal;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A {@link PrimaryAdapter} connects the outside of an application to an {@link PrimaryPort} exposed by the
 * application's core. For example, it could be a component accepting HTTP requests or a listener for a message broker.
 *
 * @author Oliver Drotbohm
 * @author Stephan Pirnbaum
 * @see <a href="https://alistair.cockburn.us/hexagonal-architecture/">Hexagonal Architecture</a>
 * @see PrimaryPort
 * @since 1.5
 */
@Adapter
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.PACKAGE, ElementType.TYPE })
@Documented
public @interface PrimaryAdapter {

	/**
	 * An identifier for the name of the {@link PrimaryAdapter} to identify and group multiple implementing classes of the
	 * same {@link PrimaryAdapter}. If not set, external tooling may default this to the simple name of the annotated type
	 * or package.
	 *
	 * @since 1.8
	 */
	String name() default "";

	/**
	 * A description of the {@link PrimaryAdapter}. If not set, external tooling may default this to the JavaDoc.
	 *
	 * @since 1.8
	 */
	String description() default "";
}
