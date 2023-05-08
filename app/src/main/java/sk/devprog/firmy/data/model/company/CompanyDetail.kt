package sk.devprog.firmy.data.model.company

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class CompanyDetail(
    @SerialName("id")
    val id: Int,

    @SerialName("dbModificationDate")
    @Contextual
    val dbModificationDate: LocalDate,

    @SerialName("identifiers")
    val identifiers: List<CompanyTimedValue>,

    @SerialName("fullNames")
    val fullNames: List<CompanyTimedValue>,

    @SerialName("alternativeNames")
    val alternativeNames: List<CompanyTimedValue>? = null,

    @SerialName("addresses")
    val addresses: List<CompanyAddress>,

    @SerialName("legalForms")
    val legalForms: List<CompanyCodeTimedValue>,

    @SerialName("establishment")
    @Contextual
    val establishment: LocalDate,

    @SerialName("termination")
    @Contextual
    val termination: LocalDate? = null,

    @SerialName("activities")
    val activities: List<CompanyActivityValue>? = null,

    @SerialName("statutoryBodies")
    val statutoryBodies: List<CompanyStatutoryBodyValue>? = null,

    @SerialName("stakeholders")
    val stakeholders: List<CompanyStakeholder>? = null,

    @SerialName("legalStatuses")
    val legalStatuses: List<CompanyCodeTimedValue>? = null,

    @SerialName("otherLegalFacts")
    val otherLegalFacts: List<CompanyTimedValue>? = null,

    @SerialName("authorizations")
    val authorizations: List<CompanyTimedValue>? = null,

    @SerialName("equities")
    val equities: List<CompanyEquity>? = null,

    @SerialName("shares")
    val shares: List<CompanyShare>? = null,

    @SerialName("deposits")
    val deposits: List<CompanyDeposit>? = null,

    @SerialName("sourceRegister")
    val sourceRegister: CompanyRegisterSource,

    @SerialName("predecessors")
    val predecessors: List<CompanyLegalPerson>? = null,

    @SerialName("successors")
    val successors: List<CompanyLegalPerson>? = null,

    @SerialName("statisticalCodes")
    val statisticalCodes: CompanyStatisticalCode? = null,

    @SerialName("kuvPersonsInfo")
    val kuvPersonsInfo: List<CompanyKuvPerson>? = null,

    @SerialName("topManagementsInfo")
    val topManagementsInfo: List<CompanyKuvPerson>? = null,

    @SerialName("organizationUnits")
    val organizationUnits: List<CompanyOrganizationUnit>? = null,

    @SerialName("license")
    val license: String
)
