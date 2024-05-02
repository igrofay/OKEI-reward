package edu.okei.reward.teachers.view_model

import androidx.lifecycle.viewModelScope
import edu.okei.reward.common.view_model.AppVM
import edu.okei.reward.teachers.model.TeacherAddOrEditEvent
import edu.okei.reward.teachers.model.TeacherAddOrEditSideEffect
import edu.okei.reward.teachers.model.TeacherAddOrEditState
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.reduce

class TeacherAddOrEditVM(
    override val di: DI,
) : AppVM<TeacherAddOrEditState, TeacherAddOrEditSideEffect, TeacherAddOrEditEvent>(),
    DIAware
{


    override val container: Container<TeacherAddOrEditState, TeacherAddOrEditSideEffect> =
        viewModelScope.container(TeacherAddOrEditState())


    override fun onEvent(event: TeacherAddOrEditEvent) {
        when(event){
            TeacherAddOrEditEvent.AddOrEdit -> {

            }
            is TeacherAddOrEditEvent.InputFIO -> blockingIntent {
                reduce { state.copy(fio = event.fio) }
            }
        }
    }

    override fun onError(er: Throwable) {
    }
}