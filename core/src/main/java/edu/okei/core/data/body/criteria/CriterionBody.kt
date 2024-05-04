package edu.okei.core.data.body.criteria

import edu.okei.core.domain.model.criteria.CriterionModel
import kotlinx.serialization.Serializable

@Serializable
internal data class CriterionBody(
    override val id: String,
    override val name: String,
    override val serialNumber: String,
    override val description: String,
    override val evaluationOptions: List<EvaluationOptionBody>
) : CriterionModel{
    @Serializable
    data class EvaluationOptionBody(
        override val id: String,
        override val description: String,
        override val countPoints: Int
    ) : CriterionModel.EvaluationOptionModel
}