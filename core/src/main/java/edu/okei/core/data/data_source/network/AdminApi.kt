package edu.okei.core.data.data_source.network

import edu.okei.core.data.body.admin.NewUserBody
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal class AdminApi(
    private val authClient: HttpClient,
) {
    suspend fun addUser(newUser: NewUserBody) = authClient
        .post("/api/signup"){
            setBody(newUser)
            contentType(ContentType.Application.Json)
        }
    suspend fun deleteUser(id: String) = authClient
        .delete("/api/user/$id")
}