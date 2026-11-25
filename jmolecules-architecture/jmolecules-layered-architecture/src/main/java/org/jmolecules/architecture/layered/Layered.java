/*
 * Copyright 2026 the original author or authors.
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
package org.jmolecules.architecture.layered;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jmolecules.stereotype.ArchitecturalUnit;

/**
 * Declares the annotated package and its sub-packages to be an architectural unit structured according to the rules
 * of a Layered Architecture. Multiple such units can exist in a code base, which allows tooling to verify rules between
 * them in addition to the ones applied within a single one.
 *
 * @author Oliver Drotbohm
 * @since 2.1
 * @see ArchitecturalUnit
 * @see InterfaceLayer
 * @see ApplicationLayer
 * @see DomainLayer
 * @see InfrastructureLayer
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PACKAGE, ElementType.ANNOTATION_TYPE })
@Documented
@ArchitecturalUnit
public @interface Layered {

	/**
	 * A stable identifier for the unit. If not defined, an identifier will be derived from the annotated element,
	 * usually the fully qualified name of the package.
	 */
	String id() default "";

	/**
	 * A human readable name for the unit. Tooling should prevent both {@link #value()} and {@link #name()} from being
	 * configured at the same time. If in doubt, the value defined in {@link #name()} will be preferred.
	 *
	 * @see #id()
	 */
	String name() default "";

	/**
	 * An alias for {@link #name()}.
	 *
	 * @see #name()
	 */
	String value() default "";

	/**
	 * A human readable description for the unit.
	 */
	String description() default "";

	/**
	 * The layers of the unit exposed to other architectural units, i.e. the ones that types of other units may refer to.
	 * If not defined, tooling applies its globally configured default.
	 *
	 * @see Exposure
	 */
	Exposure[] exposes() default {};

	/**
	 * The layers of the unit exposed to other architectural units <em>in addition</em> to the ones exposed globally, or
	 * the ones declared via {@link #exposes()}.
	 *
	 * @see #exposes()
	 * @see Exposure
	 */
	Exposure[] additionallyExposes() default {};

	/**
	 * The layers of the unit whose types may refer to other architectural units. If not defined, tooling applies its
	 * globally configured default.
	 *
	 * @see Referrer
	 */
	Referrer[] refersVia() default {};

	/**
	 * The layers of the unit whose types may refer to other architectural units <em>in addition</em> to the ones allowed
	 * globally, or the ones declared via {@link #refersVia()}.
	 *
	 * @see #refersVia()
	 * @see Referrer
	 */
	Referrer[] additionallyRefersVia() default {};

	/**
	 * The layers a unit can expose to other architectural units. Usually, a unit exposes its lower layers to the upper
	 * layers of other units.
	 *
	 * @author Oliver Drotbohm
	 * @since 2.1
	 */
	enum Exposure {

		/**
		 * Exposes the unit's interface layer types.
		 */
		INTERFACE,

		/**
		 * Exposes the unit's application layer types.
		 */
		APPLICATION,

		/**
		 * Exposes the unit's domain layer types.
		 */
		DOMAIN,

		/**
		 * Exposes the unit's infrastructure layer types.
		 */
		INFRASTRUCTURE;
	}

	/**
	 * The layers of a unit whose types can be allowed to refer to other architectural units. Usually, a unit's upper
	 * layers refer to the lower layers of other units.
	 *
	 * @author Oliver Drotbohm
	 * @since 2.1
	 */
	enum Referrer {

		/**
		 * Allows the unit's interface layer types to refer to other units.
		 */
		INTERFACE,

		/**
		 * Allows the unit's application layer types to refer to other units, including types not assigned to any layer.
		 */
		APPLICATION,

		/**
		 * Allows the unit's domain layer types to refer to other units.
		 */
		DOMAIN,

		/**
		 * Allows the unit's infrastructure layer types to refer to other units.
		 */
		INFRASTRUCTURE;
	}
}
