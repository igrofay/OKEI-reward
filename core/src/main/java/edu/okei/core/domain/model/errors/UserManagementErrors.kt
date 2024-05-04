package edu.okei.core.domain.model.errors

sealed class UserManagementErrors : AppErrors(){
    data object InvalidNameFormat : UserManagementErrors()
}