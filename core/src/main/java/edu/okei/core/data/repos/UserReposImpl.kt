package edu.okei.core.data.repos

import android.content.SharedPreferences
import androidx.core.content.edit
import edu.okei.core.domain.model.auth.UserTokenInfoModel
import edu.okei.core.domain.model.user.UserRole
import edu.okei.core.domain.repos.UserRepos
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class UserReposImpl(
    private val preferences: SharedPreferences
) : UserRepos {
    private val KEY_USER_ROLE = "KEY_USER_ROLE"
    private val KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN"
    private val KEY_REFRESH_TOKEN = "KEY_REFRESH_TOKEN"
    private val stateUserRole = MutableStateFlow(getUserRoleFromDB())
    private fun getUserRoleFromDB() = UserRole.getRole(
        preferences.getInt(KEY_USER_ROLE, UserRole.Undefined.id)
    )

    override fun getUserRole(): Flow<UserRole> =
        stateUserRole.asStateFlow()

    override fun getAccessToken(): String? =
        preferences.getString(KEY_ACCESS_TOKEN, null)

    override fun getRefreshToken(): String? =
        preferences.getString(KEY_REFRESH_TOKEN, null)

    override fun updateUserTokenInfo(userTokenInfo: UserTokenInfoModel) {
        preferences.edit {
            putInt(KEY_USER_ROLE, userTokenInfo.role.id)
            putString(KEY_ACCESS_TOKEN, userTokenInfo.tokenPair.accessToken)
            putString(KEY_REFRESH_TOKEN, userTokenInfo.tokenPair.refreshToken)
        }
        stateUserRole.value = userTokenInfo.role
    }
}