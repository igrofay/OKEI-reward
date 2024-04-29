package edu.okei.core.domain.model.statistics

interface MonthlyProgressModel {
    val currentMonth: MonthReportModel
    val previousMonth: MonthReportModel
    interface MonthReportModel{
        val name: String
        val underWay: Boolean
        val lastChange: String?
        val progress: Float
        val ratingTeachers: List<String>
    }
}