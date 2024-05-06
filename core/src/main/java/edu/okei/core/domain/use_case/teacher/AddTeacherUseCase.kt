package edu.okei.core.domain.use_case.teacher

import edu.okei.core.domain.model.errors.AddOrEditError
import edu.okei.core.domain.model.user.UserRole
import edu.okei.core.domain.repos.UsersManagementRepos
import java.util.Locale

class AddTeacherUseCase(
    private val usersManagementRepos: UsersManagementRepos,
) {
    suspend fun execute(fio: String) : Result<Boolean>{
        if (fio.isBlank()) throw AddOrEditError.DataFilledInIncorrectly
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