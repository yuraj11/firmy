package sk.devprog.firmy.data.model.company

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sk.devprog.firmy.data.model.TimeRangeData
import java.time.LocalDate

@Serializable
data class CompanyEquity(
    @SerialName("validFrom")
    @Contextual
    override val validFrom: LocalDate,

    @SerialName("validTo")
    @Contextual
    override val validTo: LocalDate? = null,

    @SerialName("value")
    val value: Double? = null,

    @SerialName("valuePaid")
    val valuePaid: Double? = null,

    @SerialName("valueApproved")
    val valueApproved: Double? = null,

    @SerialName("currency")
    val currency: CompanyCodeValue,
): TimeRangeData
