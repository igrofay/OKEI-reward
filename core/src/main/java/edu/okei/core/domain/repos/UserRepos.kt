package edu.okei.core.domain.repos

import edu.okei.core.domain.model.auth.UserTokenInfoModel
import edu.okei.core.domain.model.user.UserRole
import kotlinx.coroutines.flow.Flow

interface UserRepos {
    fun getUserRole(): Flow<UserRole>
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun updateUserTokenInfo(userTokenInfo: UserTokenInfoModel)
}