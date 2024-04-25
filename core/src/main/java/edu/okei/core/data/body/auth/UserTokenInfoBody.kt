package edu.okei.core.data.body.auth

import edu.okei.core.domain.model.auth.UserTokenInfoModel
import kotlinx.serialization.Serializable

@Serializable
internal data class UserTokenInfoBody(
    override val tokenPair: TokenPairBody,
    override val role: Int
): UserTokenInfoModel {
    @Serializable
    data class TokenPairBody(
        override val accessToken: String,
        override val refreshToken: String
    ) : UserTokenInfoModel.TokenPairModel
}