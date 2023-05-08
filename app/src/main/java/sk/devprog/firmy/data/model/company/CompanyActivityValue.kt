package sk.devprog.firmy.data.model.company

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sk.devprog.firmy.data.model.TimeRangeData
import java.time.LocalDate

@Serializable
data class CompanyActivityValue(
    @SerialName("economicActivityDescription")
    val economicActivityDescription: String,

    @SerialName("validFrom")
    @Contextual
    override val validFrom: LocalDate,

    @SerialName("validTo")
    @Contextual
    override val validTo: LocalDate? = null,

    @SerialName("suspendedFrom")
    @Contextual
    val suspendedFrom: LocalDate? = null,

    @SerialName("suspendedTo")
    @Contextual
    val suspendedTo: LocalDate? = null
): TimeRangeData
