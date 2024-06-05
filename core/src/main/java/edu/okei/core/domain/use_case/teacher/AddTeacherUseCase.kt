package edu.okei.core.domain.use_case.teacher

import edu.okei.core.domain.model.errors.AddOrEditError
import edu.okei.core.domain.model.user.UserRole
import edu.okei.core.domain.repos.UsersManagementRepos
import java.util.Locale

class AddTeacherUseCase(
    private val usersManagementRepos: UsersManagementRepos,
) {
    suspend fun execute(fio: String) : Result<Boolean> = runCatching{
        if (fio.isBlank()) throw AddOrEditError.DataFilledInIncorrectly
        val correctedFIO = formatFullName(fio)
        return usersManagementRepos.addUser(correctedFIO, UserRole.Teacher)
    }
    private fun formatFullName(fio: String): String {
        val trimmedFIO = fio.trim()
        val nameParts = trimmedFIO.split(" ")
//        val formattedNameParts = nameParts.map { part ->
//            part.replaceFirstChar { char ->
//                if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
//            }
//        }
        return nameParts.joinToString(" ")
    }
}