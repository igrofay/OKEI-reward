package edu.okei.reward.teachers.model

import edu.okei.reward.common.model.UIEvent

sealed class AddOrEditTeacherEvent : UIEvent(){
    data class InputFIO(val fio: String): AddOrEditTeacherEvent()
    data object AddOrEditTeacher : AddOrEditTeacherEvent()
}