package edu.okei.core.data.body.criteria

import edu.okei.core.domain.model.criteria.NewCriterionModel
import kotlinx.serialization.Serializable

@Serializable
internal data class NewCriterionBody(
    override val name: String,
    override val description: String,
    override val serialNumber: String,
    override val evaluationOptions: List<EvaluationOptionBody>
) : NewCriterionModel{
    @Serializable
    data class EvaluationOptionBody(
        override val description: String,
        override val countPoints: Int
    ) : NewCriterionModel.EvaluationOptionModel
    companion object{
        fun NewCriterionModel.toNewCriterionBody() = NewCriterionBody(
            name = name,
            description = description,
            serialNumber = serialNumber,
            evaluationOptions = evaluationOptions.map {
                EvaluationOptionBody(it.description, it.countPoints)
            }
        )
    }
}