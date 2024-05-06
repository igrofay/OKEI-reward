package edu.okei.reward.calendar_plan.model

import edu.okei.reward.common.model.UIEvent

sealed class CalendarPlanEvent : UIEvent() {
    data object SeeCriteria : CalendarPlanEvent()
    data object SeeTeacher : CalendarPlanEvent()
    data class SeeTeachersInMonth(val mothIndex: Int) : CalendarPlanEvent()
}