package edu.okei.reward.calculation_of_reward.model

import android.net.Uri
import edu.okei.reward.common.model.UIEvent

sealed class CalculationOfRewardEvent : UIEvent() {
    data class SeeReportForMonth(val monthIndex: Int) : CalculationOfRewardEvent()
    data class InputTotalAmountPremium(val totalAmountPremium: String) : CalculationOfRewardEvent()
    data class InputFixedPremium(val fixedPremium: String) : CalculationOfRewardEvent()
    data class InputPartSemiannualPremium(val partSemiannualPremium: String) : CalculationOfRewardEvent()
    data object CreateReportData : CalculationOfRewardEvent()
    data object SeeReportData : CalculationOfRewardEvent()
    data class DownloadReport(val uri: Uri) : CalculationOfRewardEvent()
}