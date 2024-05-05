package edu.okei.reward.criteria.model

import edu.okei.core.domain.model.criteria.NewCriterionModel

data class NewEvaluationOptionData(
    override val description: String = "",
    override val countPoints: Int = 0
) : NewCriterionModel.EvaluationOptionModel