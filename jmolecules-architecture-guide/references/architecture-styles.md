# Architecture Styles

This document provides information about the different architecture styles supported by jMolecules.

## Layered Architecture

Layered architecture follows strict unidirectional dependency rules where the domain layer is the most independent:

```
┌─────────────────────┐
│  Interface Layer    │
│ (Controllers, APIs) │
│        DEPENDS ON    │
└─────────────────────┘
          ↓
┌─────────────────────┐
│ Application Layer   │
│  (Use Cases)        │
│        DEPENDS ON    │
└─────────────────────┘
          ↓
┌─────────────────────┐
│  Domain Layer       │
│  (Business Logic)   │
│    MOST INDEPENDENT  │
│  NO DEPENDENCIES ON OTHER LAYERS  │
└─────────────────────┘
          ↑
┌─────────────────────┐
│ Infrastructure Layer│
│ (Data Access, APIs) │
│        DEPENDS ON    │
└─────────────────────┘
```

### Key Principles (Modern Interpretation)
- **Domain layer is the core and most independent** - Contains pure business logic
- **Strict dependency inversion** - Lower layers (infrastructure) depend on higher layers (domain)
- **Application layer mediates between interface and domain** - Coordinates business logic
- **Infrastructure layer supports the domain** - Provides implementations of interfaces defined in higher layers

### Dependency Flow (Correct Modern Approach)
```
Interface Layer → Application Layer → Domain Layer ← Infrastructure Layer
```

This approach aligns with modern architecture principles like Dependency Inversion and Clean Architecture.

### jMolecules Annotations
- `@DomainLayer` - For business logic (**most independent**)
- `@ApplicationLayer` - For use cases (depends on domain layer)
- `@InfrastructureLayer` - For data access and external API (**depends on domain layer**)
- `@InterfaceLayer` - For controllers and API endpoints (depends on application layer)

### Common Violations to Avoid
1. Domain layer should **never** depend on infrastructure layer
2. Application layer should **never** depend directly on infrastructure layer
3. Infrastructure layer should **only** depend on interfaces defined in higher layers

### Traditional vs Modern Interpretation

**Traditional (Incorrect) Approach:**
```
Interface → Application → Domain → Infrastructure (Domain depends on Infrastructure)
```

**Modern (Correct) Approach:**
```
Interface → Application → Domain ← Infrastructure (Infrastructure depends on Domain)
```

The modern approach is recommended by jMolecules and aligns with DDD and Clean Architecture principles.

## Onion Architecture

Onion architecture follows strict inward dependency rules:

```
┌─────────────────────────────────┐
│ Infrastructure (Adapters)       │
│        DEPENDS ON                │
└─────────────────────────────────┘
          ↓
┌─────────────────────────────────┐
│ Application Services            │
│        DEPENDS ON                │
└─────────────────────────────────┘
          ↓
┌─────────────────────────────────┐
│ Domain Services                  │
│        DEPENDS ON                │
└─────────────────────────────────┘
          ↓
┌─────────────────────────────────┐
│ Domain Model (Entities, VO)     │
│    MOST INDEPENDENT               │
└─────────────────────────────────┘
```

### Key Principles
- **Dependencies point inward** - All outer layers depend on inner layers
- **Domain model is at the center and most independent** - Contains pure business logic
- **Inversion of Control (IoC)** - External dependencies are inverted
- **Clear separation between core and infrastructure** - Inner layers define interfaces, outer layers implement them

### Dependency Flow
```
Infrastructure → Application Services → Domain Services → Domain Model
```

All dependencies go from outer layers to inner layers. The domain model is completely independent.

### jMolecules Annotations
- `@DomainModelRing` - For the core domain model (**most independent**)
- `@DomainServiceRing` - For domain services (depends on domain model)
- `@ApplicationServiceRing` - For application services (depends on domain services)
- `@InfrastructureRing` - For infrastructure and adapters (**depends on all inner layers**)

## Hexagonal Architecture

Hexagonal architecture (Ports and Adapters) focuses on the application core with strict dependency inversion:

```
┌─────────────────────────────────┐
│  Primary Adapters (Driving)     │
│ (UI, API, CLI)                  │
│         DEPENDS ON              │
└─────────────────────────────────┘
          ↓
┌─────────────────────────────────┐
│  Primary Ports                  │
│ (Application Interfaces)        │
│         DEPENDS ON              │
└─────────────────────────────────┘
          ↓
┌─────────────────────────────────┐
│  Application Core               │
│ (Business Logic)                │
│         DEPENDED ON BY          │
└─────────────────────────────────┘
          ↑
┌─────────────────────────────────┐
│  Secondary Ports                │
│ (Database, External APIs)       │
│         DEPENDED ON BY          │
└─────────────────────────────────┘
          ↑
┌─────────────────────────────────┐
│  Secondary Adapters (Driven)    │
│ (Implementations)               │
└─────────────────────────────────┘
```

### Key Principles
- **Application core is at the center and independent of external systems**
- **Ports define interfaces that the core needs**
- **Adapters implement these interfaces and adapt to external systems**
- **Dependencies point inward towards the application core**
- **Inversion of Control (IoC) principle strictly followed**

### Dependency Flow

```
Primary Adapters (UI) → Primary Ports → Application Core ← Secondary Ports ← Secondary Adapters (Database)
```

- **Primary side (driving)**：UI → API → Application（normal flow）
- **Secondary side (driven)**：Application → Port → Adapter（inverted flow）

### jMolecules Annotations
- `@Application` - For the application core
- `@PrimaryPort` - For primary (driving) ports
- `@SecondaryPort` - For secondary (driven) ports
- `@PrimaryAdapter` - For primary adapter implementations
- `@SecondaryAdapter` - For secondary adapter implementations

## CQRS Architecture

CQRS (Command Query Responsibility Segregation) separates command and query operations:

```
┌─────────────────────────────────┐
│  Command Side                   │
│ (Write Operations)              │
└─────────────────────────────────┘
          ↓
┌─────────────────────────────────┐
│  Event Store                    │
└─────────────────────────────────┘
          ↓
┌─────────────────────────────────┐
│  Query Side                     │
│ (Read Operations)               │
└─────────────────────────────────┘
```

### Key Principles
- Separate models for reads and writes
- Events propagate state changes
- Optimized data stores for each side
- High scalability and performance

### jMolecules Annotations
- `@Command` - For command messages
- `@CommandDispatcher` - For command dispatching
- `@CommandHandler` - For command handling
- `@QueryModel` - For query models

## Choosing an Architecture Style

Consider these factors:

1. **Project Size**: Layered for small, Hexagonal for large
2. **Complexity**: Onion or Hexagonal for complex business logic
3. **Team Expertise**: Choose styles your team is comfortable with
4. **Requirements**: CQRS for high-read or high-write systems
5. **Time Constraints**: Layered is quickest to implement

jMolecules allows you to start with one style and evolve as needed.