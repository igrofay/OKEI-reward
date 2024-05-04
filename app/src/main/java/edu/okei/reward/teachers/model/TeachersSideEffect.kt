package edu.okei.reward.teachers.model

import edu.okei.reward.common.model.UISideEffect

sealed class TeachersSideEffect : UISideEffect() {
    data object OpenAddTeacher : TeachersSideEffect()
}