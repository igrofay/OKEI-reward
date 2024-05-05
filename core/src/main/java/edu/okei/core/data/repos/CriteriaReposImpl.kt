package edu.okei.core.data.repos

import edu.okei.core.data.body.criteria.CriterionBody
import edu.okei.core.data.body.criteria.NewCriterionBody.Companion.toNewCriterionBody
import edu.okei.core.data.data_source.network.CriteriaApi
import edu.okei.core.domain.model.criteria.CriterionModel
import edu.okei.core.domain.model.criteria.NewCriterionModel
import edu.okei.core.domain.model.errors.AppErrors
import edu.okei.core.domain.repos.CriteriaRepos
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

internal class CriteriaReposImpl(
    private val criteriaApi: CriteriaApi,
) : CriteriaRepos {
    override suspend fun getListCriterion(): Result<List<CriterionModel>> = runCatching {
        val answer = criteriaApi.getListCriterion(Int.MAX_VALUE, 0)
        when(answer.status){
            HttpStatusCode.OK -> answer.body<List<CriterionBody>>()
            else -> throw AppErrors.UnhandledError(answer.status.toString())
        }
    }

    override suspend fun createCriterion(model: NewCriterionModel): Result<Boolean> = runCatching {
        val answer =  criteriaApi.createCriterion(model.toNewCriterionBody())
        when(answer.status){
            HttpStatusCode.OK -> true
            else -> throw AppErrors.UnhandledError(answer.status.toString())
        }
    }

    override suspend fun deleteCriterion(criterionId: String): Result<Boolean> = runCatching{
        val answer = criteriaApi.deleteCriterion(criterionId)
        when(answer.status){
            HttpStatusCode.NoContent -> true
            else -> throw AppErrors.UnhandledError(answer.status.toString())
        }
    }

}