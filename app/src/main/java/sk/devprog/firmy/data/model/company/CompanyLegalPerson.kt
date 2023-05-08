package sk.devprog.firmy.data.model.company

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sk.devprog.firmy.data.model.TimeRangeData
import java.time.LocalDate

@Serializable
data class CompanyLegalPerson(
    @SerialName("identifier")
    val identifier: String,

    @SerialName("fullName")
    val fullName: String,

    @SerialName("address")
    val address: CompanyPersonAddress,

    @SerialName("validFrom")
    @Contextual
    override val validFrom: LocalDate,

    @SerialName("validTo")
    @Contextual
    override val validTo: LocalDate? = null,
): TimeRangeData
