package edu.okei.core.data.body.statistics

import edu.okei.core.domain.model.statistics.CreateReportDataModel
import kotlinx.serialization.Serializable

@Serializable
internal data class CreateReportDataBody(
    override val totalAmountPremium: Int,
    override val fixedPremium: Int,
    override val totalAmountPoints: Int,
    override val partSemiannualPremium: Int,
    override val distributablePremium: Int,
    override val costByPoint: Int
) : CreateReportDataModel {
    companion object {
        fun CreateReportDataModel.toCreateReportDataBody() = CreateReportDataBody(
            totalAmountPremium,
            fixedPremium,
            totalAmountPoints,
            partSemiannualPremium,
            distributablePremium,
            costByPoint
        )
    }
}