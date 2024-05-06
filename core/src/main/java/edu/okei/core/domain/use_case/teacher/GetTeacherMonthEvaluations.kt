package edu.okei.core.domain.use_case.teacher

import edu.okei.core.domain.repos.ConductingAnEvaluationRepos

class GetTeacherMonthEvaluations(
    private val conductingAnEvaluationRepos: ConductingAnEvaluationRepos
) {
    suspend fun execute(teacherId: String, monthIndex: Int) = conductingAnEvaluationRepos
        .getTeacherMonthEvaluations(teacherId, monthIndex).map {list->
            list.associateBy { it.criterionId }
        }
}