package edu.okei.core.domain.use_case.criterion

import edu.okei.core.domain.model.criteria.CriteriaForEvaluatingTeacherModel
import edu.okei.core.domain.model.criteria.CriterionModel
import edu.okei.core.domain.model.teacher.TeacherEvaluationModel
import edu.okei.core.domain.use_case.teacher.GetTeacherMonthEvaluations
import edu.okei.core.domain.use_case.teacher.GetTeachersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class GetCriteriaForEvaluatingTeacher(
    private val criteriaUseCase: GetCriteriaUseCase,
    private val getTeachersUseCase: GetTeachersUseCase,
    private val getTeacherMonthEvaluations: GetTeacherMonthEvaluations,
) {
    suspend fun execute(
        teacherId: String,
        monthIndex: Int
    ): Result<CriteriaForEvaluatingTeacherModel> = runCatching {
        withContext(Dispatchers.IO) {
            val teacherDeferred = async {
                getTeachersUseCase.execute(searchText = teacherId).mapCatching { it.first() }
            }
            val criteriaDeferred = async { criteriaUseCase.execute() }
            val teacherMonthEvaluationsDeferred =
                async { getTeacherMonthEvaluations.execute(teacherId, monthIndex) }
            val teacher = teacherDeferred.await().getOrThrow()
            val criteria = criteriaDeferred.await().getOrThrow()
            val teacherMonthEvaluations = teacherMonthEvaluationsDeferred.await().getOrThrow()
            object : CriteriaForEvaluatingTeacherModel {
                override val teacherName: String = teacher.fullname
                override val listCriterion: List<CriterionModel> = criteria
                override val alreadyPostedTeacherEvaluations: Map<String, TeacherEvaluationModel> =
                    teacherMonthEvaluations
            }
        }
    }
}