package edu.okei.core.domain.model.teacher

interface TeacherRatingModel {
    val fullname: String
    val login: String
    val sumMarks: Int
    val lastChange: String?
    val isKing: Boolean
}