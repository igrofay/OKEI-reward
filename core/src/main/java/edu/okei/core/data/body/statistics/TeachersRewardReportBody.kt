package edu.okei.core.data.body.statistics

import edu.okei.core.domain.model.statistics.TeachersRewardReportModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TeachersRewardReportBody(
    override val urlReport: String,
    override val reportData: ReportDataBody,
    @SerialName("teacherPerformanceSummaries")
    override val listTeacherReward: List<TeacherRewardBody>
): TeachersRewardReportModel{
    @Serializable
    data class ReportDataBody(
        override val totalAmountPremium: Int,
        override val fixedPremium: Int,
        override val totalAmountPoints: Int,
        override val partSemiannualPremium: Int,
        override val distributablePremium: Int,
        override val costByPoint: Int,
    ) : TeachersRewardReportModel.ReportDataModel
    @Serializable
    data class TeacherRewardBody(
        override val fullname: String,
        override val countPoints: Int,
        @SerialName("premium")
        override val reward: Int
    ) : TeachersRewardReportModel.TeacherRewardModel
}