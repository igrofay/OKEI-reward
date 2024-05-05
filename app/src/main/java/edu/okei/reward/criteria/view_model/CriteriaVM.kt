package edu.okei.reward.criteria.view_model

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import edu.okei.core.domain.use_case.CriteriaIterator
import edu.okei.reward.common.model.UIEvent
import edu.okei.reward.common.view_model.AppVM
import edu.okei.reward.criteria.model.CriteriaEvent
import edu.okei.reward.criteria.model.CriteriaEventTransmitter
import edu.okei.reward.criteria.model.CriteriaSideEffect
import edu.okei.reward.criteria.model.CriteriaState
import edu.okei.reward.teachers.model.TeachersState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
    DIAware,
    CriteriaEventTransmitter.Observer
{
    private var searchCriteriaJob: Job? = null
    private val monthIndex = savedStateHandle.get<Int>("montIndex")
    private val teacherId = savedStateHandle.get<String>("teacherId")
    private val isEdit = monthIndex == null && teacherId == null

    private val criteriaIterator by di.instance<CriteriaIterator>()
    override val container: Container<CriteriaState, CriteriaSideEffect> = viewModelScope
        .container(CriteriaState.Load) {
            CriteriaEventTransmitter.subscribe(this@CriteriaVM)
            loadCriteria()
        }

    private fun loadCriteria() = intent {
        if (isEdit)
            criteriaIterator.getCriteria()
                .onFailure(::onError)
                .onSuccess { list ->
                    reduce { CriteriaState.CriteriaManagement(list) }
                }
    }

    override fun onEvent(event: CriteriaEvent) {
        when (event) {
            CriteriaEvent.AddCriterion -> blockingIntent {
                postSideEffect(CriteriaSideEffect.OpenAddCriterion)
            }
            is CriteriaEvent.InputSearch -> inputSearchText(event.search)
            CriteriaEvent.UpdateListCriterion -> updateListCriterion()
            is CriteriaEvent.DeleteCriterion -> deleteCriterion(event.criterionId)
        }
    }

    override fun onError(er: Throwable) {
        Log.e(nameVM, er.message, er)
    }

    override fun messageReceived(message: UIEvent) {
        if (message is CriteriaEvent.UpdateListCriterion)
            updateListCriterion()
    }

    override fun onCleared() {
        CriteriaEventTransmitter.unsubscribe(this)
        super.onCleared()
    }
    private fun updateListCriterion() = intent {
        val targetState = state as? CriteriaState.CriteriaManagement ?: return@intent
        criteriaIterator.getCriteria(targetState.searchText)
            .onFailure(::onError)
            .onSuccess { list->
                reduce {
                    targetState.copy(
                        listCriterion = list
                    )
                }
            }
    }
    // Search
    private fun inputSearchText(text: String) = blockingIntent {
        val targetState = state as? CriteriaState.CriteriaManagement ?: return@blockingIntent
        reduce {
            targetState.copy(searchText = text)
        }
        searchCriteriaJob?.cancel()
        searchCriteriaJob = searchCriterion(text)
    }
    private fun searchCriterion(text: String) = intent {
        delay(500)
        val result = criteriaIterator.getCriteria(text)
        if (result.isFailure) return@intent onError(result.exceptionOrNull()!!)
        reduce {
            (state as? CriteriaState.CriteriaManagement)?.copy(
                listCriterion = result.getOrNull()!!
            ) ?: state
        }
    }
    // Search
    private fun deleteCriterion(criterionId:String) = intent {
        val searchText = (state as? CriteriaState.CriteriaManagement)?.searchText
            ?: return@intent
        criteriaIterator.deleteCriterion(criterionId, searchText)
            .onFailure(::onError)
            .onSuccess {list->
                reduce {
                    (state as?  CriteriaState.CriteriaManagement)?.copy(
                        listCriterion = list
                    ) ?: state
                }
            }
    }
}