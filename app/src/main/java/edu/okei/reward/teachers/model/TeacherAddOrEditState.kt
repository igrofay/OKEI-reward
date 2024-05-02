package edu.okei.reward.teachers.model

import edu.okei.reward.common.model.UIState

data class TeacherAddOrEditState(
    val isEdit: Boolean = false,
    val fio: String = "",
) : UIState()