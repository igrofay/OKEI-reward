package edu.okei.reward.splash.model

import edu.okei.reward.common.model.UISideEffect

sealed class SplashSideEffect : UISideEffect(){
    data object OpenAuth: SplashSideEffect()
    data object ShowUserContent : SplashSideEffect()
}