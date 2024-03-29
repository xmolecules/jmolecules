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

/**
 * Identifies a domain [Service]. A service is a significant process or transformation in the domain that is not a
 * natural responsibility of an entity or value object, add an operation to the model as a standalone interface declared
 * as a service. Define a service contract, a set of assertions about interactions with the service. (See assertions.)
 * State these assertions in the ubiquitous language of a specific bounded context. Give the service a name, which also
 * becomes part of the ubiquitous language.
 *
 * Kotlin's counterpart of the Java's [Service](https://github.com/xmolecules/jmolecules/blob/main/jmolecules-ddd/src/main/java/org/jmolecules/ddd/annotation/Service.java)
 *
 * @author Jocelyn Ntakpe
 */
@Retention
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
annotation class Service
