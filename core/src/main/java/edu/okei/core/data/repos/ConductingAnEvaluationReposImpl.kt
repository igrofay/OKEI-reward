package edu.okei.core.data.repos

import edu.okei.core.data.body.teacher.CreateTeacherEvaluationBody
import edu.okei.core.data.body.teacher.TeacherEvaluationBody
import edu.okei.core.data.body.teacher.TeacherRatingBody
import edu.okei.core.data.data_source.network.TeacherApi
import edu.okei.core.domain.model.errors.AppErrors
import edu.okei.core.domain.model.teacher.TeacherEvaluationModel
import edu.okei.core.domain.model.teacher.TeacherRatingModel
import edu.okei.core.domain.repos.ConductingAnEvaluationRepos
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

internal class ConductingAnEvaluationReposImpl(
    private val teacherApi: TeacherApi,
) : ConductingAnEvaluationRepos {
    override suspend fun getListTeacherRating(monthIndex: Int): Result<List<TeacherRatingModel>> = runCatching{
        val answer = teacherApi.getListTeacherRating(monthIndex)
        when(answer.status){
            HttpStatusCode.OK-> answer.body<List<TeacherRatingBody>>()
            else -> throw AppErrors.UnhandledError(answer.status.toString())
        }
    }

    override suspend fun getTeacherMonthEvaluations(
        teacherId: String,
        monthIndex: Int,
    ): Result<List<TeacherEvaluationModel>> = runCatching {
        val answer = teacherApi.getTeacherMonthEvaluations(teacherId, monthIndex)
        when(answer.status){
            HttpStatusCode.OK -> answer.body<List<TeacherEvaluationBody>>()
            else -> throw AppErrors.UnhandledError(answer.status.toString())
        }
    }

    override suspend fun evalTeacher(
        teacherId: String,
        evaluationId: String
    ): Result<TeacherEvaluationModel> = runCatching{
        val answer = teacherApi.evaluateTeacher(CreateTeacherEvaluationBody(evaluationId, teacherId))
        when(answer.status){
            HttpStatusCode.OK -> answer.body<TeacherEvaluationBody>()
            else-> throw AppErrors.UnhandledError(answer.status.toString())
        }
    }

}