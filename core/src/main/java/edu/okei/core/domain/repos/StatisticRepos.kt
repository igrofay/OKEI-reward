package edu.okei.core.domain.repos

import edu.okei.core.domain.model.statistics.MonthlyProgressModel

interface StatisticRepos {
    suspend fun getMonthlyProgress() : Result<MonthlyProgressModel>
}