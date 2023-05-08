package sk.devprog.firmy.data.model.company

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sk.devprog.firmy.data.model.TimeRangeData
import java.time.LocalDate

@Serializable
data class CompanyShare(
    @SerialName("validFrom")
    @Contextual
    override val validFrom: LocalDate,

    @SerialName("validTo")
    @Contextual
    override val validTo: LocalDate? = null,

    @SerialName("fullName")
    val fullName: String? = null,

    @SerialName("personName")
    val personName: CompanyPersonName? = null,

    @SerialName("shareType")
    val shareType: CompanyCodeValue,

    @SerialName("shareForm")
    val shareForm: String? = null,

    @SerialName("shareNominalValue")
    val shareNominalValue: Double,

    @SerialName("shareCurrency")
    val shareCurrency: CompanyCodeValue,

    @SerialName("amountOfFunds")
    val amountOfFunds: Double,

    @SerialName("transferability")
    val transferability: String? = null,
): TimeRangeData
