package edu.okei.core.domain.use_case.teacher

import edu.okei.core.domain.model.teacher.TeacherModel
import edu.okei.core.domain.model.user.UserRole
import edu.okei.core.domain.repos.UsersManagementRepos

class GetTeachersUseCase(
    private val usersManagementRepos: UsersManagementRepos,
) {
    suspend fun execute(searchText: String = ""): Result<List<TeacherModel>> {
        return usersManagementRepos.getAllUsers().map { list ->
            filterTeachersByText(list, searchText)
                .filter { model -> model.role == UserRole.Teacher }
                .sortedBy { it.fullname }
        }
    }
    private fun filterTeachersByText(
        listTeacher: List<TeacherModel>,
        searchText: String,
    ): List<TeacherModel> {
        val trimSearchText = searchText.trim()
        return if (trimSearchText.isNotBlank())
            listTeacher.filter {model ->
                (model.fullname.contains(trimSearchText, ignoreCase = true) ||
                        model.login.contains(trimSearchText, ignoreCase = true))
            }
        else
            listTeacher
    }
}