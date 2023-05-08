package sk.devprog.firmy.data.model.company

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class CompanySearchParameters(
    @SerialName("identifier")
    val identifier: String? = null,

    @SerialName("fullName")
    val fullName: String? = null,

    @SerialName("legalForm")
    val legalForm: String? = null,

    @SerialName("legalStatus")
    val legalStatus: String? = null,

    @SerialName("addressMunicipality")
    val addressMunicipality: String? = null,

    @SerialName("addressStreet")
    val addressStreet: String? = null,

    @SerialName("establishmentAfter")
    @Contextual
    val establishmentAfter: LocalDate? = null,

    @SerialName("establishmentBefore")
    @Contextual
    val establishmentBefore: LocalDate? = null,

    @SerialName("terminationAfter")
    @Contextual
    val terminationAfter: LocalDate? = null,

    @SerialName("terminationBefore")
    @Contextual
    val terminationBefore: LocalDate? = null,

    @SerialName("onlyActive")
    val onlyActive: Boolean = false,

    @SerialName("dbModificationDateAfter")
    @Contextual
    val dbModificationDateAfter: LocalDate? = null,

    @SerialName("dbModificationDateBefore")
    @Contextual
    val dbModificationDateBefore: LocalDate? = null,

    @SerialName("mainActivity")
    val mainActivity: String? = null,

    @SerialName("esa2010")
    val esa2010: String? = null,

    @SerialName("sourceRegister")
    val sourceRegister: String? = null,

    @SerialName("stakeholderType")
    val stakeholderType: String? = null,

    @SerialName("stakeholderPersonGivenName")
    val stakeholderPersonGivenName: String? = null,

    @SerialName("stakeholderPersonFamilyName")
    val stakeholderPersonFamilyName: String? = null,

    @SerialName("stakeholderCompanyName")
    val stakeholderCompanyName: String? = null,

    @SerialName("statutoryBodyType")
    val statutoryBodyType: String? = null,

    @SerialName("statutoryBodyGivenName")
    val statutoryBodyGivenName: String? = null,

    @SerialName("statutoryBodyFamilyName")
    val statutoryBodyFamilyName: String? = null,

    @SerialName("statutoryBodyCompanyName")
    val statutoryBodyCompanyName: String? = null,

    @SerialName("orgUnitsIdentifier")
    val orgUnitsIdentifier: String? = null,

    @SerialName("orgUnitsFullName")
    val orgUnitsFullName: String? = null,

    @SerialName("orgUnitsStakeholderPersonGivenName")
    val orgUnitsStakeholderPersonGivenName: String? = null,

    @SerialName("orgUnitsStakeholderPersonFamilyName")
    val orgUnitsStakeholderPersonFamilyName: String? = null,

    @SerialName("orgUnitsStakeholderCompanyName")
    val orgUnitsStakeholderCompanyName: String? = null,
): java.io.Serializable
