package edu.okei.reward.teachers.model

import edu.okei.reward.common.model.UISideEffect

sealed class TeachersSideEffect : UISideEffect() {
    data object OpenAddTeacher : TeachersSideEffect()
    data class OpenTeacherCriteria(
        val monthIndex: Int,
        val teacherId: String
    ) : TeachersSideEffect()
}