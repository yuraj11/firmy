package sk.devprog.firmy.data.model.company

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class CompanyKuvPerson(
    @SerialName("personName")
    val personName: CompanyPersonName,

    @SerialName("birthDate")
    @Contextual
    val birthDate: LocalDate? = null,

    @SerialName("citizenship")
    val citizenship: CompanyCodeValue? = null,

    @SerialName("country")
    val country: CompanyCodeValue,

    @SerialName("kuvInfo")
    val kuvInfo: List<CompanyCodeTimedValue>,
)
