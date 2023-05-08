package sk.devprog.firmy.data.model.company

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class CompanyOrganizationUnit(
    @SerialName("type")
    val type: CompanyCodeValue,

    @SerialName("identifier")
    val identifier: String,

    @SerialName("fullNames")
    val fullNames: List<CompanyTimedValue>,

    @SerialName("addresses")
    val addresses: List<CompanyAddress>,

    @SerialName("establishment")
    @Contextual
    val establishment: LocalDate,

    @SerialName("termination")
    @Contextual
    val termination: LocalDate? = null,

    @SerialName("stakeholders")
    val stakeholders: List<CompanyStakeholder>? = null,

    @SerialName("activities")
    val activities: List<CompanyActivityValue>,
)
