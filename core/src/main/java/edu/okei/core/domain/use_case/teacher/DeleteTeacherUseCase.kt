package edu.okei.core.domain.use_case.teacher

import edu.okei.core.domain.model.teacher.TeacherModel
import edu.okei.core.domain.repos.UsersManagementRepos

class DeleteTeacherUseCase(
    private val usersManagementRepos: UsersManagementRepos,
    private val getTeachersUseCase: GetTeachersUseCase,
) {
    suspend fun execute(id: String, searchText: String) : Result<List<TeacherModel>>{
        val result = usersManagementRepos.deleteUser(id)
        return if (result.isSuccess){
            getTeachersUseCase.execute(searchText)
        }else{
            Result.failure(result.exceptionOrNull()!!)
        }
    }
}