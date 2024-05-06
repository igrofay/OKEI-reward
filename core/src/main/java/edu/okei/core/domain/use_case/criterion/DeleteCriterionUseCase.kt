package edu.okei.core.domain.use_case.criterion

import edu.okei.core.domain.model.criteria.CriterionModel
import edu.okei.core.domain.repos.CriteriaRepos

class DeleteCriterionUseCase(
    private val criteriaRepos: CriteriaRepos,
    private val getCriteriaUseCase: GetCriteriaUseCase,
) {

    suspend fun execute(criterionId: String, searchText: String) : Result<List<CriterionModel>> =
        criteriaRepos.deleteCriterion(criterionId).mapCatching {
            getCriteriaUseCase.execute(searchText).getOrThrow()
        }
}