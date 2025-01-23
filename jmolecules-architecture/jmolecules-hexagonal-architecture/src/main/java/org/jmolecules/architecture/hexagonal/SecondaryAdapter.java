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
package org.jmolecules.architecture.hexagonal;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jmolecules.stereotype.Stereotype;

/**
 * {@link SecondaryAdapter}s implement {@link SecondaryPort} to ultimately link the applications core to some extrenal
 * technology, like a database, message broker, email server or third-party service.
 *
 * @author Oliver Drotbohm
 * @author Stephan Pirnbaum
 * @see SecondaryPort
 */
@Adapter
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PACKAGE, ElementType.TYPE })
@Documented
@Stereotype(priority = 200)
public @interface SecondaryAdapter {

	/**
	 * An identifier for the name of the {@link SecondaryAdapter} to identify and group multiple implementing classes of
	 * the same {@link SecondaryAdapter}. If not set, external tooling may default this to the simple name of the
	 * annotated type or package.
	 *
	 * @since 1.8
	 */
	String name() default "";

	/**
	 * A description of the {@link SecondaryAdapter}. If not set, external tooling may default this to the JavaDoc.
	 *
	 * @since 1.8
	 */
	String description() default "";
}
