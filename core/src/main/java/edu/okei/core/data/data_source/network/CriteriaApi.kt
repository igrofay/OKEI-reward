package edu.okei.core.data.data_source.network

import edu.okei.core.data.body.criteria.NewCriterionBody
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal class CriteriaApi(
    private val authClient: HttpClient,
){
    suspend fun getListCriterion(count: Int, offset: Int) = authClient
        .get("/api/criterions?count=${count}&offset=${offset}")
    suspend fun createCriterion(body: NewCriterionBody) = authClient
        .post("/api/criterion"){
            setBody(body)
            contentType(ContentType.Application.Json)
        }
    suspend fun deleteCriterion(criterionId: String) = authClient
        .delete("/api/criterion/$criterionId")
}