package edu.okei.core.data.data_source.network

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get

internal class TeacherApi(
    private val authClient: HttpClient,
){
    suspend fun getTeachers() = authClient
        .get("/api/teachers")
    suspend fun deleteTeacher(id: String) = authClient
        .delete("/api/user/$id")
}