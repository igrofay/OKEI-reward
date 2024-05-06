package edu.okei.core.data.body.teacher

import edu.okei.core.domain.model.teacher.TeacherRatingModel
import kotlinx.serialization.Serializable

@Serializable
internal data class TeacherRatingBody(
    override val fullname: String,
    override val login: String,
    override val sumMarks: Int,
    override val lastChange: String?,
    override val isKing: Boolean
) : TeacherRatingModel