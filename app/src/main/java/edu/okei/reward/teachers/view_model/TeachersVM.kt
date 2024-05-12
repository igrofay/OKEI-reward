package edu.okei.reward.teachers.view_model

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import edu.okei.core.domain.use_case.teacher.DeleteTeacherUseCase
import edu.okei.core.domain.use_case.teacher.GetListTeacherRatingUseCase
import edu.okei.core.domain.use_case.teacher.GetTeachersUseCase
import edu.okei.reward.common.model.UIEvent
import edu.okei.reward.common.view_model.AppVM
import edu.okei.reward.teachers.model.TeachersEvent
import edu.okei.reward.teachers.model.TeachersEventTransmitter
import edu.okei.reward.teachers.model.TeachersSideEffect
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

class TeachersVM(
    override val di: DI,
    savedStateHandle: SavedStateHandle,
) :
    AppVM<TeachersState, TeachersSideEffect, TeachersEvent>(),
    DIAware,
    TeachersEventTransmitter.Observer {
    private val getTeachersUseCase by di.instance<GetTeachersUseCase>()
    private val deleteTeacherUseCase by di.instance<DeleteTeacherUseCase>()
    private val getListTeacherRatingUseCase by di.instance<GetListTeacherRatingUseCase>()
    private val monthIndex = savedStateHandle.get<String>("monthIndex")?.toInt()
    private val isManagement = monthIndex == null
    private var searchTeacherJob: Job? = null
    override val container: Container<TeachersState, TeachersSideEffect> = viewModelScope
        .container(TeachersState.Load) {
            TeachersEventTransmitter.subscribe(this@TeachersVM)
            loadTeachers()
        }

    override fun onEvent(event: TeachersEvent) {
        when (event) {
            is TeachersEvent.DeleteUser -> deleteUser(event.login)
            is TeachersEvent.InputSearch -> inputSearchText(event.search)
            TeachersEvent.AddTeacher -> blockingIntent {
                postSideEffect(TeachersSideEffect.OpenAddTeacher)
            }
            TeachersEvent.UpdateListTeacher -> updateList()
            is TeachersEvent.SeeTeacherCriteria -> blockingIntent {
                if (!isManagement) postSideEffect(
                    TeachersSideEffect.OpenTeacherCriteria(
                        monthIndex!!,
                        event.teacherId
                    )
                )
            }
        }
    }

    override fun onError(er: Throwable) {
        Log.e(nameVM, er.message, er)
    }

    override fun messageReceived(message: UIEvent) {
        if (message is TeachersEvent.UpdateListTeacher)
            updateList()
    }

    override fun onCleared() {
        TeachersEventTransmitter.unsubscribe(this)
        super.onCleared()
    }

    // Receiving data
    private fun loadTeachers() = intent {
        if (isManagement)
            getTeachersUseCase.execute()
                .onFailure(::onError)
                .onSuccess { list ->
                    reduce { TeachersState.TeacherManagement(list) }
                }
        else
            getListTeacherRatingUseCase.execute(monthIndex!!)
                .onFailure(::onError)
                .onSuccess { list ->
                    reduce { TeachersState.TeacherRating(list) }
                }
    }

    private fun updateList() = intent {
        if (isManagement){
            val targetState = state as? TeachersState.TeacherManagement ?: return@intent
            getTeachersUseCase.execute(targetState.searchText)
                .onFailure(::onError)
                .onSuccess { list ->
                    reduce {
                        targetState.copy(
                            listTeacher = list
                        )
                    }
                }
        }else{
            Log.e(nameVM, (state as? TeachersState.TeacherRating).toString())
            val targetState = state as? TeachersState.TeacherRating ?: return@intent
            getListTeacherRatingUseCase.execute(monthIndex!!)
                .onFailure(::onError)
                .onSuccess { list ->
                    reduce { targetState.copy(listTeacher = list) }
                }
        }

    }

    // Receiving data
    // Search
    private fun inputSearchText(text: String) = blockingIntent {
        (state as? TeachersState.TeacherManagement)?.also { targetState ->
            reduce {
                targetState.copy(searchText = text)
            }
        }
        (state as? TeachersState.TeacherRating)?.also { targetState ->
            reduce {
                targetState.copy(searchText = text)
            }
        }
        searchTeacherJob?.cancel()
        searchTeacherJob = searchTeachers(text)
    }

    private fun searchTeachers(text: String) = intent {
        delay(500)
        if (isManagement) {
            val result =
                getTeachersUseCase.execute(text)
            if (result.isFailure) return@intent onError(result.exceptionOrNull()!!)
            (state as TeachersState.TeacherManagement).also { targetState ->
                reduce {
                    targetState.copy(
                        listTeacher = result.getOrThrow()
                    )
                }
            }
        } else {
            val result =
                getListTeacherRatingUseCase.execute(monthIndex!!, text)
            if (result.isFailure) return@intent onError(result.exceptionOrNull()!!)
            (state as TeachersState.TeacherRating).also { targetState ->
                reduce {
                    targetState.copy(
                        listTeacher = result.getOrThrow()
                    )
                }
            }
        }


    }

    // Search
    // Teacher Managent
    private fun deleteUser(id: String) = intent {
        val searchText = (state as? TeachersState.TeacherManagement)?.searchText
            ?: return@intent
        deleteTeacherUseCase.execute(id, searchText)
            .onFailure(::onError)
            .onSuccess { list ->
                reduce {
                    (state as? TeachersState.TeacherManagement)?.copy(
                        listTeacher = list
                    ) ?: state
                }
            }
    }
    // Teacher Managent
}