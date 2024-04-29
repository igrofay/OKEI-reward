package edu.okei.core.data.body.statistics

import edu.okei.core.domain.model.statistics.MonthlyProgressModel
import kotlinx.serialization.Serializable

@Serializable
internal data class MonthlyProgressBody(
    override val currentMonth: MonthReportBody,
    override val previousMonth: MonthReportBody
) : MonthlyProgressModel{
    @Serializable
    data class MonthReportBody(
        override val name: String,
        override val underWay: Boolean,
        override val lastChange: String?,
        override val progress: Float,
        override val ratingTeachers: List<String>
    ) : MonthlyProgressModel.MonthReportModel
}