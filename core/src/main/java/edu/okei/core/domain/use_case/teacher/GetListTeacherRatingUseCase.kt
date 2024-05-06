package edu.okei.core.domain.use_case.teacher

import edu.okei.core.domain.repos.ConductingAnEvaluationRepos

class GetListTeacherRatingUseCase(
    private val conductingAnEvaluationRepos: ConductingAnEvaluationRepos,
) {
    suspend fun execute(monthIndex: Int, searchText: String = "") =
        conductingAnEvaluationRepos.getListTeacherRating(monthIndex).map {list->
            val trimSearchText = searchText.trim()
            if (trimSearchText.isNotBlank()) list.filter { model->
                model.fullname.contains(trimSearchText, ignoreCase = true)
            }
            else list
        }
}