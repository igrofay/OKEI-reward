package edu.okei.reward.splash.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import edu.okei.core.domain.model.errors.AuthError
import edu.okei.core.domain.model.user.UserRole
import edu.okei.core.domain.use_case.auth.RestartSessionUseCase
import edu.okei.reward.common.model.UIEvent
import edu.okei.reward.common.model.UIState
import edu.okei.reward.common.view_model.AppVM
import edu.okei.reward.splash.model.SplashSideEffect
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

class SplashVM(
    override val di: DI
) : AppVM<UIState.NoState, SplashSideEffect, UIEvent>(), DIAware {
    private val restartSessionUseCase by di.instance<RestartSessionUseCase>()

    override val container =
        viewModelScope.container<UIState.NoState, SplashSideEffect>(UIState.NoState) {
            initUserRole()
        }

    private fun initUserRole() = intent {
        restartSessionUseCase.execute().fold(
            onSuccess = { role ->
                when (role) {
                    UserRole.Appraiser -> postSideEffect(SplashSideEffect.OpenAppraiserContent)
                    else -> postSideEffect(SplashSideEffect.OpenAuth)
                }
            },
            onFailure = ::onError
        )
    }

    override fun onEvent(event: UIEvent) {}

    override fun onError(er: Throwable) {
        when(er){
            AuthError.BadToken -> intent {
                postSideEffect(SplashSideEffect.OpenAuth)
            }
            else -> Log.e(nameVM, er.message, er)
        }
    }
}