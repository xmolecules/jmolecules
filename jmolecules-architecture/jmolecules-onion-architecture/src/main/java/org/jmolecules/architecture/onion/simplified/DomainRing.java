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
package org.jmolecules.architecture.onion.simplified;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jmolecules.stereotype.Stereotype;

/**
 * Identifies the {@link DomainRing} in an onion architecture. The domain ring is the inner-most ring in the onion
 * architecture and is only coupled to itself. It models the truth of the business domain by consisting of behaviour
 * (logic) and the required state (data). Compared to the 4-ring onion architecture in which the domain is split into
 * domain model and domain services, the 3-ring version combines those 2 rings so that it implements behavior (logic),
 * state (data), and interfaces needed for e.g. storing and retrieving data, i.e. repository interfaces.
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
public @interface DomainRing {}
