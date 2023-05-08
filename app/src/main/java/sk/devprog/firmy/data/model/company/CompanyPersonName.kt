package sk.devprog.firmy.data.model.company

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyPersonName(
    @SerialName("formatedName")
    val formatedName: String? = null,

    @SerialName("givenNames")
    val givenNames: List<String>,

    @SerialName("familyNames")
    val familyNames: List<String>,

    @SerialName("givenFamilyNames")
    val givenFamilyNames: List<String>? = null,

    @SerialName("prefixes")
    val prefixes: List<CompanyCodeValue>? = null,

    @SerialName("postfixes")
    val postfixes: List<CompanyCodeValue>? = null,
)

fun CompanyPersonName.getFormattedName(): String =
    formatedName
        ?: buildString {
            prefixes?.let { prefixes ->
                append(prefixes.joinToString(" ") { it.value })
                append(" ")
            }
            append(givenNames.joinToString(" "))
            append(" ")
            append(familyNames.joinToString(" "))
            append(" ")
            postfixes?.let { postfixes ->
                append(postfixes.joinToString(", ") { it.value })
            }
        }
