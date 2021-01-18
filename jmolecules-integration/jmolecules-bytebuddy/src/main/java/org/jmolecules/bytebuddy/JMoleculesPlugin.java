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

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.build.Plugin;
import net.bytebuddy.build.Plugin.WithPreprocessor;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType.Builder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Oliver Drotbohm
 */
@Slf4j
public class JMoleculesPlugin implements WithPreprocessor {

	private Map<TypeDescription, List<Plugin>> delegates = new HashMap<>();

	/*
	 * (non-Javadoc)
	 * @see net.bytebuddy.build.Plugin.WithPreprocessor#onPreprocess(net.bytebuddy.description.type.TypeDescription, net.bytebuddy.dynamic.ClassFileLocator)
	 */
	@Override
	public void onPreprocess(TypeDescription typeDescription, ClassFileLocator classFileLocator) {

		delegates.computeIfAbsent(typeDescription, it -> {

			ClassWorld world = ClassWorld.of(classFileLocator);

			return Stream.concat(jpaPlugin(world), springPlugin(world))
					.filter(plugin -> plugin.matches(typeDescription))
					.collect(Collectors.toList());
		});
	}

	/*
	 * (non-Javadoc)
	 * @see net.bytebuddy.matcher.ElementMatcher#matches(java.lang.Object)
	 */
	@Override
	public boolean matches(TypeDescription target) {

		List<Plugin> plugins = delegates.get(target);

		return plugins != null && !plugins.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see net.bytebuddy.build.Plugin#apply(net.bytebuddy.dynamic.DynamicType.Builder, net.bytebuddy.description.type.TypeDescription, net.bytebuddy.dynamic.ClassFileLocator)
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Builder<?> apply(Builder<?> builder, TypeDescription typeDescription, ClassFileLocator classFileLocator) {

		return delegates.get(typeDescription).stream()
				.reduce(builder, (it, plugin) -> (Builder) plugin.apply(it, typeDescription, classFileLocator),
						(left, right) -> right);
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {}

	private static Stream<Plugin> jpaPlugin(ClassWorld world) {

		if (!world.isAvailable("javax.persistence.Entity")) {
			return Stream.empty();
		}

		boolean jMoleculesJpaAvailable = world.isAvailable("org.jmolecules.jpa.JMoleculesJpa");

		if (!jMoleculesJpaAvailable) {
			log.warn("JMoleculesJpa missing on the classpath! Add org.jmolecules:jmolecules-jpa as dependency!");
		}

		return Stream.of(new JMoleculesJpaPlugin());
	}

	private static Stream<Plugin> springPlugin(ClassWorld world) {

		return world.isAvailable("org.springframework.stereotype.Component")
				? Stream.of(new JMoleculesSpringPlugin())
				: Stream.empty();
	}
}
