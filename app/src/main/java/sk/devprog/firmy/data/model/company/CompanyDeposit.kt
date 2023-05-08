package sk.devprog.firmy.data.model.company

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sk.devprog.firmy.data.model.TimeRangeData
import java.time.LocalDate

@Serializable
data class CompanyDeposit(
    @SerialName("validFrom")
    @Contextual
    override val validFrom: LocalDate,

    @SerialName("validTo")
    @Contextual
    override val validTo: LocalDate? = null,

    @SerialName("personName")
    val personName: CompanyPersonName? = null,

    @SerialName("fullName")
    val fullName: String? = null,

    @SerialName("type")
    val type: String? = null,

    @SerialName("amount")
    val amount: Double,

    @SerialName("currency")
    val currency: CompanyCodeValue,
): TimeRangeData
