package edu.okei.reward.teachers.model

import androidx.annotation.StringRes
import edu.okei.reward.common.model.UISideEffect

sealed class AddOrEditTeacherSideEffect : UISideEffect(){
    data class ShowMessage(@StringRes val message: Int) : AddOrEditTeacherSideEffect()
    data object EditsCompleted : AddOrEditTeacherSideEffect()
}