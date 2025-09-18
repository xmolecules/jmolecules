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
 * Identifies a [Factory]. Factories encapsulate the responsibility of creating complex objects in general and
 * Aggregates in particular. Objects returned by the factory methods are guaranteed to be in valid state.
 *
 * Kotlin's counterpart of the Java's [Factory](https://github.com/xmolecules/jmolecules/blob/main/jmolecules-ddd/src/main/java/org/jmolecules/ddd/annotation/Factory.java)
 *
 * @author Jocelyn Ntakpe
 *
 * See also : [AggregateRoot]
 * See also : [Domain-Driven Design Reference (Evans) - Factories](https://domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf)
 */
@Retention
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Stereotype
annotation class Factory
