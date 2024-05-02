package edu.okei.core.data.repos

import edu.okei.core.data.body.teacher.TeacherBody
import edu.okei.core.data.data_source.network.TeacherApi
import edu.okei.core.domain.model.errors.AppErrors
import edu.okei.core.domain.model.teacher.TeacherModel
import edu.okei.core.domain.repos.TeachersRepos
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

internal class TeachersReposImpl(
    private val teacherApi: TeacherApi,
) : TeachersRepos {
    override suspend fun getAllTeacher(): Result<List<TeacherModel>> = runCatching {
        val answer = teacherApi.getTeachers()
        when (answer.status) {
            HttpStatusCode.OK -> answer.body<List<TeacherBody>>()
            else -> throw AppErrors.UnhandledError(answer.status.toString())
        }
    }

    override suspend fun deleteUser(id: String): Result<Boolean> = runCatching{
        val answer = teacherApi.deleteTeacher(id)
        when (answer.status) {
            HttpStatusCode.NoContent -> true
            else -> throw AppErrors.UnhandledError(answer.status.toString())
        }
    }
}