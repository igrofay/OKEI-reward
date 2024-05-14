package edu.okei.core.domain.model.user

fun shortenFullName(fullname: String): String {
    val parts = fullname.split(" ")
    return if (parts.size >= 3) {
        val lastName = parts[0]
        val firstNameInitial = parts[1].first()
        val middleNameInitial = parts[2].first()
        "$lastName $firstNameInitial. $middleNameInitial."
    } else {
        fullname // Если в полном имени менее трех частей, возвращаем его без изменений
    }
}