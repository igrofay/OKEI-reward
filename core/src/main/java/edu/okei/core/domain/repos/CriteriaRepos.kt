package edu.okei.core.domain.repos

import edu.okei.core.domain.model.criteria.CriterionModel
import edu.okei.core.domain.model.criteria.NewCriterionModel

interface CriteriaRepos {
    suspend fun getListCriterion() : Result<List<CriterionModel>>
    suspend fun createCriterion(model: NewCriterionModel) : Result<Boolean>
    suspend fun deleteCriterion(criterionId: String) : Result<Boolean>
}