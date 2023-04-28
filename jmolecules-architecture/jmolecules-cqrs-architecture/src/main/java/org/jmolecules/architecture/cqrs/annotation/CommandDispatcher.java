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
package org.jmolecules.architecture.cqrs.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies a command dispatcher in the context of CQRS, i.e. logic to dispatch a {@link Command}.
 *
 * @author Christian Stettler
 * @author Henning Schwentner
 * @author Stephan Pirnbaum
 * @author Martin Schimak
 * @author Oliver Drotbohm
 * @since 1.1
 * @see <a href="http://cqrs.files.wordpress.com/2010/11/cqrs_documents.pdf">CQRS Documents by Greg Young - Commands</a>
 * @deprecated since 1.7, for removal in 2.0. Use {@link org.jmolecules.architecture.cqrs.CommandDispatcher} instead.
 */
@org.jmolecules.architecture.cqrs.CommandDispatcher
@Deprecated
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
public @interface CommandDispatcher {

    /**
     * Optional identification of the command dispatched by this dispatcher. This information may be used for easier
     * linkage between command and dispatcher by external tools and refers to the combination of
     * {@link Command#namespace()} and {@link Command#name()}, separated by '.' (dot).
     */
    String dispatches() default "";
}
