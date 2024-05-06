package edu.okei.core.data.data_source.network

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get

internal class TeacherApi(
    private val authClient: HttpClient,
){
    suspend fun getTeachers() = authClient
        .get("/api/teachers")
    suspend fun getListTeacherRating(monthIndex: Int) = authClient
        .get("/api/rating/teachers?monthIndex=${monthIndex}")
    suspend fun getTeacherMonthEvaluations(teacherId: String, monthIndex: Int) = authClient
        .get("/api/marks/${teacherId}?monthIndex=${monthIndex}")
}