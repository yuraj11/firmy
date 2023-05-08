package sk.devprog.firmy.ui.screen.detail.model.mapper

import androidx.compose.ui.text.AnnotatedString
import sk.devprog.firmy.R
import sk.devprog.firmy.data.model.company.CompanyDetail
import sk.devprog.firmy.data.model.company.getFormattedAddress
import sk.devprog.firmy.data.model.sortByDate
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailItemUiModel
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailSectionUiModel
import sk.devprog.firmy.util.TextResource
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun CompanyDetail.buildBasicUiSection(): List<CompanyDetailSectionUiModel> {
    return listOfNotNull(
        CompanyDetailSectionUiModel(
            title = TextResource.Id(R.string.detail_ico_label),
            allItems = identifiers.sortByDate().map {
                it.toUiModel(AnnotatedString(it.value))
            }
        ),
        CompanyDetailSectionUiModel(
            title = TextResource.Id(R.string.detail_name_label),
            allItems = fullNames.sortByDate().map {
                it.toUiModel(AnnotatedString(it.value))
            }
        ),
        CompanyDetailSectionUiModel(
            title = TextResource.Id(R.string.detail_legal_form_label),
            allItems = legalForms.sortByDate().map {
                it.toUiModel(AnnotatedString(it.value.value))
            }
        ),
        CompanyDetailSectionUiModel(
            title = TextResource.Id(R.string.detail_address_form_label),
            allItems = addresses.sortByDate().map {
                it.toUiModel(AnnotatedString(it.getFormattedAddress()))
            }
        ),
        CompanyDetailSectionUiModel(
            title = TextResource.Id(R.string.detail_established_label),
            allItems = listOf(
                CompanyDetailItemUiModel(
                    text = AnnotatedString(
                        establishment.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
                    )
                )
            )
        ),
        termination?.let {
            CompanyDetailSectionUiModel(
                title = TextResource.Id(R.string.detail_termination_label),
                allItems = listOf(
                    CompanyDetailItemUiModel(
                        text = AnnotatedString(it.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)))
                    )
                )
            )
        },
        CompanyDetailSectionUiModel(
            title = TextResource.Id(R.string.detail_source_register),
            allItems = listOf(
                CompanyDetailItemUiModel(
                    text = AnnotatedString(sourceRegister.value.value)
                )
            )
        ),
        CompanyDetailSectionUiModel(
            title = TextResource.Id(R.string.detail_register_office),
            allItems = sourceRegister.registrationOffices.sortByDate().map {
                it.toUiModel(AnnotatedString(it.value))
            }
        ),
        CompanyDetailSectionUiModel(
            title = TextResource.Id(R.string.detail_register_number),
            allItems = sourceRegister.registrationNumbers.sortByDate().map {
                it.toUiModel(AnnotatedString(it.value))
            }
        ),
        authorizations?.let {
            CompanyDetailSectionUiModel(
                title = TextResource.Id(R.string.detail_authorizations),
                allItems = it.sortByDate().map { dateValue ->
                    dateValue.toUiModel(AnnotatedString(dateValue.value))
                }
            )
        }
    )
}
