package edu.okei.reward.criteria.model

import edu.okei.core.domain.model.criteria.CriterionModel
import edu.okei.reward.common.model.UIState

sealed class CriteriaState : UIState() {
    data object Load : CriteriaState()
    data class CriteriaManagement(
        val listCriterion: List<CriterionModel>,
        val searchText: String = "",
    ) : CriteriaState()
}