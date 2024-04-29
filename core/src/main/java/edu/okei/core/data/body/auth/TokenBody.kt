package edu.okei.core.data.body.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TokenBody(
    @SerialName("value")
    val token: String,
)