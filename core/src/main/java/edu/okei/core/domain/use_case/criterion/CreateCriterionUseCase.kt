package edu.okei.core.domain.use_case.criterion

import edu.okei.core.domain.model.criteria.NewCriterionModel
import edu.okei.core.domain.model.errors.AddOrEditError
import edu.okei.core.domain.repos.CriteriaRepos

class CreateCriterionUseCase(
    private val criteriaRepos: CriteriaRepos
)  {
    suspend fun execute(model: NewCriterionModel): Result<Boolean> = runCatching {
        if(model.name.isBlank())
            throw AddOrEditError.DataFilledInIncorrectly
        if (model.description.isBlank())
            throw AddOrEditError.DataFilledInIncorrectly
        if (model.serialNumber.isBlank())
            throw AddOrEditError.DataFilledInIncorrectly
        for(item in model.evaluationOptions){
            if (item.description.isBlank()) throw AddOrEditError.DataFilledInIncorrectly
        }
        criteriaRepos
            .createCriterion(model)
            .getOrThrow()
    }
}