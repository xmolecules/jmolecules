/*
 * Copyright 2020 the original author or authors.
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

/**
 * Identifies a DDD module.
 *
 * @author Christian Stettler
 * @author Henning Schwentner
 * @author Stephan Pirnbaum
 * @author Martin Schimak
 * @author Oliver Drotbohm
 * @see <a href="https://domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf">Domain-Driven Design
 *      Reference (Evans) - Bounded Contexts</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PACKAGE, ElementType.ANNOTATION_TYPE })
@Documented
public @interface Module {

	/**
	 * A stable identifier for the module. If not defined, an identifier will be derived from the annotated element,
	 * usually a package. That allows tooling to derive name and description by applying some kind of convention to the
	 * identifier.
	 * <p>
	 * Assuming a package {@code com.acme.myapp.module} annotated with {@code Module}, tooling could use a resource bundle
	 * to lookup the keys {@code com.acme.myapp.module._name} and {@code com.acme.myapp.module_description} to resolve
	 * name and description respectively.
	 *
	 * @return
	 */
	String id() default "";

	/**
	 * A human readable name for the module. Might be overridden by an external resolution mechanism via {@link #id()}.
	 * Tooling should prevent both {@link #value()} and {@link #name()} from being configured at the same time. If in
	 * doubt, the value defined in {@link #name()} will be preferred.
	 *
	 * @return
	 * @see #id()
	 */
	String name() default "";

	/**
	 * An alias for {@link #name()}. Tooling should prevent both {@link #value()} and {@link #name()} from being
	 * configured at the same time. If in doubt, the value defined in {@link #name()} will be preferred.
	 *
	 * @return
	 * @see #name()
	 */
	String value() default "";

	/**
	 * A human readable description for the module. Might be overridden by an external resolution mechanism via
	 * {@link #id()}.
	 *
	 * @return
	 * @see #id()
	 */
	String description() default "";
}
