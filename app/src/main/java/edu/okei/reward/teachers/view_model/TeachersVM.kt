package edu.okei.reward.teachers.view_model

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import edu.okei.core.domain.use_case.TeachersIterator
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
    private val teachersIterator by di.instance<TeachersIterator>()
    private val monthIndex = savedStateHandle.get<String>("montIndex") as Int?
    private val isEdit = monthIndex == null
    private var searchUserJob: Job? = null
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
        if (isEdit)
            teachersIterator.getTeachers()
                .onFailure(::onError)
                .onSuccess { list ->
                    reduce { TeachersState.TeacherManagement(list) }
                }
    }

    private fun updateList() = intent {
        val targetState = state as? TeachersState.TeacherManagement ?: return@intent
        teachersIterator.getTeachers(targetState.searchText)
            .onFailure(::onError)
            .onSuccess { list ->
                reduce {
                    targetState.copy(
                        listTeacher = list
                    )
                }
            }
    }

    // Receiving data
    // Search
    private fun inputSearchText(text: String) = blockingIntent {
        val targetState = state as? TeachersState.TeacherManagement ?: return@blockingIntent
        reduce {
            targetState.copy(searchText = text)
        }
        searchUserJob?.cancel()
        searchUserJob = searchTeachers(text)
    }

    private fun searchTeachers(text: String) = intent {
        delay(500)
        val result = teachersIterator.getTeachers(text)
        if (result.isFailure) return@intent onError(result.exceptionOrNull()!!)
        reduce {
            (state as? TeachersState.TeacherManagement)?.copy(
                listTeacher = result.getOrNull()!!
            ) ?: state
        }
    }
    // Search

    // Teacher Managent
    private fun deleteUser(id: String) = intent {
        val searchText = (state as? TeachersState.TeacherManagement)
            ?.searchText ?: return@intent
        teachersIterator.delete(id, searchText)
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