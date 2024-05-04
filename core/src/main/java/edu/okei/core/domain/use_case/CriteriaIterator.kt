package edu.okei.core.domain.use_case

import edu.okei.core.domain.model.criteria.CriterionModel
import edu.okei.core.domain.repos.CriteriaRepos

class CriteriaIterator(
    private val criteriaRepos: CriteriaRepos
) {
    suspend fun getCriteria(searchText: String = ""): Result<List<CriterionModel>> =
        criteriaRepos.getListCriterion().map {list->
            val trimSearchText = searchText.trim()
            val listSearch = if (trimSearchText.isNotBlank())  list.filter { model ->
                model.name.contains(trimSearchText, ignoreCase = true) ||
                        model.description.contains(trimSearchText, ignoreCase = true)
            }
            else list
            listSearch.sortedBy { it.serialNumber }
        }

}