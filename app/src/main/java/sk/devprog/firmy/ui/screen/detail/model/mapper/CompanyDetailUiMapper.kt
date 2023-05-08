package sk.devprog.firmy.ui.screen.detail.model.mapper

import androidx.compose.ui.text.AnnotatedString
import sk.devprog.firmy.data.model.TimeRangeData
import sk.devprog.firmy.data.model.company.CompanyDetail
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailItemUiModel
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailUiModel
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun CompanyDetail.toUiModel(): CompanyDetailUiModel {
    return CompanyDetailUiModel(
        id = id,
        basicTab = buildBasicUiSection(),
        activitiesTab = buildActivitiesUiSection(),
        peopleTab = buildPeopleUiSection(),
        financesTab = buildFinancesUiSection(),
        otherTab = buildOtherUiSection(),
    )
}

fun TimeRangeData.toUiModel(text: AnnotatedString): CompanyDetailItemUiModel {
    val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
    return CompanyDetailItemUiModel(
        text,
        validFrom.format(dateFormatter),
        validTo?.format(dateFormatter)
    )
}
