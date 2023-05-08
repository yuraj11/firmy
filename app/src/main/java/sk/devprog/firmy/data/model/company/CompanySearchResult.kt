package sk.devprog.firmy.data.model.company

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class CompanySearchResult(
    @SerialName("id")
    val id: Int,

    @SerialName("identifiers")
    val identifiers: List<CompanyTimedValue>,

    @SerialName("fullNames")
    val fullNames: List<CompanyTimedValue>,

    @SerialName("sourceRegister")
    val sourceRegister: CompanyRegisterSource,

    @SerialName("addresses")
    val addresses: List<CompanyAddress>,

    @SerialName("dbModificationDate")
    @Contextual
    val dbModificationDate: LocalDate,

    @SerialName("establishment")
    @Contextual
    val establishment: LocalDate,

    @SerialName("termination")
    @Contextual
    val termination: LocalDate? = null
)

@Serializable
data class CompanySearchResults(
    @SerialName("results")
    val results: List<CompanySearchResult>,

    @SerialName("license")
    val license: String
)
