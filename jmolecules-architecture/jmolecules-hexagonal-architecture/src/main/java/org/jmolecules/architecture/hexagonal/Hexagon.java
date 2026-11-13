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
package org.jmolecules.architecture.hexagonal;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jmolecules.stereotype.ArchitecturalUnit;

/**
 * Declares the annotated package and its sub-packages to be a hexagon, i.e. an architectural unit structured according
 * to the rules of the Hexagonal Architecture. Multiple hexagons can exist in a code base, which allows tooling to
 * verify rules between them in addition to the ones applied within a single one. For example, secondary adapters of one
 * hexagon might be allowed to access primary adapters of another one.
 *
 * @author Oliver Drotbohm
 * @since 2.1
 * @see ArchitecturalUnit
 * @see Application
 * @see Port
 * @see Adapter
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PACKAGE, ElementType.ANNOTATION_TYPE })
@Documented
@ArchitecturalUnit
public @interface Hexagon {

	/**
	 * A stable identifier for the hexagon. If not defined, an identifier will be derived from the annotated element,
	 * usually the fully qualified name of the package.
	 */
	String id() default "";

	/**
	 * A human readable name for the hexagon. Tooling should prevent both {@link #value()} and {@link #name()} from being
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
	 * A human readable description for the hexagon.
	 */
	String description() default "";

	/**
	 * The roles of the types the hexagon exposes to other architectural units, i.e. the ones that types of other units
	 * may refer to. If not defined, tooling applies its globally configured default.
	 *
	 * @see Exposure
	 */
	Exposure[] exposes() default {};

	/**
	 * The roles of the hexagon's types that may refer to other architectural units. If not defined, tooling applies its
	 * globally configured default.
	 *
	 * @see Referrer
	 */
	Referrer[] refersVia() default {};

	/**
	 * The roles of types a hexagon can expose to other architectural units.
	 *
	 * @author Oliver Drotbohm
	 * @since 2.1
	 */
	enum Exposure {

		/**
		 * Exposes the hexagon's {@link PrimaryAdapter}s to the {@link SecondaryAdapter}s of other units.
		 */
		PRIMARY_ADAPTERS,

		/**
		 * Exposes the hexagon's {@link PrimaryPort}s.
		 */
		PRIMARY_PORTS,

		/**
		 * Exposes the hexagon's {@link Application} code.
		 */
		APPLICATION;
	}

	/**
	 * The roles of a hexagon's types that can be allowed to refer to other architectural units.
	 *
	 * @author Oliver Drotbohm
	 * @since 2.1
	 */
	enum Referrer {

		/**
		 * Allows the hexagon's {@link SecondaryAdapter}s to refer to other units.
		 */
		SECONDARY_ADAPTERS,

		/**
		 * Allows the hexagon's {@link Application} code to refer to other units, including types without any hexagonal
		 * stereotype.
		 */
		APPLICATION;
	}
}
