package edu.okei.reward.auth.model

import edu.okei.reward.common.model.UIEvent

sealed class AuthEvent : UIEvent(){
    data class InputLogin(val login:String):AuthEvent()
    data class InputPassword(val password: String): AuthEvent()
    data object SignIn : AuthEvent()
}