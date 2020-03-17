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
package org.jddd.examples.jpa.order;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.UUID;

import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jddd.core.types.AggregateRoot;
import org.jddd.core.types.Identifier;
import org.jddd.examples.jpa.customer.Customer;
import org.jddd.examples.jpa.customer.Customer.CustomerAssociation;

/**
 * @author Oliver Drotbohm
 */
@Table(name = "SAMPLE_ORDER")
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order implements AggregateRoot<Order, Order.OrderId> {

	private final @EqualsAndHashCode.Include OrderId id;
	private @OneToMany List<LineItem> lineItems;
	private CustomerAssociation customer;

	public Order(Customer customer) {

		this.id = OrderId.of(UUID.randomUUID());
		this.customer = new CustomerAssociation(customer);
	}

	@Value
	@RequiredArgsConstructor(staticName = "of")
	@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
	public static class OrderId implements Identifier {

		private final UUID orderId;

		public static OrderId create() {
			return OrderId.of(UUID.randomUUID());
		}
	}
}
