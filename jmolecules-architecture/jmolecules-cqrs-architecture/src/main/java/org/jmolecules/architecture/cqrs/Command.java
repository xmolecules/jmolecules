/*
 * Copyright 2020-2021 the original author or authors.
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

/**
 * Identifies a command in the context of CQRS, i.e. a request to the system for the change of data. Commands are always
 * in imperative tense and thus, unlike an event, do not state that something has already happened, but something is
 * requested to happen. With that, the {@link CommandHandler} which is processing the command has the option reject the
 * command.
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
@Target(ElementType.TYPE)
public @interface Command {

    /**
     * An identifier for the namespace of the command to group multiple commands and let clients express their interest
     * in all commands of a specific namespace. If not set, external tooling may default this to the fully-qualified
     * package name of the annotated type.
     */
    String namespace() default "";

    /**
     * An identifier for the name of the command used to abstract away from the type system and to guard against
     * refactorings. If not set, external tooling may default this to the simple class name of the annotated type.
     */
    String name() default "";
}
