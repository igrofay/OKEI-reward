package edu.okei.reward.common.model

import androidx.compose.runtime.Stable

@Stable
abstract class UISideEffect  {
    object NoSideEffect: UISideEffect()
}