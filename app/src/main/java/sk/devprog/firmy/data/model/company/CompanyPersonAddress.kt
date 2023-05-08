package sk.devprog.firmy.data.model.company

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyPersonAddress(
    @SerialName("formatedAddress")
    val formatedAddress: String? = null,

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
)

fun CompanyPersonAddress.getFormattedAddress(): String =
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
            if (regNumber > 0) {
                append(" ")
                append(regNumber)
            }
            if (postalCodes.isNotEmpty()) {
                append(", ")
                append(postalCodes.first())
            }
            append(", ")
            append(country.value)
        }
