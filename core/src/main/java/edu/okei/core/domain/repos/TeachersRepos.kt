package edu.okei.core.domain.repos

import edu.okei.core.domain.model.teacher.TeacherModel

interface TeachersRepos {
    suspend fun getAllTeacher() : Result<List<TeacherModel>>
}