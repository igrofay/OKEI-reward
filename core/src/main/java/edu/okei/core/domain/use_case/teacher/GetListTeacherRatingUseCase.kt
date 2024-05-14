package edu.okei.core.domain.use_case.teacher

import edu.okei.core.domain.model.teacher.TeacherRatingModel
import edu.okei.core.domain.repos.ConductingAnEvaluationRepos

class GetListTeacherRatingUseCase(
    private val conductingAnEvaluationRepos: ConductingAnEvaluationRepos,
) {
    suspend fun execute(monthIndex: Int, searchText: String = "") =
        conductingAnEvaluationRepos.getListTeacherRating(monthIndex).map { list ->
            filterTeachersByText(list, searchText).sortedBy { it.fullname }
        }

    private fun filterTeachersByText(
        listTeacher: List<TeacherRatingModel>,
        searchText: String
    ): List<TeacherRatingModel> {
        val trimSearchText = searchText.trim()
        return if (trimSearchText.isNotBlank()) listTeacher
            .filter { model ->
                model.fullname.contains(trimSearchText, ignoreCase = true)
            }
        else
            listTeacher
    }
}