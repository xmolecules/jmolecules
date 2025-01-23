/*
 * Copyright 2020-2024 the original author or authors.
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
package org.jmolecules.architecture.onion.classical;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jmolecules.stereotype.Stereotype;

/**
 * Identifies the {@link InfrastructureRing} in an onion architecture. The infrastructure ring is the technical
 * implementation of the interfaces defined in the inner layers, such as JPA entities or concrete repository
 * implementations. It takes care of handling external requests and communication with other systems.
 *
 * @author Christian Stettler
 * @author Henning Schwentner
 * @author Stephan Pirnbaum
 * @author Martin Schimak
 * @author Oliver Drotbohm
 * @see <a href="https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/">The Onion Architecture : part 1
 *      (Palermo)</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PACKAGE, ElementType.TYPE })
@Documented
@Stereotype
public @interface InfrastructureRing {}
