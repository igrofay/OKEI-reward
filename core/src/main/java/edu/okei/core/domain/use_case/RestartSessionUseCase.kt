package edu.okei.core.domain.use_case

import edu.okei.core.domain.model.user.UserRole
import edu.okei.core.domain.repos.AuthRepos
import edu.okei.core.domain.repos.UserRepos

class RestartSessionUseCase(
    private val userRepos: UserRepos,
    private val authRepos: AuthRepos,
) {
    suspend fun execute() : Result<UserRole>{
        val token = userRepos.getRefreshToken()
            ?: return Result.success(UserRole.Undefined)
        val result = authRepos.updateUserInfo(token)
        return result.map { userTokenInfo->
            userRepos.updateUserTokenInfo(userTokenInfo)
            userTokenInfo.role
        }
    }
}