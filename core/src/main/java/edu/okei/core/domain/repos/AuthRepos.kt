package edu.okei.core.domain.repos

import edu.okei.core.domain.model.auth.AuthModel
import edu.okei.core.domain.model.auth.UserTokenInfoModel

interface AuthRepos {
    suspend fun authorization(model: AuthModel) : Result<UserTokenInfoModel>
    suspend fun updateUserInfo(token: String) : Result<UserTokenInfoModel>
}