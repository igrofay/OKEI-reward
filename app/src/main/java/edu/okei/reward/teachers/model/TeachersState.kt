package edu.okei.reward.teachers.model

import edu.okei.core.domain.model.teacher.TeacherModel
import edu.okei.core.domain.model.teacher.TeacherRatingModel
import edu.okei.reward.common.model.UIState

sealed class TeachersState : UIState() {
    data object Load : TeachersState()
    data class TeacherManagement(
        val listTeacher: List<TeacherModel>,
        val searchText: String = "",
    ) : TeachersState()
    data class TeacherRating(
        val listTeacher: List<TeacherRatingModel>,
        val searchText: String = "",
    ) : TeachersState()
}