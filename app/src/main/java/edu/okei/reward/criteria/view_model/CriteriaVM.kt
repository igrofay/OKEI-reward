package edu.okei.reward.criteria.view_model

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import edu.okei.core.domain.model.teacher.TeacherEvaluationModel
import edu.okei.core.domain.use_case.criterion.DeleteCriterionUseCase
import edu.okei.core.domain.use_case.criterion.GetCriteriaForEvaluatingTeacher
import edu.okei.core.domain.use_case.criterion.GetCriteriaUseCase
import edu.okei.core.domain.use_case.teacher.CreateTeacherEvaluationUseCase
import edu.okei.core.domain.use_case.teacher.GetTeacherMonthEvaluations
import edu.okei.reward.common.model.UIEvent
import edu.okei.reward.common.view_model.AppVM
import edu.okei.reward.criteria.model.CriteriaEvent
import edu.okei.reward.criteria.model.CriteriaEventTransmitter
import edu.okei.reward.criteria.model.CriteriaSideEffect
import edu.okei.reward.criteria.model.CriteriaState
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
import java.text.FieldPosition
import java.util.Calendar

class CriteriaVM(
    override val di: DI,
    savedStateHandle: SavedStateHandle,
) :
    AppVM<CriteriaState, CriteriaSideEffect, CriteriaEvent>(),
    DIAware,
    CriteriaEventTransmitter.Observer {
    private var searchCriteriaJob: Job? = null
    private val monthIndex = savedStateHandle.get<String>("monthIndex")?.toInt()
    private val teacherId = savedStateHandle.get<String>("teacherId")
    private val isEdit = monthIndex == null || teacherId == null
    private val deleteCriterionUseCase by di.instance<DeleteCriterionUseCase>()
    private val getCriteriaUseCase by di.instance<GetCriteriaUseCase>()
    private val getCriteriaForEvaluatingTeacher by di.instance<GetCriteriaForEvaluatingTeacher>()
    private val createTeacherEvaluationUseCase by di.instance<CreateTeacherEvaluationUseCase>()

    override val container: Container<CriteriaState, CriteriaSideEffect> = viewModelScope
        .container(CriteriaState.Load) {
            CriteriaEventTransmitter.subscribe(this@CriteriaVM)
            loadCriteria()
        }

    private fun loadCriteria() = intent {
        if (isEdit)
            getCriteriaUseCase.execute()
                .onFailure(::onError)
                .onSuccess { list ->
                    reduce { CriteriaState.CriteriaManagement(list) }
                }
        else {
            getCriteriaForEvaluatingTeacher
                .execute(teacherId!!, monthIndex!!)
                .onFailure(::onError)
                .onSuccess { data ->
                    reduce {
                        CriteriaState.TeacherEvaluationAccordingToCriteria(
                            listCriterion = data.listCriterion,
                            alreadyPostedTeacherEvaluations = data.alreadyPostedTeacherEvaluations,
                            teacherName = data.teacherName
                        )
                    }
                }
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
            is CriteriaEvent.SeeFullCriteriaInformation -> seeFullCriteriaInformation(event.position)
            is CriteriaEvent.TeacherEvaluation -> teacherEvaluation(event.evaluationId)
        }
    }

    private fun seeFullCriteriaInformation(position: Int) = blockingIntent {
        postSideEffect(CriteriaSideEffect.OpenFullCriteriaInformation(position))
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
        getCriteriaUseCase.execute(targetState.searchText)
            .onFailure(::onError)
            .onSuccess { list ->
                reduce {
                    targetState.copy(
                        listCriterion = list
                    )
                }
            }
    }
    private fun teacherEvaluation(evaluationId:String) = intent {
        if(Calendar.getInstance().get(Calendar.MONTH).inc() != monthIndex) return@intent
        createTeacherEvaluationUseCase.execute(teacherId!!, evaluationId)
            .onFailure(::onError)
            .onSuccess { teacherEvaluationModel ->
                (state as CriteriaState.TeacherEvaluationAccordingToCriteria).also {targetState->
                    val newMap = targetState.alreadyPostedTeacherEvaluations.toMutableMap()
                    newMap[teacherEvaluationModel.criterionId] = teacherEvaluationModel
                    reduce {
                        targetState.copy(
                            alreadyPostedTeacherEvaluations = newMap
                        )
                    }
                }
            }
    }
    // Search
    private fun inputSearchText(text: String) = blockingIntent {
        (state as? CriteriaState.CriteriaManagement)?.also { targetState ->
            reduce {
                targetState.copy(searchText = text)
            }
        }
        (state as? CriteriaState.TeacherEvaluationAccordingToCriteria)?.also { targetState ->
            reduce {
                targetState.copy(searchText = text)
            }
        }
        searchCriteriaJob?.cancel()
        searchCriteriaJob = searchCriterion(text)
    }

    private fun searchCriterion(text: String) = intent {
        delay(500)
        val result = getCriteriaUseCase.execute(text)
        if (result.isFailure) return@intent onError(result.exceptionOrNull()!!)
        (state as? CriteriaState.CriteriaManagement)?.also { targetState ->
            reduce {
                targetState.copy(listCriterion = result.getOrThrow())
            }
        }
        (state as? CriteriaState.TeacherEvaluationAccordingToCriteria)?.also { targetState ->
            reduce {
                targetState.copy(listCriterion = result.getOrThrow())
            }
        }
    }

    // Search
    private fun deleteCriterion(criterionId: String) = intent {
        val searchText = (state as? CriteriaState.CriteriaManagement)?.searchText
            ?: return@intent
        deleteCriterionUseCase.execute(criterionId, searchText)
            .onFailure(::onError)
            .onSuccess { list ->
                reduce {
                    (state as? CriteriaState.CriteriaManagement)?.copy(
                        listCriterion = list
                    ) ?: state
                }
            }
    }
}