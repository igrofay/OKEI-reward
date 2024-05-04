package edu.okei.reward.criteria.model

import edu.okei.reward.common.model.UIState

sealed class AddOrEditCriteriaState : UIState(){
    data object Load : AddOrEditCriteriaState()
    data class AddOrEditCriteria(
        val examplesOfDescriptions: Set<String>,
        val examplesOfSerialNumber: Set<Int>,
        val nameCriterion : String = "",
    ) : AddOrEditCriteriaState()
}