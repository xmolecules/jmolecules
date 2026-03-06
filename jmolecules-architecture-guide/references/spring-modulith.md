# Spring Modulith Integration

This document provides guidance on integrating jMolecules with Spring Modulith for module decomposition and documentation.

## What is Spring Modulith?

Spring Modulith is a module decomposition and documentation tool for Spring Boot applications. It helps you:

1. Define modules with clear boundaries
2. Visualize module dependencies
3. Validate module structure
4. Generate module documentation

## Getting Started

Add the required dependencies:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.modulith</groupId>
        <artifactId>spring-modulith-core</artifactId>
        <version>1.1.0</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.modulith</groupId>
        <artifactId>spring-modulith-starter-test</artifactId>
        <version>1.1.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Configuration

Enable Spring Modulith in your application:

```java
@SpringBootApplication
@EnableSpringModulith
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

## Module Structure

Spring Modulith follows a conventional module structure:

```
src/main/java
└── com/example
    ├── inventory
    │   ├── InventoryController.java
    │   ├── InventoryService.java
    │   ├── InventoryRepository.java
    │   └── InventoryItem.java
    ├── order
    │   ├── OrderController.java
    │   ├── OrderService.java
    │   ├── OrderRepository.java
    │   └── Order.java
    └── payment
        ├── PaymentController.java
        ├── PaymentService.java
        ├── PaymentRepository.java
        └── Payment.java
```

## Integrating with jMolecules

### Module Identification

Spring Modulith automatically identifies modules based on package structure. You can customize this with:

```java
@Module
@BoundedContext("Inventory Management")
public interface InventoryModule {

    @ModuleInfo(name = "Inventory Management", description = "Manages inventory items")
    class Configuration {}
}
```

### Validating Architecture

Spring Modulith provides tests for validating your architecture:

```java
@SpringBootTest
@ExtendWith(SpringExtension.class)
class ApplicationTests {

    @Test
    void verifiesModuleStructure(ModuleTestAPI modules) {
        modules.verify()
            .haveExactlyOneModuleWithName("inventory")
            .haveExactlyOneModuleWithName("order")
            .haveExactlyOneModuleWithName("payment");
    }

    @Test
    void verifiesModuleDependencies(ModuleTestAPI modules) {
        modules.verify()
            .that(modules.byName("inventory"))
            .doesNotDependOnModulesThat()
            .haveNameMatching("order|payment");
    }
}
```

### Documenting Modules

Spring Modulith can generate module documentation:

```java
@ApplicationModuleTest
class ModuleDocumentationTests {

    @Test
    void documentModules(Documenter documenter) {
        documenter.writeDocumentation(Documenter.Options.defaults());
    }
}
```

Run this test to generate documentation in:

```
target/spring-modulith-docs/
├── modules.md
├── dependencies.png
├── module-structure.html
└── ...
```

## Advanced Configuration

### Customizing Module Detection

```java
@Configuration
public class ModulithConfiguration {

    @Bean
    ApplicationModuleDetectionStrategy moduleDetection() {
        return ApplicationModuleDetectionStrategy.explicit()
            .including("com.example.order", "com.example.payment")
            .excluding("com.example.shared");
    }
}
```

### Module Boundaries

```java
@Module
public class OrderModule {

    @PublicApi
    public void placeOrder(Order order) {
        // Implementation
    }

    void internalMethod() {
        // Implementation
    }
}
```

### Event Publication

```java
@AggregateRoot
@Entity
public class Order {

    @DomainEvent
    public static class OrderPlaced {
        // Event properties
    }

    public OrderPlaced placeOrder() {
        // Business logic
        return new OrderPlaced();
    }
}

@Module
public class OrderModule {

    @EventListener
    void handleOrderPlaced(OrderPlaced event) {
        // Event handling
    }
}
```

## Running Application Tests

Spring Modulith provides a test support module:

```java
@ApplicationModuleTest
class OrderModuleTests {

    @Autowired
    private OrderService orderService;

    @Test
    void shouldCreateOrder() {
        // Test logic
        Order order = orderService.createOrder();
        assertThat(order).isNotNull();
    }
}
```

## Generating Documentation

Run the module documentation test to generate:

1. Markdown documentation
2. PNG dependency diagrams
3. HTML documentation
4. Asciidoctor documentation

Run:

```bash
mvn test -Dtest=ModuleDocumentationTests
```

## Integrating with jQAssistant

Spring Modulith works well with jQAssistant:

```java
@ArchTest
static ArchRule should_follow_spring_modulith_conventions = ArchRuleDefinition.allClasses()
    .that().areAnnotatedWith(Module.class)
    .should().resideInAPackage("com.example..module..")
    .orShould().resideInAPackage("com.example..modulith..");
```

## Best Practices

1. **Keep modules small**: Each module should address one business capability
2. **Limit dependencies**: Minimize cross-module dependencies
3. **Explicit APIs**: Use `@PublicApi` to define clear boundaries
4. **Document thoroughly**: Generate module documentation regularly
5. **Test boundaries**: Use Spring Modulith tests to validate architecture

## Tools and Integrations

- **jQAssistant**: Static analysis and documentation
- **Spring Boot DevTools**: Hot reloading for faster development
- **Spring Boot Actuator**: Health checks and metrics
- **Docker**: Containerization for deployment

## Resources

- [Spring Modulith Documentation](https://docs.spring.io/spring-modulith/reference/index.html)
- [jMolecules Reference](https://jmolecules.org/reference/)
- [Spring Modulith Examples](https://github.com/spring-projects/spring-modulith-examples)