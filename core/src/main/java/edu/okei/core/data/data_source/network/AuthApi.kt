package edu.okei.core.data.data_source.network

import edu.okei.core.data.body.auth.AuthBody
import edu.okei.core.data.body.auth.TokenBody
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal class AuthApi(
    private val baseClient: HttpClient,
){
    suspend fun authorization(body: AuthBody) =
        baseClient.post("/api/signin"){
            setBody(body)
            contentType(ContentType.Application.Json)
        }
    suspend fun updateToken(body: TokenBody) =
        baseClient.post("/api/token"){
            setBody(body)
            contentType(ContentType.Application.Json)
        }
}