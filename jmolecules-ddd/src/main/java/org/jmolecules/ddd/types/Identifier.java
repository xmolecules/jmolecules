/*
 * Copyright 2020-2024 the original author or authors.
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
package org.jmolecules.ddd.types;

import org.jmolecules.stereotype.Stereotype;

/**
 * Marker interface for identifiers. Exists primarily to easily identify types that are supposed to be identifiers
 * within the code base and let the compiler verify the correctness of declared relationships.
 *
 * @author Oliver Drotbohm
 * @see Identifiable
 */
@Stereotype(priority = 0)
public interface Identifier {

}
