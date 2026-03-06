---
name: jmolecules-arch-guide
description: jMolecules architecture guidance for AI coding. Provides intelligent support for recognizing, validating, and generating code with jMolecules architectural abstractions. Use this skill when working with DDD, layered, onion, hexagonal, or CQRS architecture styles in Java/Kotlin projects.
---

# jMolecules Architecture Guide

## Overview

This skill provides AI-powered guidance for working with jMolecules architectural abstractions. It helps developers recognize architecture concepts in code, validate architecture compliance using existing tools, suggest improvements, generate architecture skeletons, and visualize architectural structures.

## Core Capabilities

### 1. Architecture Concept Recognition

Automatically recognize and identify jMolecules architectural abstractions in code:
- **DDD Building Blocks**: `@Entity`, `@AggregateRoot`, `@ValueObject`, `@Repository`, `@Service`, `@Factory`
- **Architecture Layers**: `@DomainLayer`, `@ApplicationLayer`, `@InfrastructureLayer`, `@InterfaceLayer`
- **Event-Driven Components**: `@DomainEvent`, `@DomainEventHandler`, `@DomainEventPublisher`
- **Architecture Styles**: Layered, onion, hexagonal, CQRS architecture annotations

### 2. Architecture Validation Guidance

Guide developers on using existing tools for architecture validation:

#### jQAssistant
- Generate Cypher queries to find architecture patterns
- Interpret jQAssistant analysis results
- Help configure jQAssistant rules for jMolecules

#### ArchUnit
- Generate ArchUnit rules for architecture violation detection
- Provide examples of common ArchUnit checks
- Explain how to integrate ArchUnit into build process

#### Spring Modulith
- Guide on using Spring Modulith for module decomposition
- Explain how to create module documentation
- Show how to integrate with jMolecules

### 3. Improvement Suggestions

Provide intelligent suggestions for improving architecture:
- Recommend where to add jMolecules annotations
- Suggest refactorings to convert primitive fields to Value Objects
- Propose architecture style changes (e.g., from layered to hexagonal)
- Identify opportunities to better separate concerns

### 4. Architecture Skeleton Generation

Generate complete architecture skeletons from high-level descriptions:
- Create DDD aggregates with entities, value objects, and repositories
- Generate layered architecture structures (domain, application, infrastructure)
- Create event-driven components (events, handlers, publishers)
- Support multiple architecture styles (layered, onion, hexagonal, CQRS)

### 5. Visualization and Documentation

Visualize and document architecture:
- Generate Mermaid diagrams of architecture structures
- Create jMolecules configuration files (jmolecules-stereotypes.json)
- Produce markdown documentation from code annotations
- Export architecture information for reporting

## Usage Scenarios

### Scenario 1: Analyze Existing Code

When analyzing existing code:
1. Read and analyze the codebase
2. Identify existing architecture patterns and annotations
3. Suggest appropriate jQAssistant, ArchUnit, or Spring Modulith checks
4. Interpret and explain tool output
5. Provide improvement suggestions

### Scenario 2: Create New Architecture

When creating a new architecture:
1. Get high-level requirements from user
2. Choose appropriate architecture style
3. Generate complete architecture skeleton
4. Add documentation and visualization
5. Configure architecture validation tools

### Scenario 3: Refactor Architecture

When refactoring existing architecture:
1. Analyze current architecture
2. Identify refactoring opportunities
3. Apply suggested refactorings
4. Validate the new architecture using tools
5. Update documentation and configuration

## Resources

This skill includes:

### references/
- [jmolecules-stereotypes.json](./references/jmolecules-stereotypes.json) - jMolecules stereotypes configuration
- [ddd-best-practices.md](./references/ddd-best-practices.md) - DDD implementation guidelines
- [architecture-styles.md](./references/architecture-styles.md) - Architecture style comparisons
- [jqassistant-examples.md](./references/jqassistant-examples.md) - jQAssistant query examples
- [archunit-examples.md](./references/archunit-examples.md) - ArchUnit rule examples
- [spring-modulith.md](./references/spring-modulith.md) - Spring Modulith integration guide

### assets/
- [templates/](./assets/templates/) - Architecture skeleton templates
- [mermaid/](./assets/mermaid/) - Mermaid diagram templates
