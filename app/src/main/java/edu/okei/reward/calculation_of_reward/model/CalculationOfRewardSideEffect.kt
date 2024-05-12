package edu.okei.reward.calculation_of_reward.model

import edu.okei.reward.common.model.UISideEffect

sealed class CalculationOfRewardSideEffect : UISideEffect() {
    data class ShowMessage(val message: Int) : CalculationOfRewardSideEffect()
    data object OpenReportData : CalculationOfRewardSideEffect()
}