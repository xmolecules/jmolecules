package org.jmolecules.spring;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.build.Plugin.NoOp;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType.Builder;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.jmolecules.ddd.annotation.Repository;
import org.jmolecules.ddd.annotation.Service;
import org.springframework.stereotype.Component;

@Slf4j
public class JMoleculesSpringPlugin extends NoOp {

	private static Set<Class<?>> ANNOTATIONS = new HashSet<>(
			Arrays.asList(Service.class, Repository.class, Factory.class));

	@Override
	public boolean matches(TypeDescription target) {
		return ANNOTATIONS.stream().anyMatch(it -> isAnnotatedWith(target, it));
	}

	/*
	 * (non-Javadoc)
	 * @see net.bytebuddy.build.Plugin#apply(net.bytebuddy.dynamic.DynamicType.Builder, net.bytebuddy.description.type.TypeDescription, net.bytebuddy.dynamic.ClassFileLocator)
	 */
	@Override
	public Builder<?> apply(Builder<?> builder, TypeDescription type, ClassFileLocator classFileLocator) {

		if (isAnnotatedWith(type, Service.class)) {
			builder = addAnnotationIfMissing(org.springframework.stereotype.Service.class, builder, type);
		}

		if (isAnnotatedWith(type, Repository.class)) {
			builder = addAnnotationIfMissing(org.springframework.stereotype.Repository.class, builder, type);
		}

		if (isAnnotatedWith(type, org.jmolecules.ddd.annotation.Factory.class)) {
			builder = addAnnotationIfMissing(Component.class, builder, type);
		}

		return builder;
	}

	private static Builder<?> addAnnotationIfMissing(Class<? extends Annotation> annotation, Builder<?> builder,
			TypeDescription type) {

		if (isAnnotatedWith(type, annotation)) {
			return builder;
		}

		log.info("jMolecules Spring Plugin - Annotating {} with {}.", type.getName(), annotation.getName());

		return builder.annotateType(getAnnotation(annotation));
	}

	private static AnnotationDescription getAnnotation(Class<? extends Annotation> type) {
		return AnnotationDescription.Builder.ofType(type).build();
	}

	private static boolean isAnnotatedWith(TypeDescription type, Class<?> annotationType) {

		return type.getDeclaredAnnotations() //
				.asTypeList() //
				.stream() //
				.anyMatch(it -> it.isAssignableTo(annotationType)); // || isAnnotatedWith(it, annotationType));
	}
}
