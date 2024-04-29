package edu.okei.reward.calendar_plan.model

import edu.okei.core.domain.model.statistics.MonthlyProgressModel
import edu.okei.reward.common.model.UIState

sealed class CalendarPlanState: UIState(){
    data object Load : CalendarPlanState()
    data class MonthProgress(
        val model: MonthlyProgressModel
    ) : CalendarPlanState()
}