# DDD Best Practices

This document provides guidelines and best practices for implementing Domain-Driven Design using jMolecules.

## Core Principles

1. **Ubiquitous Language** - Use business terminology consistently throughout the codebase
2. **Bounded Contexts** - Separate the system into bounded contexts with explicit boundaries
3. **Value Objects** - Prefer value objects over primitive types for domain concepts
4. **Aggregate Roots** - Use aggregates with strict consistency boundaries
5. **Repositories** - Encapsulate data access behind repository interfaces
6. **Domain Services** - Keep business logic in domain services or entities
7. **Domain Events** - Use events to communicate between bounded contexts

## Implementation Guidelines

### 1. Value Objects

```java
// Good - Value Object
@ValueObject
public class EmailAddress {

    private final String value;

    private EmailAddress(String value) {
        this.value = value;
    }

    public static EmailAddress of(String value) {
        validateEmail(value);
        return new EmailAddress(value);
    }

    public String getValue() {
        return value;
    }
}

// Bad - Using primitive type
private String email;
```

### 2. Aggregate Roots

```java
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.ValueObject;

@AggregateRoot
@Entity
public class Order {

    private OrderId id;

    private OrderNumber orderNumber;

    private OrderTotal total;

    private List<OrderLineItem> lineItems = new ArrayList<>();

    // Aggregate root should manage all state changes
    public void addLineItem(Product product, int quantity) {
        // Business logic here
        OrderLineItem item = new OrderLineItem(this, product, quantity);
        lineItems.add(item);
        total = calculateTotal();
    }
}
```

### 3. Repositories

```java
@Repository
public interface OrderRepository extends CrudRepository<Order, OrderId> {

    List<Order> findByCustomerId(CustomerId customerId);

    List<Order> findOrdersWithMinimumTotal(BigDecimal minimumAmount);
}
```

### 4. Domain Events

```java
@DomainEvent
public class OrderPlaced {

    private final OrderId orderId;
    private final LocalDateTime placedAt;
    private final CustomerId customerId;
    private final OrderTotal total;

    public OrderPlaced(OrderId orderId, LocalDateTime placedAt, CustomerId customerId, OrderTotal total) {
        this.orderId = orderId;
        this.placedAt = placedAt;
        this.customerId = customerId;
        this.total = total;
    }

    // Getters
}
```

### 5. Architecture Layers

```java
import org.jmolecules.ddd.annotation.ApplicationLayer;
import org.jmolecules.ddd.annotation.DomainLayer;
import org.jmolecules.ddd.annotation.Service;

@ApplicationLayer
@Service
public class OrderApplicationService {

    private final OrderRepository orderRepository;
    private final OrderDomainService orderDomainService;
    private final CustomerRepository customerRepository;
    private final ProductDomainService productDomainService;
    private final PaymentGateway paymentGateway;

    public OrderApplicationService(OrderRepository orderRepository, OrderDomainService orderDomainService,
                                     CustomerRepository customerRepository, ProductService productDomainService,
                                     PaymentGateway paymentGateway) {
        this.orderRepository = orderRepository;
        this.orderDomainService = orderDomainService;
        this.customerRepository = customerRepository;
        this.productDomainService = productDomainService;
        this.paymentGateway = paymentGateway;
    }

    @Transactional
    public Order placeOrder(CustomerId customerId, List<OrderLineItemDTO> items) {
        // Application orchestration logic here

        // 1. Validate customer exists
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        // 2. Check customer can place orders
        if (!customer.isActive()) {
            throw new CustomerNotActiveException(customerId);
        }

        // 3. Validate products and quantities are available (orchestration layer)
        List<Product> availableProducts = new ArrayList<>();
        for (OrderLineItemDTO itemDTO : items) {
            Product product = productDomainService.getProduct(itemDTO.getProductId());
            if (product.getStockLevel() < itemDTO.getQuantity()) {
                throw new ProductOutOfStockException(itemDTO.getProductId(), itemDTO.getQuantity());
            }
            availableProducts.add(product);
        }

        // 4. Create order using domain service
        Order order = orderDomainService.createOrder(customerId, items);

        // 5. Save order
        orderRepository.save(order);

        // 6. Reduce product quantities (orchestration layer)
        for (int i = 0; i < items.size(); i++) {
            OrderLineItemDTO itemDTO = items.get(i);
            productDomainService.reduceStock(itemDTO.getProductId(), itemDTO.getQuantity());
        }

        // 7. Start payment process (asynchronous)
        paymentGateway.initiatePayment(order.getId(), order.getTotal());

        // 8. Publish order placed event
        DomainEventPublisher.publish(new OrderPlaced(order.getId(), LocalDateTime.now(), customerId, order.getTotal()));

        return order;
    }
}

@DomainLayer
@Service
public class OrderDomainService {

    public Order createOrder(CustomerId customerId, List<OrderLineItemDTO> items) {
        // Domain logic here
        Order order = new Order(customerId);

        for (OrderLineItemDTO itemDTO : items) {
            // In real implementation, we would use ProductReference or similar
            OrderLineItem item = new OrderLineItem(order, itemDTO.getProductId(), itemDTO.getQuantity());
            order.addLineItem(item);
        }

        return order;
    }
}

@DomainLayer
@Service
public class ProductDomainService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(ProductId productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    public void reduceStock(ProductId productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        product.reduceStock(quantity);
        productRepository.save(product);
    }
}
```