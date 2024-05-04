package edu.okei.reward.criteria.view_model

import androidx.lifecycle.viewModelScope
import edu.okei.core.domain.model.criteria.CriterionModel
import edu.okei.core.domain.use_case.CriteriaIterator
import edu.okei.reward.common.view_model.AppVM
import edu.okei.reward.criteria.model.AddOrEditCriteriaEvent
import edu.okei.reward.criteria.model.AddOrEditCriteriaSideEffect
import edu.okei.reward.criteria.model.AddOrEditCriteriaState
import edu.okei.reward.criteria.model.CriterionBuilder
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

class AddOrEditCriteriaVM(
    override val di: DI
) : AppVM<AddOrEditCriteriaState, AddOrEditCriteriaSideEffect, AddOrEditCriteriaEvent>(),
    DIAware
{
    private val criteriaIterator by di.instance<CriteriaIterator>()
    private lateinit var builder: CriterionBuilder
    override val container: Container<AddOrEditCriteriaState, AddOrEditCriteriaSideEffect> =
        viewModelScope.container(AddOrEditCriteriaState.Load){
            loadListCriterion()
        }
    override fun onEvent(event: AddOrEditCriteriaEvent) {
        when(event){
            is AddOrEditCriteriaEvent.InputName -> inputName(event.name)
        }
    }
    override fun onError(er: Throwable) {

    }

    private fun loadListCriterion() = intent {
        criteriaIterator.getCriteria()
            .onFailure(::onError)
            .onSuccess {list->
                builder = CriterionBuilder(list)
                reduce {
                    AddOrEditCriteriaState.AddOrEditCriteria(
                        examplesOfDescriptions = builder.suggestDescriptions(),
                        examplesOfSerialNumber = builder.suggestFirstSerialNumber(),
                    )
                }
            }
    }

    private fun inputName(name: String) = blockingIntent{
        builder.setName(name)
        reduce {
            (state as AddOrEditCriteriaState.AddOrEditCriteria).copy(
                nameCriterion = name
            )
        }
    }
}