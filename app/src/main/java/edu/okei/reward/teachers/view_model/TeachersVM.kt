package edu.okei.reward.teachers.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import edu.okei.core.domain.repos.TeachersRepos
import edu.okei.core.domain.use_case.TeachersIterator
import edu.okei.reward.common.view_model.AppVM
import edu.okei.reward.teachers.model.TeachersEvent
import edu.okei.reward.teachers.model.TeachersSideEffect
import edu.okei.reward.teachers.model.TeachersState
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.reduce

class TeachersVM(
    override val di: DI,
    savedStateHandle: SavedStateHandle,
) :
    AppVM<TeachersState, TeachersSideEffect, TeachersEvent>(),
    DIAware
{
    private val teachersIterator by di.instance<TeachersIterator>()
    private val monthIndex = savedStateHandle.get<String>("montIndex") as Int?
    private val isEdit = monthIndex == null

    override val container: Container<TeachersState, TeachersSideEffect> = viewModelScope
        .container(TeachersState.Load){
            if (isEdit)
                teachersIterator.getTeachers().fold(
                    onSuccess = { list->
                        reduce { TeachersState.TeacherManagement(list) }
                    },
                    onFailure = ::onError
                )
        }

    override fun onEvent(event: TeachersEvent) {

    }

    override fun onError(er: Throwable) {

    }

}