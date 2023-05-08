package sk.devprog.firmy.ui.screen.detail.model.mapper

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import sk.devprog.firmy.R
import sk.devprog.firmy.data.model.company.CompanyDetail
import sk.devprog.firmy.data.model.company.getFormattedAddress
import sk.devprog.firmy.data.model.sortByDate
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailSectionUiModel
import sk.devprog.firmy.util.TextResource

fun CompanyDetail.buildOtherUiSection(): List<CompanyDetailSectionUiModel> {
    return listOfNotNull(
        otherLegalFacts?.let {
            CompanyDetailSectionUiModel(
                title = TextResource.Id(R.string.detail_other_legal),
                allItems = it.sortByDate().map { fact ->
                    fact.toUiModel(AnnotatedString("- " + fact.value))
                }
            )
        },
        predecessors?.let {
            CompanyDetailSectionUiModel(
                title = TextResource.Id(R.string.detail_predecessors),
                allItems = it.sortByDate().map { predecessor ->
                    predecessor.toUiModel(buildAnnotatedString {
                        appendLine("- " + predecessor.fullName)
                        append(predecessor.address.getFormattedAddress())
                    })
                }
            )
        },
        successors?.let {
            CompanyDetailSectionUiModel(
                title = TextResource.Id(R.string.detail_successors),
                allItems = it.sortByDate().map { successors ->
                    successors.toUiModel(buildAnnotatedString {
                        appendLine("- " + successors.fullName)
                        append(successors.address.getFormattedAddress())
                    })
                }
            )
        }
    )
}
