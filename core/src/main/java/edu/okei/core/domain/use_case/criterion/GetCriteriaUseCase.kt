package edu.okei.core.domain.use_case.criterion

import edu.okei.core.domain.model.criteria.CriterionModel
import edu.okei.core.domain.repos.CriteriaRepos

class GetCriteriaUseCase(
    private val criteriaRepos: CriteriaRepos
){
    suspend fun execute(searchText: String = ""): Result<List<CriterionModel>> =
        criteriaRepos.getListCriterion().map {list->
            filterCriteriaByText(list, searchText).sortedBy { it.serialNumber }
        }
    private fun filterCriteriaByText(
        criteriaList: List<CriterionModel>,
        searchText: String
    ): List<CriterionModel> {
        val trimSearchText = searchText.trim()
        return if (trimSearchText.isNotBlank()) {
            criteriaList.filter { model ->
                model.name.contains(trimSearchText, ignoreCase = true) ||
                        model.description.contains(trimSearchText, ignoreCase = true)
            }
        } else {
            criteriaList
        }
    }
}