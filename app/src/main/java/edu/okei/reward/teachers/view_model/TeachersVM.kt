package edu.okei.reward.teachers.view_model

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import edu.okei.core.domain.repos.TeachersRepos
import edu.okei.core.domain.use_case.TeachersIterator
import edu.okei.reward.common.view_model.AppVM
import edu.okei.reward.teachers.model.AddOrEditClarification
import edu.okei.reward.teachers.model.TeachersEvent
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
import org.orbitmvi.orbit.syntax.simple.reduce

class TeachersVM(
    override val di: DI,
    savedStateHandle: SavedStateHandle,
) :
    AppVM<TeachersState, TeachersSideEffect, TeachersEvent>(),
    DIAware {
    private val teachersIterator by di.instance<TeachersIterator>()
    private val monthIndex = savedStateHandle.get<String>("montIndex") as Int?
    private val isEdit = monthIndex == null
    private var searchUserJob: Job? = null
    override val container: Container<TeachersState, TeachersSideEffect> = viewModelScope
        .container(TeachersState.Load) {
            loadTeachers()
        }

    private fun loadTeachers() = intent {
        if (isEdit)
            teachersIterator.getTeachers().fold(
                onSuccess = { list ->
                    reduce { TeachersState.TeacherManagement(list) }
                },
                onFailure = ::onError
            )
    }

    private fun searchUsers(text: String) = intent {
        delay(500)
        val result = teachersIterator.getTeachers(text)
        if (result.isFailure) return@intent onError(result.exceptionOrNull()!!)
        reduce {
            (state as? TeachersState.TeacherManagement)?.copy(
                listTeacher = result.getOrNull()!!
            ) ?: state
        }
    }

    override fun onEvent(event: TeachersEvent) {
        when (event) {
            is TeachersEvent.DeleteUser -> deleteUser(event.login)
            is TeachersEvent.InputSearch -> inputSearchText(event.search)
            TeachersEvent.AddTeacher -> blockingIntent {
                reduce {
                    (state as? TeachersState.TeacherManagement)?.copy(
                        addOrEditClarification = AddOrEditClarification.Add
                    ) ?: state
                }
            }
            TeachersEvent.CancelAddOrEdit -> blockingIntent {
                reduce {
                    (state as? TeachersState.TeacherManagement)?.copy(
                        addOrEditClarification = AddOrEditClarification.Nothing
                    ) ?: state
                }
            }
        }
    }

    private fun deleteUser(id: String) = intent {
        val searchText = (state as? TeachersState.TeacherManagement)
            ?.searchText ?: return@intent
        teachersIterator.delete(
            id,
            searchText
        ).fold(
            onFailure = ::onError,
            onSuccess = { list ->
                reduce {
                    (state as? TeachersState.TeacherManagement)?.copy(
                        listTeacher = list
                    ) ?: state
                }
            }
        )
    }

    private fun inputSearchText(text: String) = blockingIntent {
        reduce {
            (state as? TeachersState.TeacherManagement)
                ?.copy(searchText = text) ?: state
        }
        searchUserJob?.cancel()
        searchUserJob = searchUsers(text)
    }

    override fun onError(er: Throwable) {
        Log.e(nameVM, er.message, er)
    }

}