/*
 * Copyright 2022-2024 the original author or authors.
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
package org.jmolecules.architecture.hexagonal;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link Adapter}s contain technology specific implementations to either drive (see {@link PrimaryPort}) or implement
 * {@link Port}s (see {@link SecondaryPort}). Adapters must not depend on {@link Application} code other than ports.
 *
 * @author Oliver Drotbohm
 * @author Stephan Pirnbaum
 * @see <a href="https://alistair.cockburn.us/hexagonal-architecture/">Hexagonal Architecture</a>
 * @see Application
 * @see Port
 * @see PrimaryPort
 * @see SecondaryPort
 * @since 1.5
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PACKAGE, ElementType.TYPE })
@Documented
public @interface Adapter {

	/**
	 * An identifier for the name of the {@link Adapter} to identify and group multiple implementing classes of the same
	 * {@link Adapter}. If not set, external tooling may default this to the simple name of the annotated type or package.
	 *
	 * @since 1.8
	 */
	String name() default "";

	/**
	 * A description of the {@link Adapter}. If not set, external tooling may default this to the JavaDoc.
	 *
	 * @since 1.8
	 */
	String description() default "";
}
