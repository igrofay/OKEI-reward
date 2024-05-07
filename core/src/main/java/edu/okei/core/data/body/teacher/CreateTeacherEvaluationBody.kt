package edu.okei.core.data.body.teacher

import edu.okei.core.domain.model.teacher.CreateTeacherEvaluationModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CreateTeacherEvaluationBody(
    override val evaluationId: String,
    @SerialName("evaluationLogin")
    override val teacherId: String
) : CreateTeacherEvaluationModel