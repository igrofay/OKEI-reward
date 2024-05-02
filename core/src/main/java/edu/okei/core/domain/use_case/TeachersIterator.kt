package edu.okei.core.domain.use_case

import edu.okei.core.domain.model.teacher.TeacherModel
import edu.okei.core.domain.model.user.UserRole
import edu.okei.core.domain.repos.TeachersRepos

class TeachersIterator(
    private val teachersRepos: TeachersRepos,
) {
    suspend fun getTeachers(searchText: String = ""): Result<List<TeacherModel>> {
        val trimSearchText = searchText.trim()
        return teachersRepos.getAllTeacher().map { list ->
            if (trimSearchText.isNotBlank())
                list.filter { model ->
                    model.role == UserRole.Teacher &&
                            model.fullname.contains(trimSearchText, ignoreCase = true)
                }
            else
                list.filter { model->
                    model.role == UserRole.Teacher
                }
        }
    }
    suspend fun delete(id: String, searchText: String) : Result<List<TeacherModel>>{
        val result = teachersRepos.deleteUser(id)
        return if (result.isSuccess){
            getTeachers(searchText)
        }else{
            Result.failure(result.exceptionOrNull()!!)
        }
    }
}