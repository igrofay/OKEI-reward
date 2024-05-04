package edu.okei.reward.teachers.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import edu.okei.core.domain.model.errors.UserManagementErrors
import edu.okei.core.domain.use_case.TeachersIterator
import edu.okei.reward.R
import edu.okei.reward.common.view_model.AppVM
import edu.okei.reward.teachers.model.AddOrEditTeacherEvent
import edu.okei.reward.teachers.model.AddOrEditTeacherSideEffect
import edu.okei.reward.teachers.model.AddOrEditTeacherState
import edu.okei.reward.teachers.model.TeachersEvent
import edu.okei.reward.teachers.model.TeachersEventTransmitter
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class AddOrEditTeacherVM(
    override val di: DI,
) : AppVM<AddOrEditTeacherState, AddOrEditTeacherSideEffect, AddOrEditTeacherEvent>(),
    DIAware
{
    private val teachersIterator by di.instance<TeachersIterator>()

    override val container: Container<AddOrEditTeacherState, AddOrEditTeacherSideEffect> =
        viewModelScope.container(AddOrEditTeacherState())


    override fun onEvent(event: AddOrEditTeacherEvent) {
        when(event){
            AddOrEditTeacherEvent.AddOrEditTeacher -> addTeacher()
            is AddOrEditTeacherEvent.InputFIO -> blockingIntent {
                reduce { state.copy(fio = event.fio) }
            }
        }
    }
    private fun addTeacher() = intent {
        if (state.fio.isBlank()) return@intent
        teachersIterator.add(state.fio)
            .onFailure(::onError)
            .onSuccess {
                TeachersEventTransmitter.sendMessage(TeachersEvent.UpdateListTeacher)
                postSideEffect(AddOrEditTeacherSideEffect.EditsCompleted)
            }
    }

    override fun onError(er: Throwable) {
        when(er){
            UserManagementErrors.InvalidNameFormat -> intent {
                postSideEffect(AddOrEditTeacherSideEffect.ShowMessage(R.string.invalid_name_format))
            }
            else -> Log.e(nameVM, er.message, er)
        }
    }
}