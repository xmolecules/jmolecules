/*
 * Copyright 2020 the original author or authors.
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
package org.jmolecules.architecture.cqrs.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a command handler in the context of CQRS, i.e. logic to process a {@link Command}.
 * The command handler may or may not reject the command. In case of processing, the handler
 * takes care of orchestrating the business logic related to the command.
 *
 * @author Christian Stettler
 * @author Henning Schwentner
 * @author Stephan Pirnbaum
 * @author Martin Schimak
 * @author Oliver Drotbohm
 * @see <a href="http://cqrs.files.wordpress.com/2010/11/cqrs_documents.pdf">CQRS Documents by Greg Young - Commands</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface CommandHandler {

    /**
     * Optional identification of the command handled by this handler.
     * This information may be used for easier linkage between command and handler
     * by external tools and refers to the type of the command. When leaving the default value,
     * it is assumed that the method signature makes clear what event is consumer.
     */
    String handles() default "";

}