package edu.okei.core.domain.repos

import edu.okei.core.domain.model.teacher.TeacherEvaluationModel
import edu.okei.core.domain.model.teacher.TeacherRatingModel

interface ConductingAnEvaluationRepos {
    suspend fun getListTeacherRating(monthIndex: Int) : Result<List<TeacherRatingModel>>
    suspend fun getTeacherMonthEvaluations(teacherId: String, monthIndex: Int) : Result<List<TeacherEvaluationModel>>
}