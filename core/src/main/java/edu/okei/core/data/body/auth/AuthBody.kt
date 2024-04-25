package edu.okei.core.data.body.auth

import edu.okei.core.domain.model.auth.AuthModel
import kotlinx.serialization.Serializable

@Serializable
internal data class AuthBody(
    override val login: String,
    override val password: String
) : AuthModel{
    companion object{
        fun AuthModel.toAuthBody() = AuthBody(login, password)
    }
}