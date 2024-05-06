package edu.okei.core.data.body.teacher

import edu.okei.core.domain.model.teacher.TeacherEvaluationModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TeacherEvaluationBody(
    @SerialName("id")
    override val criterionId: String,
    override val appraiserName: String,
    override val date: String,
    override val evaluationId: String
) : TeacherEvaluationModel