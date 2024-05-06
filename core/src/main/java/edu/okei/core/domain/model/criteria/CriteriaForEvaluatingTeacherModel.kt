package edu.okei.core.domain.model.criteria

import edu.okei.core.domain.model.teacher.TeacherEvaluationModel

interface CriteriaForEvaluatingTeacherModel {
    val teacherName: String
    val listCriterion: List<CriterionModel>
    val alreadyPostedTeacherEvaluations: Map<String, TeacherEvaluationModel>
}