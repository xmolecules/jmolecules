package org.jmolecules.ddd.types;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import lombok.Value;

class AbstractEntityTest {

	@Value
    static class Id implements Identifier {

        long id;

        public static Id of(long id) {
            return new Id(id);
		}
		
		public String toString() {
			return "" + id;
		}

    }
	
	static class ConcreteEntity extends AbstractEntity<ConcreteEntity, Id> implements AggregateRoot<ConcreteEntity, Id> {

		public ConcreteEntity(Id id) {
			super(id);
        }

	}

	@Test
	void givenANewEntity_whenGetId_thenEqual() {
		// given
		ConcreteEntity entityUnderTest = new ConcreteEntity(Id.of(47L)) {
		};
		
		// when
		Id id = entityUnderTest.getId();
		
		// then
		assertThat(id).isEqualTo(Id.of(47L));
	}

	@Test
	void givenTwoEntitiesOfSameTypeWithSameId_whenEquals_thenTrue() {
		// given
		ConcreteEntity entity1 = new ConcreteEntity(Id.of(47L));
		ConcreteEntity entity2 = new ConcreteEntity(Id.of(47L));
		
		// when
		boolean equal = entity1.equals(entity2);
		
		// then
		assertThat(equal).isTrue();
	}
	
	@Test
	void givenTwoEntitiesOfDifferentTypeWithSameId_whenEquals_thenFalse() {
		// given
		ConcreteEntity entity1 = new ConcreteEntity(Id.of(47L)) { };
		ConcreteEntity entity2 = new ConcreteEntity(Id.of(47L)) { };
		
		// when
		boolean equal = entity1.equals(entity2);
		
		// then
		assertThat(equal).isFalse();
	}
	
	@Test
	void givenAnEntity_whenToString_thenClassnamePlusId() {
		// given
		ConcreteEntity entityUnderTest = new ConcreteEntity(Id.of(47L));
		
		// when
		String string = entityUnderTest.toString();
		
		// then
		assertThat(string).isEqualTo("ConcreteEntity [id=47]");
	}
	
}
