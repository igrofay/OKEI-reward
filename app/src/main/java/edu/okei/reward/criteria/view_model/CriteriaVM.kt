package edu.okei.reward.criteria.view_model

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import edu.okei.core.domain.use_case.CriteriaIterator
import edu.okei.reward.common.view_model.AppVM
import edu.okei.reward.criteria.model.CriteriaEvent
import edu.okei.reward.criteria.model.CriteriaSideEffect
import edu.okei.reward.criteria.model.CriteriaState
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class CriteriaVM(
    override val di: DI,
    savedStateHandle: SavedStateHandle,
) :
    AppVM<CriteriaState, CriteriaSideEffect, CriteriaEvent>(),
    DIAware
{
    private val monthIndex = savedStateHandle.get<Int>("montIndex")
    private val teacherId = savedStateHandle.get<String>("teacherId")
    private val isEdit = monthIndex == null && teacherId == null

    private val criteriaIterator by di.instance<CriteriaIterator>()
    override val container: Container<CriteriaState, CriteriaSideEffect> = viewModelScope
        .container(CriteriaState.Load){
            loadCriteria()
        }
    private fun loadCriteria() = intent {
        if (isEdit)
            criteriaIterator.getCriteria()
                .onFailure(::onError)
                .onSuccess { list->
                    reduce { CriteriaState.CriteriaManagement(list) }
                }
    }
    override fun onEvent(event: CriteriaEvent) {
        when(event){
            CriteriaEvent.AddCriterion -> blockingIntent {
                postSideEffect(CriteriaSideEffect.OpenAddCriterion)
            }
            is CriteriaEvent.InputSearch -> {}
        }
    }

    override fun onError(er: Throwable) {
        Log.e(nameVM, er.message, er)
    }
}