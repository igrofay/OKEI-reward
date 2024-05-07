package edu.okei.core.data.data_source.network

import edu.okei.core.data.body.teacher.CreateTeacherEvaluationBody
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal class TeacherApi(
    private val authClient: HttpClient,
){
    suspend fun getTeachers() = authClient
        .get("/api/teachers")
    suspend fun getListTeacherRating(monthIndex: Int) = authClient
        .get("/api/rating/teachers?monthIndex=${monthIndex}")
    suspend fun getTeacherMonthEvaluations(teacherId: String, monthIndex: Int) = authClient
        .get("/api/marks/${teacherId}?monthIndex=${monthIndex}")
    suspend fun evaluateTeacher(body: CreateTeacherEvaluationBody) = authClient
        .post("/api/mark"){
            setBody(body)
            contentType(ContentType.Application.Json)
        }
}