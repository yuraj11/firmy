package sk.devprog.firmy.ui.screen.detail.model.mapper

import androidx.compose.ui.text.AnnotatedString
import sk.devprog.firmy.R
import sk.devprog.firmy.data.model.company.CompanyDetail
import sk.devprog.firmy.data.model.sortByDate
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailItemUiModel
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailSectionUiModel
import sk.devprog.firmy.util.TextResource

fun CompanyDetail.buildActivitiesUiSection(): List<CompanyDetailSectionUiModel> {
    return listOfNotNull(
        statisticalCodes?.mainActivity?.value?.let { mainActivity ->
            CompanyDetailSectionUiModel(
                title = TextResource.Id(R.string.detail_main_activity_label),
                allItems = listOf(
                    CompanyDetailItemUiModel(
                        text = AnnotatedString(mainActivity)
                    )
                )
            )
        },
        statisticalCodes?.esa2010?.value?.let { esa ->
            CompanyDetailSectionUiModel(
                title = TextResource.Id(R.string.detail_esa2010_label),
                allItems = listOf(
                    CompanyDetailItemUiModel(
                        text = AnnotatedString(esa)
                    )
                )
            )
        },
        activities?.let { activities ->
            CompanyDetailSectionUiModel(
                title = TextResource.Id(R.string.detail_activities_label),
                allItems = activities.sortByDate().map { dateValue ->
                    dateValue.toUiModel(AnnotatedString("- ${dateValue.economicActivityDescription}"))
                }
            )
        },
    )
}
