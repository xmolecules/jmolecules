# ArchUnit Examples

This document contains example ArchUnit rules for validating jMolecules architecture.

## Getting Started

First, add the required dependencies:

```xml
<dependencies>
    <dependency>
        <groupId>com.tngtech.archunit</groupId>
        <artifactId>archunit-junit5</artifactId>
        <version>1.2.1</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Basic Layer Dependency Rules

```java
@AnalyzeClasses(packages = "com.example")
class ArchitectureTests {

    // Domain layer should not depend on any other layer
    @ArchTest
    static final ArchRule domain_layer_should_not_depend_on_other_layers = noClasses()
            .that().areAnnotatedWith(DomainLayer.class)
            .should().dependOnClassesThat().areAnnotatedWith(ApplicationLayer.class)
            .orShould().dependOnClassesThat().areAnnotatedWith(InfrastructureLayer.class)
            .orShould().dependOnClassesThat().areAnnotatedWith(InterfaceLayer.class);

    // Application layer should not depend on infrastructure layer
    @ArchTest
    static final ArchRule application_layer_should_not_depend_on_infrastructure = noClasses()
            .that().areAnnotatedWith(ApplicationLayer.class)
            .should().dependOnClassesThat().areAnnotatedWith(InfrastructureLayer.class);

    // Infrastructure layer should not depend on interface layer
    @ArchTest
    static final ArchRule infrastructure_layer_should_not_depend_on_interface = noClasses()
            .that().areAnnotatedWith(InfrastructureLayer.class)
            .should().dependOnClassesThat().areAnnotatedWith(InterfaceLayer.class);
}
```

## Value Object Rules

```java
@AnalyzeClasses(packages = "com.example")
class ValueObjectTests {

    // Classes annotated with @ValueObject should not have setters
    @ArchTest
    static final ArchRule value_objects_should_be_immutable = noClasses()
            .that().areAnnotatedWith(ValueObject.class)
            .should().haveMethodsThat().arePublic()
            .and().haveNameMatching("set.*")
            .because("Value objects should be immutable");

    // Classes annotated with @ValueObject should implement equals() and hashCode()
    @ArchTest
    static final ArchRule value_objects_should_implement_equals_and_hashCode = allClasses()
            .that().areAnnotatedWith(ValueObject.class)
            .should().implementEqualsAndHashCode()
            .because("Value objects need to be compared by value");
}
```

## Aggregate Root Rules

```java
@AnalyzeClasses(packages = "com.example")
class AggregateRootTests {

    // Aggregate roots should be the only classes with @Entity annotation
    @ArchTest
    static final ArchRule only_aggregate_roots_should_be_entities = allClasses()
            .that().areAnnotatedWith(Entity.class)
            .should().beAnnotatedWith(AggregateRoot.class)
            .because("Only aggregate roots should be directly persistent");

    // Repositories should only work with aggregate roots
    @ArchTest
    static final ArchRule repositories_should_work_with_aggregate_roots = allClasses()
            .that().areAnnotatedWith(Repository.class)
            .should().onlyDependOnClassesThat().areAnnotatedWith(AggregateRoot.class)
            .orShould().onlyDependOnClassesThat().areAssignableTo(Collection.class)
            .orShould().onlyDependOnClassesThat().arePrimitiveTypes()
            .because("Repositories should only manage aggregate roots");
}
```

## Event-Driven Architecture Rules

```java
@AnalyzeClasses(packages = "com.example")
class EventDrivenTests {

    // Event handlers should only depend on domain events
    @ArchTest
    static final ArchRule event_handlers_should_depend_on_events = allClasses()
            .that().areAnnotatedWith(DomainEventHandler.class)
            .should().onlyDependOnClassesThat().areAnnotatedWith(DomainEvent.class)
            .orShould().onlyDependOnClassesThat().areAnnotatedWith(Repository.class)
            .because("Event handlers should only process events and access data");

    // Events should be immutable
    @ArchTest
    static final ArchRule events_should_be_immutable = noClasses()
            .that().areAnnotatedWith(DomainEvent.class)
            .should().haveMethodsThat().arePublic()
            .and().haveNameMatching("set.*")
            .because("Events should be immutable once published");
}
```

## Package Structure Rules

```java
@AnalyzeClasses(packages = "com.example")
class PackageStructureTests {

    // Classes in domain package should be in domain layer
    @ArchTest
    static final ArchRule domain_package_classes_should_be_domain_layer = allClasses()
            .that().resideInAPackage("..domain..")
            .should().beAnnotatedWith(DomainLayer.class)
            .because("Classes in domain package should belong to domain layer");

    // Classes in infrastructure package should be in infrastructure layer
    @ArchTest
    static final ArchRule infrastructure_package_classes_should_be_infrastructure = allClasses()
            .that().resideInAPackage("..infrastructure..")
            .should().beAnnotatedWith(InfrastructureLayer.class)
            .because("Classes in infrastructure package should belong to infrastructure layer");
}
```

## Custom Rule Example

```java
@AnalyzeClasses(packages = "com.example")
class CustomRules {

    // Classes should be in the correct package based on annotation
    @ArchTest
    static final ArchRule classes_should_be_in_correct_package = allClasses()
            .that().areAnnotatedWith(ValueObject.class)
            .should().resideInAPackage("..valueobject..")
            .orShould().resideInAPackage("..vo..")
            .because("Value objects should be in dedicated packages");

    // Services should be interfaces or abstract classes
    @ArchTest
    static final ArchRule services_should_be_interfaces = allClasses()
            .that().areAnnotatedWith(Service.class)
            .should().beInterfaces()
            .orShould().beAbstractClasses()
            .because("Services should be abstractions");
}
```

## Running the Tests

Run the tests as regular JUnit tests:

```bash
mvn test
```

or in your IDE.