package edu.okei.reward.teachers.model

sealed class AddOrEditClarification {
    data object Nothing : AddOrEditClarification()
    data object Add : AddOrEditClarification()
    data class Edit(val id: String) : AddOrEditClarification()
}