package edu.okei.core.domain.model.auth

import edu.okei.core.domain.model.user.UserRole

interface UserTokenInfoModel {
    val tokenPair: TokenPairModel
    val role: UserRole
    interface TokenPairModel{
        val accessToken: String
        val refreshToken: String
    }
}