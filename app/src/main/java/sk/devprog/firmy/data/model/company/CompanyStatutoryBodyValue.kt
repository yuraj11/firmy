package sk.devprog.firmy.data.model.company

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sk.devprog.firmy.data.model.TimeRangeData
import java.time.LocalDate

@Serializable
data class CompanyStatutoryBodyValue(
    @SerialName("stakeholderType")
    val stakeholderType: CompanyCodeValue,

    @SerialName("statutoryBodyMember")
    val statutoryBodyMember: CompanyCodeValue? = null,

    @SerialName("validFrom")
    @Contextual
    override val validFrom: LocalDate,

    @SerialName("validTo")
    @Contextual
    override val validTo: LocalDate? = null,

    @SerialName("address")
    val address: CompanyPersonAddress,

    @SerialName("personName")
    val personName: CompanyPersonName? = null,

    @SerialName("identifier")
    val identifier: String? = null,

    @SerialName("fullName")
    val fullName: String? = null,

    @SerialName("establishment")
    @Contextual
    val establishment: LocalDate? = null,

    @SerialName("termination")
    @Contextual
    val termination: LocalDate? = null,
): TimeRangeData
