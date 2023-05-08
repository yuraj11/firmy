package sk.devprog.firmy.ui.screen.detail.model.mapper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import sk.devprog.firmy.R
import sk.devprog.firmy.data.model.company.CompanyDetail
import sk.devprog.firmy.data.model.company.getFormattedAddress
import sk.devprog.firmy.data.model.company.getFormattedName
import sk.devprog.firmy.data.model.sortByDate
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailSectionUiModel
import sk.devprog.firmy.util.TextResource

fun CompanyDetail.buildPeopleUiSection(): List<CompanyDetailSectionUiModel> {
    return listOfNotNull(
        stakeholders?.let {
            CompanyDetailSectionUiModel(
                title = TextResource.Id(R.string.detail_stakeholders),
                allItems = it.sortByDate().map { stakeholder ->
                    stakeholder.toUiModel(
                        buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontStyle = FontStyle.Italic,
                                    color = Color.Gray,
                                )
                            ) {
                                appendLine(stakeholder.stakeholderType.value)
                            }
                            appendLine(stakeholder.fullName ?: stakeholder.personName?.getFormattedName())
                            append(stakeholder.address.getFormattedAddress())
                        }
                    )
                }
            )
        },
        statutoryBodies?.let {
            CompanyDetailSectionUiModel(
                title = TextResource.Id(R.string.detail_statutory_bodies),
                allItems = it.sortByDate().map { statutoryBody ->
                    statutoryBody.toUiModel(
                        buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontStyle = FontStyle.Italic,
                                    color = Color.Gray,
                                )
                            ) {
                                appendLine(statutoryBody.stakeholderType.value)
                            }
                            appendLine(statutoryBody.fullName ?: statutoryBody.personName?.getFormattedName())
                            append(statutoryBody.address.getFormattedAddress())
                        }
                    )
                }
            )
        }
    )
}