package sk.devprog.firmy.ui.screen.search.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.devprog.firmy.R
import sk.devprog.firmy.ui.theme.AppTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectDateDialog(
    title: String,
    startDate: LocalDate? = null,
    endDate: LocalDate? = null,
    onUpdateDate: (LocalDate?, LocalDate?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDateRangePickerState(
        initialDisplayMode = DisplayMode.Input,
        initialSelectedStartDateMillis =
        startDate?.atStartOfDay()?.atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli() ?:
        (if (endDate != null) 0L else null), // End date without start date is not possible
        initialSelectedEndDateMillis =
        endDate?.atStartOfDay()?.atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
    )
    DatePickerDialog(onDismissRequest = onDismiss, confirmButton = {
        TextButton(
            onClick = {
                val start = datePickerState.selectedStartDateMillis?.let { millis ->
                    Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate()
                }

                val end = datePickerState.selectedEndDateMillis?.let { millis ->
                    Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate()
                }

                onUpdateDate(start, end)
            },
            enabled = datePickerState.selectedEndDateMillis != null ||
                    datePickerState.selectedStartDateMillis != null,
            content = {
                Text(
                    text = stringResource(id = R.string.search_dialog_date_select)
                )
            })
    }) {
        DateRangePicker(
            state = datePickerState,
            headline = { Text(text = title, modifier = Modifier.padding(horizontal = 16.dp)) },
            title = {}
        )
    }
}

@Preview
@Composable
private fun SelectDateDialogPreview() {
    AppTheme {
        SelectDateDialog(
            title = "Date",
            onUpdateDate = { _, _ -> },
            onDismiss = {},
        )
    }
}
