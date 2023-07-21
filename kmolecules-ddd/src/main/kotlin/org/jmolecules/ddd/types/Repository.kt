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
package org.jmolecules.ddd.types

/**
 * Identifies a [Repository]. Repositories simulate a collection of aggregates to which aggregate instances can be
 * added and removed. They usually also expose API to select a subset of aggregates matching certain criteria. Access to
 * projections of an aggregate might be provided as well but also via a dedicated separate abstraction.
 *
 * Implementations use a dedicated persistence mechanism appropriate to the data structure and query requirements at
 * hand. However, they should make sure that no persistence mechanism specific APIs leak into client code.
 *
 * Kotlin's counterpart of Java's [Repository](https://github.com/xmolecules/jmolecules/blob/main/jmolecules-ddd/src/main/java/org/jmolecules/ddd/types/Repository.java)
 *
 * @author Jocelyn Ntakpe
 * @since 1.0
 * @see [AggregateRoot]
 *
 * See also: [Domain-Driven Design Reference (Evans) - Repositories](https://domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf)
 */
interface Repository<T : AggregateRoot<T, ID>, ID : Identifier>
