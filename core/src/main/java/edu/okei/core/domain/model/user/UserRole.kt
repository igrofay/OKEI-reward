package edu.okei.core.domain.model.user

enum class UserRole{
    Director,
    Appraiser,
    Teacher;

    companion object{
        fun getRole(id: Int): UserRole {
            return entries[id]
        }
    }

}