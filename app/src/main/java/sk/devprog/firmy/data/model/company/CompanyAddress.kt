package sk.devprog.firmy.data.model.company

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sk.devprog.firmy.data.model.TimeRangeData
import java.time.LocalDate

@Serializable
data class CompanyAddress(
    @SerialName("formatedAddress")
    val formatedAddress: String? = null,

    @SerialName("validFrom")
    @Contextual
    override val validFrom: LocalDate,

    @SerialName("validTo")
    @Contextual
    override val validTo: LocalDate? = null,

    @SerialName("street")
    val street: String? = null,

    @SerialName("regNumber")
    val regNumber: Int,

    @SerialName("buildingNumber")
    val buildingNumber: String? = null,

    @SerialName("postalCodes")
    val postalCodes: List<String> = emptyList(),

    @SerialName("municipality")
    val municipality: CompanyCodeValue,

    @SerialName("country")
    val country: CompanyCodeValue,

    @SerialName("district")
    val district: CompanyCodeValue? = null,

    @SerialName("buildingIndex")
    val buildingIndex: String? = null,
) : TimeRangeData

fun CompanyAddress.getFormattedAddress(): String =
    formatedAddress
        ?: buildString {
            street?.let {
                append(it)
                append(" ")
            }
            buildingNumber?.let {
                append(it)
                append(", ")
            }
            append(municipality.value)
            if (postalCodes.isNotEmpty()) {
                append(", ")
                append(postalCodes.first())
            }
        }
