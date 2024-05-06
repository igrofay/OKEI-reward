package edu.okei.core.domain.use_case.teacher

import edu.okei.core.domain.model.teacher.TeacherModel
import edu.okei.core.domain.model.user.UserRole
import edu.okei.core.domain.repos.UsersManagementRepos

class GetTeachersUseCase(
    private val usersManagementRepos: UsersManagementRepos,
) {
    suspend fun execute(searchText: String = ""): Result<List<TeacherModel>> {
        val trimSearchText = searchText.trim()
        return usersManagementRepos.getAllUsers().map { list ->
            if (trimSearchText.isNotBlank())
                list.filter { model ->
                    model.role == UserRole.Teacher &&
                            (model.fullname.contains(trimSearchText, ignoreCase = true) ||
                                    model.login.contains(trimSearchText, ignoreCase = true))
                }
            else
                list.filter { model ->
                    model.role == UserRole.Teacher
                }
        }
    }

}