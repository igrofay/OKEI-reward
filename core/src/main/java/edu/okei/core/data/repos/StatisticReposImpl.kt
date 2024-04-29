package edu.okei.core.data.repos

import edu.okei.core.data.body.statistics.MonthlyProgressBody
import edu.okei.core.data.data_source.network.StatsApi
import edu.okei.core.domain.model.errors.AppErrors
import edu.okei.core.domain.model.statistics.MonthlyProgressModel
import edu.okei.core.domain.repos.StatisticRepos
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

internal class StatisticReposImpl(
    private val statsApi: StatsApi,
) : StatisticRepos {
    override suspend fun getMonthlyProgress(): Result<MonthlyProgressModel> {
        val answer = statsApi.getStatisticsForCurrentAndPreviousMonth()
        return when(answer.status){
            HttpStatusCode.OK -> Result.success(answer.body<MonthlyProgressBody>())
            else -> Result.failure(AppErrors.UnhandledError(answer.status.toString()))
        }
    }

}