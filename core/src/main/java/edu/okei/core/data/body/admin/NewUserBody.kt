package edu.okei.core.data.body.admin

import edu.okei.core.domain.model.user.UserRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class NewUserBody(
    @SerialName("fullname")
    val fio: String,
    val role: UserRole,
)