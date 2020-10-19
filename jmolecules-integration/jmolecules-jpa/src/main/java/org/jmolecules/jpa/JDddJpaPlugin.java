package org.jmolecules.jpa;

import static net.bytebuddy.matcher.ElementMatchers.*;

import lombok.extern.slf4j.Slf4j;
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

import org.jmolecules.ddd.types.Association;
import org.jmolecules.ddd.types.Entity;
import org.jmolecules.ddd.types.Identifier;

@Slf4j
public class JDddJpaPlugin extends NoOp {

	@Override
	public boolean matches(TypeDescription target) {
		return !target.getInterfaces().filter(nameStartsWith("org.jmolecules")).isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see net.bytebuddy.build.Plugin#apply(net.bytebuddy.dynamic.DynamicType.Builder, net.bytebuddy.description.type.TypeDescription, net.bytebuddy.dynamic.ClassFileLocator)
	 */
	@Override
	public Builder<?> apply(Builder<?> builder, TypeDescription type, ClassFileLocator classFileLocator) {

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

	private static Builder<?> handleIdentifier(Builder<?> builder, TypeDescription type) {

		if (!type.isAssignableTo(Serializable.class)) {

			log.info("jDDD JPA Plugin - Adding Serializable to {}.", type.getName());

			builder = builder.implement(Serializable.class);
		}

		return addAnnotationIfMissing(Embeddable.class, builder, type);
	}

	private static Builder<?> handleAssociation(Builder<?> builder, TypeDescription type) {
		return addAnnotationIfMissing(Embeddable.class, builder, type);
	}

	private static Builder<?> handleEntity(Builder<?> builder, TypeDescription type) {

		// Annotate identifier types
		builder = builder
				.field(fieldType(isSubTypeOf(Identifier.class))
						.and(not(isAnnotatedWith(EmbeddedId.class).or(isAnnotatedWith(Id.class)))))
				.annotateField(getAnnotation(EmbeddedId.class));

		return addAnnotationIfMissing(javax.persistence.Entity.class, builder, type);
	}

	private static Builder<?> addAnnotationIfMissing(Class<? extends Annotation> annotation, Builder<?> builder,
			TypeDescription type) {

		if (type.getDeclaredAnnotations().isAnnotationPresent(annotation)) {
			return builder;
		}

		log.info("jDDD JPA Plugin - Annotating {} with {}.", type.getName(), annotation.getName());

		return builder.annotateType(getAnnotation(annotation));
	}

	private static AnnotationDescription getAnnotation(Class<? extends Annotation> type) {
		return AnnotationDescription.Builder.ofType(type).build();
	}
}
