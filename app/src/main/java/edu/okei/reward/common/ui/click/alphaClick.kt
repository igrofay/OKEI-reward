package edu.okei.reward.common.ui.click


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha

fun Modifier.alphaClick(enabled: Boolean = true, minAlpha: Float = 0.6f,onClick: ()-> Unit) = composed {
    val mutableInteractionSource = remember {
        MutableInteractionSource()
    }
    val isPressed by mutableInteractionSource.collectIsPressedAsState()
    val alphaAnim by animateFloatAsState(
        targetValue = if (isPressed) minAlpha else 1f,
        label = "anim alpha button"
    )
    this
        .clickable(mutableInteractionSource, null, enabled= enabled, onClick = onClick)
        .alpha(alphaAnim)
}