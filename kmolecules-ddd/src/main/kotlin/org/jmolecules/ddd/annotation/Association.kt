/*
 * Copyright 2020-2021 the original author or authors.
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

import kotlin.reflect.KClass

/**
 * An association to an [org.jmolecules.ddd.types.AggregateRoot].
 *
 * @author Simon Zambrovski
 * @see [](https://scabl.blogspot.com/2015/04/aeddd-9.html>John Sullivan - Advancing Enterprise DDD - Reinstating
the Aggregate</a>
) */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@MustBeDocumented
annotation class Association(
    /**
     * Defines the aggregate type.
     *
     * @return class defining the identifiable type of the aggregate.
     */
    val identifiableType: KClass<*> = Unit::class
)