package edu.okei.core.data.body.teacher

import edu.okei.core.domain.model.teacher.TeacherModel
import edu.okei.core.domain.model.user.UserRole
import kotlinx.serialization.Serializable

@Serializable
internal data class TeacherBody(
    override val fullname: String,
    override val login: String,
    override val role: UserRole
) : TeacherModel