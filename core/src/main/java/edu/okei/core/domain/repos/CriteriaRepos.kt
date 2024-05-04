package edu.okei.core.domain.repos

import edu.okei.core.domain.model.criteria.CriterionModel

interface CriteriaRepos {
    suspend fun getListCriterion() : Result<List<CriterionModel>>
}