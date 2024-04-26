package edu.okei.reward.auth.model

import edu.okei.reward.common.model.UISideEffect

sealed class AuthSideEffect : UISideEffect() {
    data class ShowMessage(val message: Int) : AuthSideEffect()
    data object OpenAppraiserContent : AuthSideEffect()
}