package edu.okei.reward.criteria.model

import edu.okei.reward.common.model.UIEvent

sealed class CriteriaEvent : UIEvent() {
    data class InputSearch(val search: String) : CriteriaEvent()
    data object AddCriterion : CriteriaEvent()
}