package edu.okei.reward.calendar_plan.model

import edu.okei.reward.common.model.UISideEffect

sealed class CalendarPlanSideEffect : UISideEffect() {
    data object OpenCriteria : CalendarPlanSideEffect()
    data object OpenTeachers : CalendarPlanSideEffect()
    data class OpenTeachersInMonth(val monthIndex: Int) : CalendarPlanSideEffect()
    data object OpenRewards : CalendarPlanSideEffect()
}