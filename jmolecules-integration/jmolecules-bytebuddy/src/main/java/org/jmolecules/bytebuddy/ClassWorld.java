/*
 * Copyright 2021 the original author or authors.
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
package org.jmolecules.bytebuddy;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.dynamic.ClassFileLocator;

import java.io.IOException;

@RequiredArgsConstructor(staticName = "of")
class ClassWorld {

	private final ClassFileLocator locator;

	boolean isAvailable(String name) {

		try {
			return locator.locate(name).isResolved();
		} catch (IOException o_O) {
			return false;
		}
	}
}
