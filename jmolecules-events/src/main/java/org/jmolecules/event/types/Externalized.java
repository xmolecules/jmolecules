/*
 * Copyright 2023-2024 the original author or authors.
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
package org.jmolecules.event.types;

import org.jmolecules.stereotype.Stereotype;

/**
 * Interface to marks domain events as to be externalized, which means that they are intended to be published to
 * infrastructure outside the application.
 *
 * @author Oliver Drotbohm
 * @since 1.8
 */
@Stereotype
public interface Externalized {}
