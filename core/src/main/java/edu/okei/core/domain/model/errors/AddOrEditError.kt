package edu.okei.core.domain.model.errors

sealed class AddOrEditError : AppErrors() {
    data object DataFilledInIncorrectly : AddOrEditError()
}