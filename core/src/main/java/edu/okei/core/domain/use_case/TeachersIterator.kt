package edu.okei.core.domain.use_case

import edu.okei.core.domain.model.teacher.TeacherModel
import edu.okei.core.domain.repos.TeachersRepos

class TeachersIterator(
    private val teachersRepos: TeachersRepos,
) {
    suspend fun getTeachers(searchText: String = ""): Result<List<TeacherModel>> {
        return teachersRepos.getAllTeacher().map { list ->
            if (searchText.isNotBlank())
                list.filter { model ->
                    model.fullname.contains(searchText, ignoreCase = true)
                }
            else
                list
        }
    }

}