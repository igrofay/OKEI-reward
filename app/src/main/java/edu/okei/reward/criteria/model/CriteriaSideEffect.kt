package edu.okei.reward.criteria.model

import edu.okei.reward.common.model.UISideEffect

sealed class CriteriaSideEffect : UISideEffect() {
    data object OpenAddCriterion : CriteriaSideEffect()
    data class OpenFullCriteriaInformation(val position: Int) : CriteriaSideEffect()
}