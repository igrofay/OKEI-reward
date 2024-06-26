package edu.okei.reward.teachers.model

import edu.okei.reward.common.model.UIEvent

sealed class TeachersEvent : UIEvent() {

    data class InputSearch(val search: String) : TeachersEvent()
    // Teacher Managent
    data object AddTeacher : TeachersEvent()
    data class DeleteUser(val login: String) : TeachersEvent()
    data object UpdateListTeacher : TeachersEvent()
    // Teacher Managent
    // TeacherRating
    data class SeeTeacherCriteria(val teacherId: String) : TeachersEvent()
    // TeacherRating
}