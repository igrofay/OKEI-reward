package edu.okei.core.data.data_source.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get

internal class StatsApi(
    private val authClient: HttpClient,
) {
    suspend fun getStatisticsForCurrentAndPreviousMonth() =
        authClient.get("/api/stats")
}