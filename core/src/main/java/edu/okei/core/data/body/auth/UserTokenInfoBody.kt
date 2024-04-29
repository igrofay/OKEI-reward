package edu.okei.core.data.body.auth

import edu.okei.core.domain.model.auth.UserTokenInfoModel
import edu.okei.core.domain.model.user.UserRole
import kotlinx.serialization.Serializable

@Serializable
internal data class UserTokenInfoBody(
    override val tokenPair: TokenPairBody,
    override val role: UserRole
): UserTokenInfoModel {
    @Serializable
    data class TokenPairBody(
        override val accessToken: String,
        override val refreshToken: String
    ) : UserTokenInfoModel.TokenPairModel
}