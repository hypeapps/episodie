package pl.hypeapp.dataproviders.entity.mapper

import org.amshove.kluent.`should equal to`
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test

class EntityMapperTest {

    private lateinit var mapper: Mapper<TestModel, TestEntity>

    @Before
    fun setUp() {
        mapper = EntityMapperTestClass()
    }

    @Test
    fun `should transform single entity to model`() {
        val testEntity: TestEntity = TestEntity("name", 1)
        val testModel: TestModel? = mapper.transform(testEntity)
        testEntity.name `should equal to` testModel!!.name!!
        testEntity.id `should equal to` testModel.id!!
    }

    @Test
    fun `should transform list of entites to list models`() {
        val testEntites: List<TestEntity> = listOf(TestEntity("name1", 1), TestEntity("name2", 2))
        val testModels: List<TestModel> = mapper.transform(testEntites)
        testModels.size `should equal to` 2
        for (i in 0..1) {
            testEntites[i].name `should equal` testModels[i].name
            testEntites[i].id `should equal` testModels[i].id
        }
    }

    private class EntityMapperTestClass : Mapper<TestModel, TestEntity>() {
        override fun transform(item: TestEntity?): TestModel? = TestModel(item?.name, item?.id)

    }

    private data class TestModel(val name: String?, val id: Int?)

    private data class TestEntity(val name: String, val id: Int)

}
