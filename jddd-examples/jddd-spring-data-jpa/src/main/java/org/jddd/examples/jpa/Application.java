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
package org.jddd.examples.jpa;

import org.jddd.examples.jpa.customer.Customer;
import org.jddd.examples.jpa.customer.Customers;
import org.jddd.examples.jpa.order.Order;
import org.jddd.examples.jpa.order.Orders;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author Oliver Drotbohm
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(Application.class, args);

		Customers customers = context.getBean(Customers.class);
		Customer customer = customers.save(new Customer("Dave", "Matthews"));

		Orders orders = context.getBean(Orders.class);
		Order order = orders.save(new Order(customer));

		Customer resolved = customers.resolveRequired(order.getCustomer());
	}
}
