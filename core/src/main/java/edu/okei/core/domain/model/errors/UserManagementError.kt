package edu.okei.core.domain.model.errors

sealed class UserManagementError : AppErrors(){
    data object InvalidNameFormat : UserManagementError()
}