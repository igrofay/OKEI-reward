package edu.okei.reward.criteria.model

import edu.okei.reward.common.model.UIEvent

sealed class AddOrEditCriteriaEvent : UIEvent() {
    data class InputName(val name: String) : AddOrEditCriteriaEvent()
    data class InputDescription(val description: String) : AddOrEditCriteriaEvent()
    data class InputSerialNumber(val serialNumber: String) : AddOrEditCriteriaEvent()
    data object AddEvaluationOption : AddOrEditCriteriaEvent()
    data class InputDescriptionEvaluationOption(val index: Int, val description: String) :
        AddOrEditCriteriaEvent()

    data class IncrementCountPointersInEvaluationOption(val index: Int) :
        AddOrEditCriteriaEvent()
    data class DecrementCountPointersInEvaluationOption(val index: Int) :
        AddOrEditCriteriaEvent()
    data class DeleteEvaluationOption(val index: Int) : AddOrEditCriteriaEvent()
    data object AddOrEditCriterion : AddOrEditCriteriaEvent()
}