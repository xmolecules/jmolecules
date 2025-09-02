/*
 * Copyright 2021-2024 the original author or authors.
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
package org.jmolecules.ddd.types;

import org.jmolecules.stereotype.Stereotype;

/**
 * Identifies a value object. Domain concepts that are modeled as value objects have no conceptual identity or
 * lifecycle. Implementations should be immutable, operations on it are side-effect free.
 *
 * @author Oliver Drotbohm
 * @author Stephan Pirnbaum
 * @author Martin Schimak
 * @author Henning Schwentner
 * @author Christian Stettler
 * @see <a href="https://domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf">Domain-Driven Design
 *      Reference (Evans) - Value objects</a>
 * @since 1.3
 */
@Stereotype(priority = 30)
public interface ValueObject {}
