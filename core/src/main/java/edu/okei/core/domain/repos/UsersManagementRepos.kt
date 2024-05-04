package edu.okei.core.domain.repos

import edu.okei.core.domain.model.teacher.TeacherModel
import edu.okei.core.domain.model.user.UserRole

interface UsersManagementRepos {
    suspend fun getAllUsers() : Result<List<TeacherModel>>
    suspend fun deleteUser(id: String) : Result<Boolean>
    suspend fun addUser(fio: String, role: UserRole) : Result<Boolean>
}