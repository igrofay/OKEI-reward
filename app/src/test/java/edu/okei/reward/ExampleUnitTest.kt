package edu.okei.reward

import edu.okei.reward.criteria.model.CriterionBuilder
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class BuilderCriterionUnitTest {
    @Test
    fun serialNumber_isCorrect_BadInput() {
        val builder = CriterionBuilder(listOf())
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
    @Test
    fun serialNumber_isCorrect_TrimPoint(){
        assertEquals("2", "2.".trim('.'))
        assertEquals("1.3", "1.3.".trim('.'))
    }
}