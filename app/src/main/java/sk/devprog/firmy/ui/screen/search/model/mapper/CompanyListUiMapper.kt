package sk.devprog.firmy.ui.screen.search.model.mapper

import sk.devprog.firmy.R
import sk.devprog.firmy.data.model.company.CompanyAddress
import sk.devprog.firmy.data.model.company.CompanyDetail
import sk.devprog.firmy.data.model.company.CompanySearchResult
import sk.devprog.firmy.data.model.company.CompanyTimedValue
import sk.devprog.firmy.data.model.company.getFormattedAddress
import sk.devprog.firmy.data.model.sortByDate
import sk.devprog.firmy.ui.screen.search.model.CompanyListUiModel
import sk.devprog.firmy.util.TextResource
import sk.devprog.firmy.util.toTextResource
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun CompanySearchResult.toListUiModel(): CompanyListUiModel =
    createCompanyListUiModel(id, fullNames, addresses, identifiers, establishment, termination)

fun CompanyDetail.toListUiModel(): CompanyListUiModel =
    createCompanyListUiModel(id, fullNames, addresses, identifiers, establishment, termination)

private fun createCompanyListUiModel(
    id: Int,
    fullNames: List<CompanyTimedValue>,
    addresses: List<CompanyAddress>,
    identifiers: List<CompanyTimedValue>,
    establishment: LocalDate,
    termination: LocalDate? = null
): CompanyListUiModel {
    val fullNamesSorted = fullNames.sortByDate()
    val name = fullNamesSorted.sortByDate().first()
    val establishmentPeriod = if (termination != null) {
        Period.between(establishment, termination)
    } else {
        Period.between(establishment, LocalDate.now())
    }

    return CompanyListUiModel(
        id = id,
        companyName = name.value.toTextResource(),
        oldCompanyName = if (fullNamesSorted.size > 1) fullNamesSorted[1].value.toTextResource() else null,
        address = addresses.sortByDate().first().getFormattedAddress().toTextResource(),
        identifierNumber = TextResource.Id(
            R.string.company_identifier_line,
            identifiers.sortByDate().first().value
        ),
        establishment =
        if (termination != null) {
            TextResource.Id(
                R.string.company_establishment_simple_line,
                establishment.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
            )
        } else {
            TextResource.Id(
                R.string.company_establishment_line,
                establishment.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
                TextResource.Plural(
                    R.plurals.period_year_format,
                    establishmentPeriod.years,
                    establishmentPeriod.years
                ),
                TextResource.Plural(
                    R.plurals.period_month_format,
                    establishmentPeriod.months,
                    establishmentPeriod.months
                )
            )
        },
        termination = termination?.let {
            TextResource.Id(
                R.string.company_termination_line,
                termination.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
                TextResource.Plural(
                    R.plurals.period_year_format,
                    establishmentPeriod.years,
                    establishmentPeriod.years
                ),
                TextResource.Plural(
                    R.plurals.period_month_format,
                    establishmentPeriod.months,
                    establishmentPeriod.months
                )
            )
        }
    )
}
