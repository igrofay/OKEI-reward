package edu.okei.core.data.data_source.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get

internal class CriteriaApi(
    private val authClient: HttpClient,
){
    suspend fun getListCriterion(count: Int, offset: Int) = authClient
        .get("/api/criterions?count=${count}&offset=${offset}")
}