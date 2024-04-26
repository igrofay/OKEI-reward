package edu.okei.reward.auth.model

import edu.okei.core.domain.model.auth.AuthModel
import edu.okei.reward.common.model.UIState

data class AuthState(
    override val login: String = "",
    override val password: String = ""
) : AuthModel, UIState(){
    fun prepare() = copy(
        login = login.trim(),
        password = password.trim()
    )
}