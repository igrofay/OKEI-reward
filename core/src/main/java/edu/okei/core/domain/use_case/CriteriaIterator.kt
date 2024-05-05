package edu.okei.core.domain.use_case

import android.adservices.adid.AdId
import edu.okei.core.domain.model.criteria.CriterionModel
import edu.okei.core.domain.model.criteria.NewCriterionModel
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
    suspend fun createCriterion(model: NewCriterionModel): Result<Boolean> =
        criteriaRepos.createCriterion(model)
    suspend fun deleteCriterion(criterionId: String, searchText: String) : Result<List<CriterionModel>> =
        criteriaRepos.deleteCriterion(criterionId).mapCatching {
            getCriteria(searchText).getOrThrow()
        }
}