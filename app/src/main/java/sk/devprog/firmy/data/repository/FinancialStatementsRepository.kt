package sk.devprog.firmy.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import sk.devprog.firmy.data.model.financial.FinancialIdentifierDetail
import sk.devprog.firmy.data.model.financial.FinancialIdentifiers
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

private const val BASE_URL = "https://www.registeruz.sk/cruz-public/api"

private const val IDENTIFIER_LIST_ENDPOINT = "$BASE_URL/uctovne-jednotky"
private const val IDENTIFIER_DETAIL_ENDPOINT = "$BASE_URL/uctovna-jednotka"

@Singleton
class FinancialStatementsRepository @Inject constructor(
    private val httpClient: HttpClient,
) {
    private val datePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    suspend fun searchIdentifiers(
        companyIdentifier: String,
        maxEntries: Int = 1000,
        changedFrom: LocalDate = LocalDate.ofYearDay(1900, 1),
    ): Result<FinancialIdentifiers> =
        runCatching<FinancialIdentifiers> {
            httpClient.get(IDENTIFIER_LIST_ENDPOINT) {
                parameter("ico", companyIdentifier)
                parameter("max-zaznamov", maxEntries)
                parameter("zmenene-od", changedFrom.format(datePattern))
            }.body()
        }.onFailure { Timber.e(it) }

    suspend fun getIdentifierDetail(id: Int): Result<FinancialIdentifierDetail> =
        runCatching<FinancialIdentifierDetail> {
            httpClient.get(IDENTIFIER_DETAIL_ENDPOINT) {
                parameter("id", id)
            }.body()
        }.onFailure { Timber.e(it) }
}
