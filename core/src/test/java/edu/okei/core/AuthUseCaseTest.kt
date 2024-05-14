package edu.okei.core

import edu.okei.core.data.body.auth.UserTokenInfoBody
import edu.okei.core.domain.model.auth.AuthModel
import edu.okei.core.domain.model.auth.UserTokenInfoModel
import edu.okei.core.domain.model.user.UserRole
import edu.okei.core.domain.repos.AuthRepos
import edu.okei.core.domain.repos.UserRepos
import edu.okei.core.domain.use_case.auth.AuthUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import org.junit.Before
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */



@ExperimentalCoroutinesApi
class AuthUseCaseTest {
    private lateinit var authUseCase: AuthUseCase
    private lateinit var successfulAuthModel: AuthModel
    private lateinit var failedAuthModel: AuthModel
    private val mockUserRepos = object : UserRepos {
        override fun getUserRole(): Flow<UserRole> = flowOf(UserRole.Appraiser)
        override fun getAccessToken(): String = "mockAccessToken"
        override fun getRefreshToken(): String = "mockRefreshToken"
        override fun updateUserTokenInfo(userTokenInfo: UserTokenInfoModel) {
            // Метод заглушка для теста
        }
    }
    private val mockAuthRepos = object : AuthRepos {
        override suspend fun authorization(model: AuthModel) =
            if (model == successfulAuthModel) {
                Result.success(
                    UserTokenInfoBody(
                        UserTokenInfoBody.TokenPairBody(
                            "mockAccessToken",
                            "mockRefreshToken"
                        ),
                        UserRole.Appraiser
                    )
                )
            } else {
                Result.failure(Exception("Authorization failed"))
            }
        override suspend fun updateUserInfo(token: String) =
            Result.success(
                UserTokenInfoBody(
                    UserTokenInfoBody.TokenPairBody(
                        "mockAccessToken",
                        "mockRefreshToken"
                    ),
                    UserRole.Appraiser
                )
            )
    }

    @Before
    fun setUp() {
        // Инициализация use case и моделей перед каждым тестом
        authUseCase = AuthUseCase(mockUserRepos, mockAuthRepos)
        successfulAuthModel = object : AuthModel {
            override val login: String = "username"
            override val password: String = "password"
        }
        failedAuthModel = object : AuthModel {
            override val login: String = "invalid_username"
            override val password: String = "invalid_password"
        }
    }

    @Test
    fun `test successful execute`() = runBlocking {
        // Выполнение use case с успешным результатом
        val result = authUseCase.execute(successfulAuthModel)
        // Проверка успешного результата и роли пользователя
        assert(result.isSuccess)
        assert(result.getOrNull() == UserRole.Appraiser)
    }

    @Test
    fun `test failed execute`() = runBlocking {
        // Выполнение use case с неудачным результатом
        val result = authUseCase.execute(failedAuthModel)
        // Проверка неудачного результата
        assert(result.isFailure)
    }
}
