package org.kmolecules.ddd.types

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Unit tests for [Association]
 *
 * @author Jocelyn Ntakpe
 */
internal class AssociationUnitTests {

    @Test
    fun `creates association from identifier`() {
        val id = SampleIdentifier()
        assertThat(Association.forId<SampleAggregate, SampleIdentifier>(id).id).isEqualTo(id)
    }

    @Test
    fun `creates association from aggregate`() {
        val id = SampleIdentifier()
        val aggregate = SampleAggregate(id)
        assertThat(Association.forAggregate(aggregate).id).isEqualTo(id)
    }

    @Test
    fun `associations equal if they point to the same id`() {
        val id = SampleIdentifier()
        val aggregate = SampleAggregate(id)
        assertThat(Association.forId<SampleAggregate, SampleIdentifier>(id)).isEqualTo(Association.forAggregate(aggregate))
        assertThat(Association.forAggregate(aggregate)).isEqualTo(Association.forId<SampleAggregate, SampleIdentifier>(id))
    }

    @Test
    fun `points to same aggregate`() {
        val id = SampleIdentifier()
        val simpleAssociation: Association<Nothing, SampleIdentifier> = Association.forId(id)
        val sampleAssociation = SampleAssociation(id)
        assertThat(simpleAssociation.pointsToSameAggregateAs(sampleAssociation)).isTrue
        assertThat(sampleAssociation.pointsToSameAggregateAs(simpleAssociation)).isTrue
    }

    class SampleIdentifier : Identifier

    class SampleAggregate(override val id: SampleIdentifier) : AggregateRoot<SampleAggregate, SampleIdentifier>

    class SampleAssociation(override val id: SampleIdentifier) : Association<SampleAggregate, SampleIdentifier>
}
