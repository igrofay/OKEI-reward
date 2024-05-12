package edu.okei.reward.calculation_of_reward.model

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class SuffixVisualTransformation(private val suffix: String) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val annotatedString = AnnotatedString(text.text + suffix)
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return offset
            }

            override fun transformedToOriginal(offset: Int): Int {
                return if (offset < text.text.length) offset else text.text.length
            }
        }
        return TransformedText(annotatedString, offsetMapping)
    }
}