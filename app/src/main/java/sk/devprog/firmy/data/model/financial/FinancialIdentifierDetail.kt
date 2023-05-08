package sk.devprog.firmy.data.model.financial

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class FinancialIdentifierDetail(
    @SerialName("id")
    val id: Int,

    @SerialName("idUctovnychZavierok")
    val idFinancialStatements: List<Int>? = null,

    @SerialName("idVyrocnychSprav")
    val idAnnualReports: List<Int>? = null,

    @SerialName("velkostOrganizacie")
    val organizationSizeCode: String,

    @SerialName("druhVlastnictva")
    val organizationOwnershipCode: String,

    @SerialName("skNace")
    val skNaceCode: String,

    @SerialName("konsolidovana")
    val consolidated: Boolean,

    @SerialName("mesto")
    val city: String,

    @SerialName("ulica")
    val street: String,

    @SerialName("datumZalozenia")
    @Contextual
    val establishmentDate: LocalDate,

    @SerialName("datumZrusenia")
    @Contextual
    val terminationDate: LocalDate? = null,

    @SerialName("psc")
    val zipCode: String,

    @SerialName("datumPoslednejUpravy")
    @Contextual
    val lastModificationDate: LocalDate,

    @SerialName("pravnaForma")
    val legalFormCode: String,

    @SerialName("sidlo")
    val registrationOfficeCode: String,

    @SerialName("zdrojDat")
    val sourceOfDataCode: String,

    @SerialName("dic")
    val dic: String,

    @SerialName("nazovUJ")
    val entityName: String,

    @SerialName("kraj")
    val regionCode: String,

    @SerialName("okres")
    val districtCode: String,

    @SerialName("ico")
    val ico: String,

    @SerialName("sid")
    val sid: String? = null,
)
