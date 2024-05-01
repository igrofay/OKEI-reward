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
) : TeachersRepos{
    override suspend fun getAllTeacher(): Result<List<TeacherModel>> {
        val answer = teacherApi.getTeachers()
        return when(answer.status){
            HttpStatusCode.OK -> Result.success(
                answer.body<List<TeacherBody>>()
            )
            else -> Result.failure(AppErrors.UnhandledError(answer.status.toString()))
        }
    }
}