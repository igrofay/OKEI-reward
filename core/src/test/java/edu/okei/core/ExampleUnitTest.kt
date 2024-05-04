package edu.okei.core

import org.junit.Test

import org.junit.Assert.*
import java.util.Locale

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun name_isCorrect() {
        val input =  correctName("  пушилин георгий   ")
        assertEquals("Пушилин Георгий", input)
    }
    fun correctName(text: String) = text.trim()
        .split(" ")
        .joinToString(" ") { str ->
            str.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
        }
}