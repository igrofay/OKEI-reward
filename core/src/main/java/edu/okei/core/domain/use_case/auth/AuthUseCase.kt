package edu.okei.core.domain.use_case.auth

import edu.okei.core.domain.model.auth.AuthModel
import edu.okei.core.domain.model.user.UserRole
import edu.okei.core.domain.repos.AuthRepos
import edu.okei.core.domain.repos.UserRepos

class AuthUseCase(
    private val userRepos: UserRepos,
    private val authRepos: AuthRepos,
) {
    suspend fun execute(authModel: AuthModel) : Result<UserRole>{
        val result = authRepos.authorization(authModel)
        return result.map { userTokenInfo->
            userRepos.updateUserTokenInfo(userTokenInfo)
            userTokenInfo.role
        }
    }
}