# jQAssistant Examples

This document contains example Cypher queries for querying jMolecules architecture with jQAssistant.

## Getting Started

jQAssistant uses Neo4j for storing code structure information. Install jQAssistant and run:

```bash
mvn jqassistant:scan
mvn jqassistant:analyze
```

## Basic Queries

### Find all classes with jMolecules annotations

```cypher
MATCH (c:Class)
WHERE exists(c.`jmolecules:annotations`)
RETURN c.fqn AS className, c.`jmolecules:annotations` AS jmoleculesAnnotations
ORDER BY c.fqn
```

### Find all DDD building blocks

```cypher
MATCH (c:Class)
WHERE
  exists(c.`jmolecules:annotation:Entity`) OR
  exists(c.`jmolecules:annotation:AggregateRoot`) OR
  exists(c.`jmolecules:annotation:ValueObject`) OR
  exists(c.`jmolecules:annotation:Repository`) OR
  exists(c.`jmolecules:annotation:Service`) OR
  exists(c.`jmolecules:annotation:Factory`)
RETURN
  c.fqn AS className,
  CASE
    WHEN exists(c.`jmolecules:annotation:Entity`) THEN 'Entity'
    WHEN exists(c.`jmolecules:annotation:AggregateRoot`) THEN 'AggregateRoot'
    WHEN exists(c.`jmolecules:annotation:ValueObject`) THEN 'ValueObject'
    WHEN exists(c.`jmolecules:annotation:Repository`) THEN 'Repository'
    WHEN exists(c.`jmolecules:annotation:Service`) THEN 'Service'
    WHEN exists(c.`jmolecules:annotation:Factory`) THEN 'Factory'
  END AS type
ORDER BY type, className
```

## Layer Architecture Queries

### Find layer dependencies

```cypher
MATCH (s:Class)-[:DEPENDS_ON]->(t:Class)
WHERE
  exists(s.`jmolecules:annotation:DomainLayer`) AND
  exists(t.`jmolecules:annotation:ApplicationLayer`)
RETURN
  s.fqn AS source,
  t.fqn AS target,
  'Domain → Application' AS dependencyType
```

### Find layer violations

```cypher
MATCH (s:Class)-[:DEPENDS_ON]->(t:Class)
WHERE
  exists(s.`jmolecules:annotation:DomainLayer`) AND
  exists(t.`jmolecules:annotation:InfrastructureLayer`)
RETURN
  s.fqn AS source,
  t.fqn AS target
```

## Value Object Queries

### Find potential value objects

```cypher
MATCH (c:Class)-[:DECLARES]->(f:Field)
WHERE
  NOT exists(c.`jmolecules:annotation:ValueObject`) AND
  f.type IN ['String', 'int', 'long', 'double', 'float', 'boolean', 'LocalDate', 'LocalDateTime'] AND
  NOT f.name IN ['id', 'name', 'description']
RETURN
  c.fqn AS className,
  f.name AS fieldName,
  f.type AS fieldType
ORDER BY className
```

## Aggregate Root Queries

### Find aggregates with their entities

```cypher
MATCH (ar:Class)-[:DECLARES]->(f:Field)
WHERE
  exists(ar.`jmolecules:annotation:AggregateRoot`) AND
  exists(f.`jmolecules:annotation:Entity`)
RETURN
  ar.fqn AS aggregateRoot,
  collect(f.name) AS entities
```

## Repository Queries

### Find all repositories

```cypher
MATCH (r:Class)
WHERE exists(r.`jmolecules:annotation:Repository`)
RETURN r.fqn AS repositoryName
ORDER BY r.fqn
```

### Find repositories with dependencies

```cypher
MATCH (r:Class)-[:DEPENDS_ON]->(d:Class)
WHERE exists(r.`jmolecules:annotation:Repository`)
RETURN r.fqn AS repository, d.fqn AS dependency
ORDER BY repository
```

## Event-Driven Architecture Queries

### Find all domain events

```cypher
MATCH (e:Class)
WHERE exists(e.`jmolecules:annotation:DomainEvent`)
RETURN e.fqn AS eventName
ORDER BY e.fqn
```

### Find event handlers and their events

```cypher
MATCH (h:Class)-[:DEPENDS_ON]->(e:Class)
WHERE
  exists(h.`jmolecules:annotation:DomainEventHandler`) AND
  exists(e.`jmolecules:annotation:DomainEvent`)
RETURN
  h.fqn AS handler,
  e.fqn AS event
```

## Architecture Analysis Queries

### Check layer structure compliance

```cypher
MATCH (c:Class)
WHERE
  exists(c.`jmolecules:annotation:DomainLayer`) AND
  (exists(c.`jmolecules:annotation:ApplicationLayer`) OR exists(c.`jmolecules:annotation:InfrastructureLayer`))
RETURN c.fqn AS invalidClass
```

### Find classes with multiple layer annotations

```cypher
MATCH (c:Class)
WHERE
  exists(c.`jmolecules:annotation:DomainLayer`) AND
  exists(c.`jmolecules:annotation:ApplicationLayer`)
RETURN c.fqn AS classWithMultipleLayerAnnotations
```

## Custom Queries

### Create your own rules file

Create a `jqassistant/rules` directory with a `.cypher` file:

```cypher
# my-architecture.rules.cypher
@id: "my-architecture:layer-dependency-violation"
@severity: "MAJOR"
@message: "Domain layer should not depend on infrastructure layer"
MATCH (s:Class)-[:DEPENDS_ON]->(t:Class)
WHERE
  exists(s.`jmolecules:annotation:DomainLayer`) AND
  exists(t.`jmolecules:annotation:InfrastructureLayer`)
RETURN
  s.fqn AS Source,
  t.fqn AS Target
```

## Visualization Queries

### Generate architecture diagram

```cypher
MATCH (c:Class)-[d:DEPENDS_ON]->(e:Class)
WHERE
  exists(c.`jmolecules:annotations`) OR
  exists(e.`jmolecules:annotations`)
RETURN *
```

Use the jQAssistant web interface to visualize this.

## Running Queries

You can run queries from the command line:

```bash
mvn jqassistant:execute -Djqassistant.query="your-cypher-query"
```

or from the web interface: http://localhost:7474

## Further Reading

- [jQAssistant Documentation](https://jqassistant.org/documentation/)
- [Cypher Query Language](https://neo4j.com/developer/cypher/)
- [jMolecules jQAssistant Integration](https://github.com/xmolecules/jmolecules-jqassistant)