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
package org.jmolecules.stereotype;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Meta-annotation to declare an annotation as defining an <em>architectural unit</em>, i.e. a unit of code (usually a
 * package and its sub-packages) that a particular architectural language is applied to. Conceptually, this is similar
 * to a module in that it creates a boundary which a type can belong to or not, but on a meta-level: a code base can
 * consist of multiple units, each of which applies its own architectural rules internally.
 * <p>
 * Tooling identifies the unit a type belongs to by finding the <em>nearest</em> package (the type's own package or any
 * of its parent packages) that is annotated with an annotation meta-annotated with {@link ArchitecturalUnit}. Units can
 * thus be nested, and a type always belongs to the innermost unit only.
 * <p>
 * Tooling is expected to inspect the following, optional attributes of the concrete annotation:
 * <ul>
 * <li>{@code id} – a stable identifier of the unit. Defaults to the fully qualified name of the annotated package if
 * not declared or empty.</li>
 * <li>{@code name} or {@code value} – a human-readable name of the unit, with {@code name} taking precedence. Defaults
 * to the simple name of the annotated package if not declared or empty.</li>
 * <li>{@code description} – a human-readable description of the unit.</li>
 * </ul>
 *
 * @author Oliver Drotbohm
 * @since 2.1
 */
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
@Documented
public @interface ArchitecturalUnit {}
