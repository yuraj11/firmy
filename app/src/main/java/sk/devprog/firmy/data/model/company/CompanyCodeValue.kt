package sk.devprog.firmy.data.model.company

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyCodeValue(
    @SerialName("value")
    val value: String,

    @SerialName("code")
    @Contextual
    val code: String? = null,

    @SerialName("codelistCode")
    @Contextual
    val codelistCode: String? = null
)
