package edu.okei.reward.auth.model

import androidx.annotation.StringRes
import edu.okei.reward.common.model.UISideEffect

sealed class AuthSideEffect : UISideEffect() {
    data class ShowMessage(@StringRes val message: Int) : AuthSideEffect()
    data object OpenAppraiserContent : AuthSideEffect()
}