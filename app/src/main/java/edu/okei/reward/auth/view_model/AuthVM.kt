package edu.okei.reward.auth.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import edu.okei.core.domain.model.errors.AuthError
import edu.okei.core.domain.model.user.UserRole
import edu.okei.core.domain.use_case.auth.AuthUseCase
import edu.okei.reward.R
import edu.okei.reward.auth.model.AuthEvent
import edu.okei.reward.auth.model.AuthSideEffect
import edu.okei.reward.auth.model.AuthState
import edu.okei.reward.common.view_model.AppVM
import kotlinx.coroutines.Job
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class AuthVM(
    override val di: DI,
) : AppVM<AuthState, AuthSideEffect, AuthEvent>(), DIAware {

    private val authUseCase by di.instance<AuthUseCase>()
    private var signInJob: Job? = null
    override val container: Container<AuthState, AuthSideEffect> =
        viewModelScope.container(AuthState())

    override fun onEvent(event: AuthEvent) {
        when(event){
            is AuthEvent.InputLogin -> blockingIntent {
                reduce {
                    state.copy(login = event.login)
                }
            }
            is AuthEvent.InputPassword -> blockingIntent {
                reduce {
                    state.copy(password = event.password)
                }
            }
            AuthEvent.SignIn ->{
                signInJob?.cancel()
                signInJob = signIn()
            }
        }
    }

    private fun signIn() = intent {
        authUseCase.execute(state.prepare()).fold(
            onSuccess = { role ->
                when(role){
                    UserRole.Appraiser -> postSideEffect(AuthSideEffect.OpenAppraiserContent)
                    else -> showMessage(R.string.no_rights)
                }
            },
            onFailure = ::onError
        )
    }

    private fun showMessage(message: Int) = intent {
        postSideEffect(AuthSideEffect.ShowMessage(message))
    }

    override fun onError(er: Throwable) {
        when(er){
            AuthError.UserNotFound ->
                showMessage(R.string.user_not_found)
            AuthError.WrongPassword->
                showMessage(R.string.wrong_password)
            else -> Log.e(nameVM, er.message, er)
        }
    }
}