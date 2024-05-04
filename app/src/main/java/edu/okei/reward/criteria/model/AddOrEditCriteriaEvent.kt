package edu.okei.reward.criteria.model

import edu.okei.reward.common.model.UIEvent

sealed class AddOrEditCriteriaEvent : UIEvent() {
    data class InputName(val name: String) : AddOrEditCriteriaEvent()
}