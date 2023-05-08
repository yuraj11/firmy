package sk.devprog.firmy.ui.screen.search.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.flow.filter
import sk.devprog.firmy.R
import java.time.LocalDate

@Composable
fun SearchTextField(
    title: String,
    text: String,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    updateText: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        label = { Text(title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        readOnly = readOnly,
        interactionSource = interactionSource,
        singleLine = singleLine,
        trailingIcon = {
            AnimatedVisibility(
                visible = text.isNotEmpty(),
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                IconButton(
                    content = {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(id = R.string.textfield_clear)
                        )
                    },
                    onClick = { updateText("") }
                )
            }
        },
        onValueChange = { updateText(it) })
}

@Composable
fun SearchTextFieldItems(
    title: String,
    text: String,
    items: List<String>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    updateText: (String) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    SearchTextField(
        modifier = modifier,
        title = title,
        text = text,
        readOnly = true,
        singleLine = false,
        interactionSource = interactionSource,
        updateText = updateText
    )

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.filter { it is PressInteraction.Release }.collect {
            onClick()
            showDialog = true
        }
    }

    if (showDialog && items.isNotEmpty()) {
        SelectOptionDialog(
            title = title,
            itemList = items,
            onSelectOption = {
                updateText(it)
                showDialog = false
            },
            onDismiss = {
                showDialog = false
            })
    }
}

@Composable
fun SearchTextFieldDateRange(
    title: String,
    modifier: Modifier = Modifier,
    startDate: LocalDate? = null,
    endDate: LocalDate? = null,
    onUpdateDate: (LocalDate?, LocalDate?) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    SearchTextField(
        modifier = modifier,
        title = title,
        text = if (startDate != null || endDate != null) {
            startDate?.toString().orEmpty() + " <-> " + endDate?.toString().orEmpty()
        } else {
            ""
        },
        readOnly = true,
        singleLine = false,
        interactionSource = interactionSource,
        updateText = {
            if (it.isBlank()) {
                onUpdateDate(null, null)
            }
        }
    )

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.filter { it is PressInteraction.Release }.collect {
            showDialog = true
        }
    }

    if (showDialog) {
        SelectDateDialog(
            title = title,
            startDate = startDate,
            endDate = endDate,
            onDismiss = { showDialog = false },
            onUpdateDate = { start, end ->
                onUpdateDate(start, end)
                showDialog = false
            }
        )
    }
}
