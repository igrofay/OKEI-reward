package edu.okei.core.domain.model.auth

interface UserTokenInfoModel {
    val tokenPair: TokenPairModel
    val role: Int
    interface TokenPairModel{
        val accessToken: String
        val refreshToken: String
    }
}