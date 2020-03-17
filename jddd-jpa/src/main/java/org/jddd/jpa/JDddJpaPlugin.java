package org.jddd.jpa;

import static net.bytebuddy.matcher.ElementMatchers.*;

import net.bytebuddy.build.Plugin.NoOp;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType.Builder;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;

import org.jddd.core.types.Association;
import org.jddd.core.types.Entity;
import org.jddd.core.types.Identifier;

public class JDddJpaPlugin extends NoOp {

	@Override
	public boolean matches(TypeDescription target) {
		return !target.getInterfaces().filter(nameStartsWith("org.jddd")).isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see net.bytebuddy.build.Plugin#apply(net.bytebuddy.dynamic.DynamicType.Builder, net.bytebuddy.description.type.TypeDescription, net.bytebuddy.dynamic.ClassFileLocator)
	 */
	@Override
	public Builder<?> apply(Builder<?> builder, TypeDescription type, ClassFileLocator classFileLocator) {

		System.out.println("Processing " + type.getName());

		if (type.isAssignableTo(Entity.class)) {
			builder = handleEntity(builder, type);
		}

		if (type.isAssignableTo(Association.class)) {
			builder = handleAssociation(builder, type);
		}

		if (type.isAssignableTo(Identifier.class)) {
			builder = handleIdentifier(builder, type);
		}

		return builder;
	}

	/**
	 * @param builder
	 * @param type
	 * @return
	 */
	private Builder<?> handleIdentifier(Builder<?> builder, TypeDescription type) {

		System.out.println("Processing identifier " + type.getName());

		if (!type.isAssignableTo(Serializable.class)) {

			System.out.println("Adding Serializable to " + type.getName());

			builder = builder.implement(Serializable.class);
		}

		return addAnnotationIfMissing(Embeddable.class, builder, type);
	}

	/**
	 * @param builder
	 * @param type
	 * @return
	 */
	private Builder<?> handleAssociation(Builder<?> builder, TypeDescription type) {
		return addAnnotationIfMissing(Embeddable.class, builder, type);
	}

	private Builder<?> handleEntity(Builder<?> builder, TypeDescription type) {

		// MethodDescription superClassConstructor = findDefaultConstructor(type.getSuperClass());
		//
		// // Create no-arg constructor
		// if (type.getDeclaredMethods().filter(ElementMatchers.isDefaultConstructor()).isEmpty()) {
		//
		// System.out.println("Adding default constructor to " + type.getName());
		//
		// builder = builder.defineConstructor(Visibility.PRIVATE) //
		// .intercept();
		// // .intercept(MethodCall.invoke(superClassConstructor).onSuper());
		// }

		// Annotate identifier types
		builder = builder
				.field(fieldType(isSubTypeOf(Identifier.class))
						.and(not(isAnnotatedWith(EmbeddedId.class).or(isAnnotatedWith(Id.class)))))
				.annotateField(getAnnotation(EmbeddedId.class));

		return addAnnotationIfMissing(javax.persistence.Entity.class, builder, type);
	}

	// private static MethodDescription findDefaultConstructor(Generic type) {
	//
	// MethodList<InGenericShape> defaultConstructor = type.getDeclaredMethods()
	// .filter(ElementMatchers.isDefaultConstructor());
	//
	// return defaultConstructor.isEmpty() ? null : defaultConstructor.getOnly();
	// }

	private static Builder<?> addAnnotationIfMissing(Class<? extends Annotation> annotation, Builder<?> builder,
			TypeDescription type) {

		return type.getDeclaredAnnotations().isAnnotationPresent(annotation) //
				? builder //
				: builder.annotateType(getAnnotation(annotation));
	}

	private static AnnotationDescription getAnnotation(Class<? extends Annotation> type) {
		return AnnotationDescription.Builder.ofType(type).build();
	}
}
