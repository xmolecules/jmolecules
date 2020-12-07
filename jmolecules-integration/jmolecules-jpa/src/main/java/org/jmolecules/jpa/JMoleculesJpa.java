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
package org.jmolecules.jpa;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.persistence.GeneratedValue;

@Slf4j
public class JMoleculesJpa {

	private static Map<Class<?>, Collection<Field>> fields = new ConcurrentHashMap<>();

	public static void verifyNullability(@This Object object) throws Exception {

		log.info("Verifying nullability!");

		fields.computeIfAbsent(object.getClass(), JMoleculesJpa::fieldsToNullCheckFor)
				.forEach(it -> {

					Object value;

					try {
						value = it.get(object);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}

					if (value == null) {
						throw new IllegalStateException(
								String.format("%s.%s must not be null!", it.getDeclaringClass().getSimpleName(), it.getName()));
					}
				});
	}

	private static Collection<Field> fieldsToNullCheckFor(Class<?> type) {

		return Arrays.stream(type.getDeclaredFields())
				.filter(field -> !isGeneratedValue(field))
				.filter(field -> !isNullable(field))
				.peek(field -> field.setAccessible(true))
				.collect(Collectors.toSet());
	}

	private static boolean isGeneratedValue(Field field) {
		return field.getAnnotation(GeneratedValue.class) != null;
	}

	private static boolean hasAnnotation(Field field, String simpleName) {
		return Arrays.stream(field.getAnnotations())
				.anyMatch(it -> it.annotationType().getSimpleName().equals(simpleName));
	}

	private static boolean isNullable(Field field) {

		if (hasAnnotation(field, "Nullable")) {
			return true;
		}

		return !Arrays.stream(field.getDeclaringClass().getPackage().getAnnotations())
				.anyMatch(it -> it.annotationType().getSimpleName().equals("NonNullApi"));
	}
}
