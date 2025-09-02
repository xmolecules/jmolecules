/*
 * Copyright 2022-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jmolecules.ddd.annotation

import org.jmolecules.stereotype.Stereotype

/**
 * Declares a field (or a getter) of a class to constitute the identity of the corresponding class. Primarily used in
 *  [AggregateRoot] [Entity] types.
 *
 * Kotlin's counterpart of the Java's [Entity](https://github.com/xmolecules/jmolecules/blob/main/jmolecules-ddd/src/main/java/org/jmolecules/ddd/annotation/Identity.java)
 *
 * @author Oliver Drotbohm
 * @author Stephan Pirnbaum
 * @author Henning Schwentner
 *
 * See also : [Domain-Driven Design Reference (Evans) - Entities](https://domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf)
 */
@Retention
@MustBeDocumented
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.PROPERTY_GETTER
)
@Stereotype
annotation class Identity
