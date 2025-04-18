package part3

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import part3.entities.Entity
import part3.enums.Material

class EntityTest {

    @Test
    fun `Create entity`() {
        val entity = TestEntity(Material.METAL)
        assertTrue(entity.material == Material.METAL)
    }

    private class TestEntity(override val material: Material) : Entity()
}