package edu.okei.reward.splash.view_model

import androidx.lifecycle.viewModelScope
import edu.okei.reward.common.model.UIEvent
import edu.okei.reward.common.model.UIState
import edu.okei.reward.common.view_model.AppVM
import edu.okei.reward.splash.model.SplashSideEffect
import org.orbitmvi.orbit.container

class SplashVM : AppVM<UIState.NoState, SplashSideEffect, UIEvent>() {

    override val container =
        viewModelScope.container<UIState.NoState, SplashSideEffect>(UIState.NoState)

    override fun onEvent(event: UIEvent) {}

    override fun onError(er: Throwable) {

    }
}