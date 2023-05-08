package sk.devprog.firmy.data.model.financial

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FinancialIdentifiers(
    @SerialName("id")
    val id: List<Int>,

    @SerialName("existujeDalsieId")
    val hasNextId: Boolean,
)
