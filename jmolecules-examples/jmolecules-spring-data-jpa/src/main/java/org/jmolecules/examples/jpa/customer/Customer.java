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
package org.jmolecules.examples.jpa.customer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.UUID;

import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Association;
import org.jmolecules.ddd.types.Identifier;
import org.jmolecules.examples.jpa.customer.Customer.CustomerId;

/**
 * @author Oliver Drotbohm
 */
@Getter
public class Customer implements AggregateRoot<Customer, CustomerId> {

	private final CustomerId id;
	private String firstname, lastname;
	private List<Address> addresses;

	public Customer(String firstname, String lastname) {

		this.id = CustomerId.of(UUID.randomUUID());
		this.firstname = firstname;
		this.lastname = lastname;
	}

	@Value
	@RequiredArgsConstructor(staticName = "of")
	public static class CustomerId implements Identifier {
		private final UUID id;
	}

	@Getter
	public static class CustomerAssociation implements Association<Customer, CustomerId> {

		private CustomerId id;

		public CustomerAssociation(Customer customer) {
			this.id = customer.getId();
		}
	}
}
