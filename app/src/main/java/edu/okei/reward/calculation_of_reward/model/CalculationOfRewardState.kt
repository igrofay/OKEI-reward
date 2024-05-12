package edu.okei.reward.calculation_of_reward.model

import edu.okei.core.domain.model.statistics.CreateReportDataModel
import edu.okei.core.domain.model.statistics.MonthlyProgressModel
import edu.okei.core.domain.model.statistics.TeachersRewardReportModel
import edu.okei.reward.common.model.UIState

sealed class CalculationOfRewardState : UIState(){
    data object Load : CalculationOfRewardState()
    data class ListMonthsOfReward(
        val monthlyProgress: MonthlyProgressModel
    ) : CalculationOfRewardState()
    data class RewardReportForMonth(
        val report: TeachersRewardReportModel,
        val monthIndex: Int,
    ) : CalculationOfRewardState(){
        val fileName
            get() = report.urlReport.split('/').last()
    }
    data class CreateReportForMonth(
        val monthIndex: Int,
        val countTeacher: Int,
        override val totalAmountPremium: Int = 0,
        override val fixedPremium: Int = 0,
        val fixedPremiumForTeachers: Int = 0,
        override val totalAmountPoints: Int = 0,
        override val partSemiannualPremium: Int = 0,
        override val distributablePremium: Int = 0,
        override val costByPoint: Int = 0,
    ) : CalculationOfRewardState(), CreateReportDataModel
}