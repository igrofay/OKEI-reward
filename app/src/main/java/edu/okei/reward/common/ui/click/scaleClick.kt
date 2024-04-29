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
import androidx.compose.ui.draw.scale


fun Modifier.scaleClick(enabled: Boolean = true, minScale: Float = 0.9f,onClick: () -> Unit) = composed {
    val mutableInteractionSource = remember {
        MutableInteractionSource()
    }
    val isPressed by mutableInteractionSource.collectIsPressedAsState()
    val sizeScale by animateFloatAsState(
        if (isPressed) minScale else 1f,
        label = "anim scale button",
    )
    val alphaAnim by animateFloatAsState(
        targetValue = if (enabled) 1f else 0f,
        label = "anim alpha button"
    )
    this
        .clickable(mutableInteractionSource, null, enabled = enabled, onClick = onClick)
        .scale(sizeScale)
        .alpha(alphaAnim)

}