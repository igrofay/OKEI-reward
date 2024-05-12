package edu.okei.core.data.repos

import edu.okei.core.data.body.admin.NewUserBody
import edu.okei.core.data.body.teacher.TeacherBody
import edu.okei.core.data.data_source.network.AdminApi
import edu.okei.core.data.data_source.network.TeacherApi
import edu.okei.core.domain.model.errors.AppErrors
import edu.okei.core.domain.model.errors.UserManagementError
import edu.okei.core.domain.model.teacher.TeacherModel
import edu.okei.core.domain.model.user.UserRole
import edu.okei.core.domain.repos.UsersManagementRepos
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

internal class UsersManagementReposImpl(
    private val teacherApi: TeacherApi,
    private val adminApi: AdminApi,
) : UsersManagementRepos {
    override suspend fun getAllUsers(): Result<List<TeacherModel>> = runCatching {
        val answer = teacherApi.getTeachers()
        when (answer.status) {
            HttpStatusCode.OK -> answer.body<List<TeacherBody>>()
            else -> throw AppErrors.UnhandledError(answer.status.toString())
        }
    }

    override suspend fun deleteUser(id: String): Result<Boolean> = runCatching{
        val answer = adminApi.deleteUser(id)
        when (answer.status) {
            HttpStatusCode.NoContent -> true
            else -> throw AppErrors.UnhandledError(answer.status.toString())
        }
    }

    override suspend fun addUser(fio: String, role: UserRole): Result<Boolean> = runCatching {
        val answer = adminApi.addUser(NewUserBody(fio, role))
        when (answer.status) {
            HttpStatusCode.OK -> true
            HttpStatusCode.BadRequest -> throw UserManagementError.InvalidNameFormat
            else -> throw AppErrors.UnhandledError(answer.status.toString())
        }
    }

}