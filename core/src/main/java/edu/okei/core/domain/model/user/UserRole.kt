package edu.okei.core.domain.model.user

enum class UserRole(val id: Int){
    Undefined(-1),
    Director(0),
    Appraiser(1),
    Teacher(2);

    companion object{
        fun getRole(id: Int): UserRole {
            return entries.find { userRole -> userRole.id == id }!!
        }
    }

}