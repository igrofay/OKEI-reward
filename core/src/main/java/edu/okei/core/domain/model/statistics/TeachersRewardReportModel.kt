package edu.okei.core.domain.model.statistics

interface TeachersRewardReportModel {
    val urlReport: String
    val reportData: ReportDataModel
    val listTeacherReward:List<TeacherRewardModel>

    interface ReportDataModel{
        val totalAmountPremium: Int // Общая сумма премий
        val fixedPremium: Int // Фиксированная премия
        val totalAmountPoints: Int // Общее кол-во баллов
        val partSemiannualPremium: Int //часть полугодовой премии
        val distributablePremium: Int // распределяемая премия
        val costByPoint: Int // Стоимость балла
    }
    interface TeacherRewardModel{
        val fullname: String
        val countPoints: Int
        val reward: Int
    }
}