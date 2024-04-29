package edu.okei.core.domain.model.errors

sealed class AuthError : AppErrors(){
    data object WrongPassword : AuthError()
    data object UserNotFound : AuthError()
    data object BadToken : AuthError()
}