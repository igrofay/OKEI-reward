package edu.okei.reward.criteria.model

import edu.okei.reward.common.model.UISideEffect

sealed class AddOrEditCriteriaSideEffect : UISideEffect() {
    data class ShowMessage(val message: Int) : AddOrEditCriteriaSideEffect()
    data object EditsCompleted : AddOrEditCriteriaSideEffect()
}