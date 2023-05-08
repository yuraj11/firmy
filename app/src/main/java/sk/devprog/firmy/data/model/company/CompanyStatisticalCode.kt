package sk.devprog.firmy.data.model.company

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyStatisticalCode(
    @SerialName("statCodesActualization")
    val statCodesActualization: String,

    @SerialName("mainActivity")
    val mainActivity: CompanyCodeValue? = null,

    @SerialName("esa2010")
    val esa2010: CompanyCodeValue? = null,
)
