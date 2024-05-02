package edu.okei.reward.teachers.model

import edu.okei.reward.common.model.UIEvent

sealed class TeacherAddOrEditEvent : UIEvent(){
    data class InputFIO(val fio: String): TeacherAddOrEditEvent()
    data object AddOrEdit : TeacherAddOrEditEvent()
}