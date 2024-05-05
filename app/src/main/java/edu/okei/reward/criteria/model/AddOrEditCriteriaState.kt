package edu.okei.reward.criteria.model

import edu.okei.core.domain.model.criteria.NewCriterionModel
import edu.okei.reward.common.model.UIState

sealed class AddOrEditCriteriaState : UIState(){
    data object Load : AddOrEditCriteriaState()
    data class AddOrEditCriteria(
        val examplesOfDescriptions: List<String>,
        val nameCriterion : String = "",
        val description: String = "",
        val serialNumber: String = "",
        val evaluationOptions: List<NewEvaluationOptionData> = listOf()
    ) : AddOrEditCriteriaState()
}