package edu.okei.reward.teachers.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import edu.okei.core.domain.model.errors.AddOrEditError
import edu.okei.core.domain.model.errors.UserManagementError
import edu.okei.core.domain.use_case.teacher.AddTeacherUseCase
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
    private val addTeacherUseCase by di.instance<AddTeacherUseCase>()

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
        addTeacherUseCase.execute(state.fio)
            .onFailure(::onError)
            .onSuccess {
                TeachersEventTransmitter.sendMessage(TeachersEvent.UpdateListTeacher)
                postSideEffect(AddOrEditTeacherSideEffect.EditsCompleted)
            }
    }

    override fun onError(er: Throwable) {
        when(er){
            UserManagementError.InvalidNameFormat -> intent {
                postSideEffect(AddOrEditTeacherSideEffect.ShowMessage(R.string.invalid_name_format))
            }
            AddOrEditError.DataFilledInIncorrectly -> intent {
                postSideEffect(AddOrEditTeacherSideEffect.ShowMessage(R.string.fill_in_blanks))
            }
            else -> Log.e(nameVM, er.message, er)
        }
    }
}