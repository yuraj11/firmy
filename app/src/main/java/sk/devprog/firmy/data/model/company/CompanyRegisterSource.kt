package sk.devprog.firmy.data.model.company

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyRegisterSource(
    @SerialName("value")
    val value: CompanyCodeValue,

    @SerialName("registrationOffices")
    val registrationOffices: List<CompanyTimedValue>,

    @SerialName("registrationNumbers")
    val registrationNumbers: List<CompanyTimedValue>,
)
