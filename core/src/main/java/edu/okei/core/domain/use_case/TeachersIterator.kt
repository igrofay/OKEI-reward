package edu.okei.core.domain.use_case

import edu.okei.core.domain.model.teacher.TeacherModel
import edu.okei.core.domain.model.user.UserRole
import edu.okei.core.domain.repos.UsersManagementRepos
import java.util.Locale

class TeachersIterator(
    private val usersManagementRepos: UsersManagementRepos,
) {
    suspend fun getTeachers(searchText: String = ""): Result<List<TeacherModel>> {
        val trimSearchText = searchText.trim()
        return usersManagementRepos.getAllUsers().map { list ->
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
        val result = usersManagementRepos.deleteUser(id)
        return if (result.isSuccess){
            getTeachers(searchText)
        }else{
            Result.failure(result.exceptionOrNull()!!)
        }
    }
    suspend fun add(fio: String) : Result<Boolean>{
        val correctionFIO = fio.trim()
            .split(" ")
            .joinToString(" ") { str ->
                str.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }
            }
        return usersManagementRepos.addUser(correctionFIO, UserRole.Teacher)
    }
}