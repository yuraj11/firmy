package sk.devprog.firmy.ui.screen.detail.model.mapper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import sk.devprog.firmy.R
import sk.devprog.firmy.data.model.company.CompanyDetail
import sk.devprog.firmy.data.model.company.getFormattedName
import sk.devprog.firmy.data.model.financial.FinancialIdentifierDetail
import sk.devprog.firmy.data.model.sortByDate
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailItemUiModel
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailSectionUiModel
import sk.devprog.firmy.util.TextResource

fun CompanyDetail.buildFinancesUiSection(): List<CompanyDetailSectionUiModel> {
    return listOfNotNull(
        equities?.let {
            CompanyDetailSectionUiModel(
                title = TextResource.Id(R.string.detail_equities),
                allItems = it.sortByDate().map { equity ->
                    equity.toUiModel(
                        buildAnnotatedString {
                            val currency = equity.currency
                            // TODO: Missing translations
                            if (equity.valuePaid != null) {
                                append("Paid: " + equity.valuePaid.toBigDecimal().toString() + " " + currency.code)
                            } else if (equity.value != null) {
                                append("Base: " + equity.value.toBigDecimal().toString() + " " + currency.code)
                            } else if (equity.valueApproved != null) {
                                append("Approved: " + equity.valueApproved.toBigDecimal().toString() + " " + currency.code)
                            }
                        }
                    )
                }
            )

        },
        deposits?.let {
            CompanyDetailSectionUiModel(
                title = TextResource.Id(R.string.detail_deposits),
                allItems = it.sortByDate().map { deposit ->
                    deposit.toUiModel(
                        buildAnnotatedString {
                            deposit.type?.let { type ->
                                withStyle(
                                    SpanStyle(
                                        fontStyle = FontStyle.Italic,
                                        color = Color.Gray,
                                    )
                                ) {
                                    appendLine(type)
                                }
                            }

                            appendLine(deposit.fullName ?: deposit.personName?.getFormattedName())
                            append(deposit.amount.toBigDecimal().toString() + " " + deposit.currency.code)
                        }
                    )
                }
            )
        }
    )
}

fun FinancialIdentifierDetail.buildFinancesExtraSection(): List<CompanyDetailSectionUiModel> {
    return listOfNotNull(
        CompanyDetailSectionUiModel(
            title = TextResource.Id(R.string.detail_dic),
            allItems = listOf(CompanyDetailItemUiModel(AnnotatedString(dic)))
        ),
        idFinancialStatements?.let {
            CompanyDetailSectionUiModel(
                title = TextResource.Id(R.string.detail_financial_statements),
                allItems = listOf(CompanyDetailItemUiModel(AnnotatedString(it.size.toString())))
            )
        }
    )
}
