package edu.okei.core.data.repos

import edu.okei.core.data.body.auth.AuthBody.Companion.toAuthBody
import edu.okei.core.data.body.auth.TokenBody
import edu.okei.core.data.body.auth.UserTokenInfoBody
import edu.okei.core.data.data_source.network.AuthApi
import edu.okei.core.domain.model.auth.AuthModel
import edu.okei.core.domain.model.auth.UserTokenInfoModel
import edu.okei.core.domain.model.errors.AppErrors
import edu.okei.core.domain.model.errors.AuthError
import edu.okei.core.domain.repos.AuthRepos
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

internal class AuthReposImpl(
    private val authApi: AuthApi
) : AuthRepos{
    override suspend fun authorization(model: AuthModel): Result<UserTokenInfoModel> {
        val result = authApi.authorization(model.toAuthBody())
        val error = when(result.status){
            HttpStatusCode.OK->
                return Result.success(result.body<UserTokenInfoBody>())
            HttpStatusCode.BadRequest-> AuthError.WrongPassword
            HttpStatusCode.NotFound -> AuthError.UserNotFound
            else-> AppErrors.UnhandledError(result.status.toString())
        }
        return Result.failure(error)
    }

    override suspend fun updateUserInfo(token: String): Result<UserTokenInfoModel> {
        val result = authApi.updateToken(TokenBody(token))
        val error = when(result.status){
            HttpStatusCode.OK->
                return Result.success(result.body<UserTokenInfoBody>())
            HttpStatusCode.NotFound -> AuthError.BadToken
            else-> AppErrors.UnhandledError(result.status.toString())
        }
        return Result.failure(error)
    }

}