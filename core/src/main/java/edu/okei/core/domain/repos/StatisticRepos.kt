package edu.okei.core.domain.repos

import android.net.Uri
import edu.okei.core.domain.model.statistics.CreateReportDataModel
import edu.okei.core.domain.model.statistics.MonthlyProgressModel
import edu.okei.core.domain.model.statistics.TeachersRewardReportModel
import kotlinx.coroutines.flow.Flow

interface StatisticRepos {
    suspend fun getMonthlyProgress() : Result<MonthlyProgressModel>
    suspend fun createReportForMonth(monthIndex: Int, model: CreateReportDataModel) : Result<TeachersRewardReportModel>
    suspend fun getReportForMonth(monthIndex: Int) : Result<TeachersRewardReportModel>
    suspend fun downloadFile(uri: Uri, url:String) : Result<Boolean>
}