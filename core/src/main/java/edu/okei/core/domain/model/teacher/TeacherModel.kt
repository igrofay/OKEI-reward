package edu.okei.core.domain.model.teacher

import edu.okei.core.domain.model.user.UserRole

interface TeacherModel {
    val fullname: String
    val login: String
    val role: UserRole
}