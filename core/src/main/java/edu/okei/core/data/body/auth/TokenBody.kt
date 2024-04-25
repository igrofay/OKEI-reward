package edu.okei.core.data.body.auth

import edu.okei.core.domain.model.auth.TokenModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TokenBody(
    @SerialName("value")
    override val token: String,
) : TokenModel{
    companion object{
        fun TokenModel.toTokenBody() = TokenBody(token)
    }
}