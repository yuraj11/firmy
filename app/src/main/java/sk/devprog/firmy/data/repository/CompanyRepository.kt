package sk.devprog.firmy.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import sk.devprog.firmy.data.model.company.CompanyDetail
import sk.devprog.firmy.data.model.company.CompanySearchParameters
import sk.devprog.firmy.data.model.company.CompanySearchResults
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

private const val BASE_URL = "https://api.statistics.sk/rpo/v1"

private const val SEARCH_ENDPOINT = "$BASE_URL/search"
private const val ENTITY_ENDPOINT = "$BASE_URL/entity"

/**
 * Provides search and company details.
 */
@Singleton
class CompanyRepository @Inject constructor(
    private val httpClient: HttpClient,
    private val json: Json
) {

    suspend fun searchCompany(
        parameters: CompanySearchParameters
    ): Result<CompanySearchResults> =
        runCatching<CompanySearchResults> {
            httpClient.get(SEARCH_ENDPOINT) {
                parameters.encodeToMap().forEach { (key, value) -> parameter(key, value) }
            }.body()
        }.onFailure { Timber.e(it) }

    suspend fun getDetail(
        id: Int,
        showHistoricalData: Boolean = false,
        showOrganizationUnits: Boolean = false
    ): Result<CompanyDetail> =
        runCatching<CompanyRepository, CompanyDetail> {
            httpClient.get("$ENTITY_ENDPOINT/$id") {
                parameter("showHistoricalData", showHistoricalData)
                parameter("showOrganizationUnits", showOrganizationUnits)
            }.body()
        }.onFailure { Timber.e(it) }

    private fun CompanySearchParameters.encodeToMap(): Map<String, String> =
        json.encodeToJsonElement(this).jsonObject.entries
            .filter { (_, value) -> value.jsonPrimitive.content.isNotBlank() }
            .associate { (key, value) -> key to value.jsonPrimitive.content }
}
