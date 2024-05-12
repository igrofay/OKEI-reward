package edu.okei.core.data.repos

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import edu.okei.core.data.body.statistics.CreateReportDataBody.Companion.toCreateReportDataBody
import edu.okei.core.data.body.statistics.MonthlyProgressBody
import edu.okei.core.data.body.statistics.TeachersRewardReportBody
import edu.okei.core.data.data_source.network.AdminApi
import edu.okei.core.data.data_source.network.StatsApi
import edu.okei.core.domain.model.errors.AppErrors
import edu.okei.core.domain.model.errors.ReportError
import edu.okei.core.domain.model.statistics.CreateReportDataModel
import edu.okei.core.domain.model.statistics.MonthlyProgressModel
import edu.okei.core.domain.model.statistics.TeachersRewardReportModel
import edu.okei.core.domain.repos.StatisticRepos
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

internal class StatisticReposImpl(
    private val statsApi: StatsApi,
    private val adminApi: AdminApi,
    private val contentResolver: ContentResolver,
) : StatisticRepos {
    override suspend fun getMonthlyProgress(): Result<MonthlyProgressModel> = runCatching {
        val answer = statsApi.getStatisticsForCurrentAndPreviousMonth()
        when (answer.status) {
            HttpStatusCode.OK -> answer.body<MonthlyProgressBody>()
            else -> throw AppErrors.UnhandledError(answer.status.toString())
        }
    }

    override suspend fun createReportForMonth(
        monthIndex: Int,
        model: CreateReportDataModel
    ): Result<TeachersRewardReportModel> = runCatching{
        val answer = adminApi.createReportForMonth(monthIndex, model.toCreateReportDataBody())
        when(answer.status){
            HttpStatusCode.OK-> answer.body<TeachersRewardReportBody>()
            else -> throw AppErrors.UnhandledError(answer.status.toString())
        }
    }

    override suspend fun getReportForMonth(monthIndex: Int): Result<TeachersRewardReportModel> = runCatching{
        val answer = adminApi.getReportForMonth(monthIndex)
        when(answer.status){
            HttpStatusCode.OK-> answer.body<TeachersRewardReportBody>()
            HttpStatusCode.NotFound -> throw ReportError.ReportNotFound(monthIndex)
            else -> throw AppErrors.UnhandledError(answer.status.toString())
        }
    }

    override suspend fun downloadFile(uri: Uri, url: String): Result<Boolean> = runCatching{
        val answer = adminApi.downloadFile(url)
        when(answer.status){
            HttpStatusCode.OK-> contentResolver.openOutputStream(uri)?.use{ stream ->
                stream.write(answer.body<ByteArray>())
                true
            } ?: false
            else -> throw AppErrors.UnhandledError(answer.status.toString())
        }
    }
}