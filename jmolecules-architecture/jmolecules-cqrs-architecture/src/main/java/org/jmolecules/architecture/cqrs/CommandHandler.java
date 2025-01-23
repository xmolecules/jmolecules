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
package org.jmolecules.architecture.cqrs;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jmolecules.stereotype.Stereotype;

/**
 * Identifies a command handler in the context of CQRS, i.e. logic to process a {@link Command}. The command handler may
 * or may not reject the command. In case of processing, the handler takes care of orchestrating the business logic
 * related to the command.
 *
 * @author Christian Stettler
 * @author Henning Schwentner
 * @author Stephan Pirnbaum
 * @author Martin Schimak
 * @author Oliver Drotbohm
 * @since 1.1
 * @see <a href="http://cqrs.files.wordpress.com/2010/11/cqrs_documents.pdf">CQRS Documents by Greg Young - Commands</a>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR })
@Stereotype
public @interface CommandHandler {

	/**
	 * Optional identification of the namespace of the command handled by this handler. This information may be used for
	 * easier linkage between command and handler by external tools and refers to {@link Command#namespace()}. When
	 * leaving the default value, it is assumed that the method signature makes clear what command is consumed. If the
	 * handler takes care of all commands of a specific namespace, the value of this field needs to be set to the
	 * respective namespace and the {@link CommandHandler#name()} needs to be set accordingly. If the handler doesn't care
	 * about the namespace, the value may be set to the '*' (asterisk) placeholder.
	 */
	String namespace() default "";

	/**
	 * Optional identification of the name of the command handled by this handler. This information may be used for easier
	 * linkage between command and handler by external tools and refers to {@link Command#name()}. When leaving the
	 * default value, it is assumed that the method signature makes clear what command is consumed. If the handler takes
	 * care of all commands of a specific namespace, the value of this field needs to be set to the '*' (asterisk)
	 * placeholder.
	 */
	String name() default "";

}
