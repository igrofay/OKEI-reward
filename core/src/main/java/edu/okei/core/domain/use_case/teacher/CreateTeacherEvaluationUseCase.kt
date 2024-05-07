package edu.okei.core.domain.use_case.teacher

import edu.okei.core.domain.repos.ConductingAnEvaluationRepos

class CreateTeacherEvaluationUseCase(
    private val conductingAnEvaluationRepos: ConductingAnEvaluationRepos
){
    suspend fun execute(teacherId: String, evaluationId:String) = conductingAnEvaluationRepos
        .evalTeacher(teacherId, evaluationId)
}