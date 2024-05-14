package edu.okei.reward

import edu.okei.core.domain.model.criteria.CriterionModel
import edu.okei.reward.criteria.model.CriterionBuilder
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class BuilderCriterionUnitTest {
    private lateinit var builder: CriterionBuilder
    @Before
    fun createDb() {
        val listDescription: List<String> = listOf(
            "Low",
            "LowMax",
            "Optional",
            "Medium",
            "High",
            "High",
            "Critical",
            "Optional",
            "Mandatory",
            "Critical",
            "Critical",
            "Low",
            "Critical",
        ) // Список рандомных повторяющихся Descriptions
        builder = CriterionBuilder(listDescription)
    }
    @Test
    fun suggestDescriptions_isCorrect_withFilter(){
        assertEquals(
            setOf("Low","LowMax", "Medium", "High", "Critical", "Optional", "Mandatory"),
            builder.suggestDescriptions().toSet()
        )
        assertEquals(
            setOf("Low", "LowMax"),
            builder.suggestDescriptions("lo").toSet()
        )
        assertEquals(
            setOf("High"),
            builder.suggestDescriptions("H").toSet()
        )
    }
    @Test
    fun serialNumber_isCorrect_BadInput() {
        assertEquals(false, builder.isSerialNumber("."))
        assertEquals(false, builder.isSerialNumber(".1"))
        assertEquals(false, builder.isSerialNumber(".1.2"))
        assertEquals(false, builder.isSerialNumber("1.."))
        assertEquals(false, builder.isSerialNumber("1.2.."))
    }

    @Test
    fun serialNumber_isCorrect_GoodInput(){
        val builder = CriterionBuilder(listOf())
        assertEquals(true, builder.isSerialNumber("1"))
        assertEquals(true, builder.isSerialNumber("2."))
        assertEquals(true, builder.isSerialNumber("2.1"))
        assertEquals(true, builder.isSerialNumber("2.1."))
        assertEquals(true, builder.isSerialNumber("2.3.4"))
    }

}