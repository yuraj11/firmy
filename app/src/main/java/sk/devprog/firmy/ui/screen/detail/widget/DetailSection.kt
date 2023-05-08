package sk.devprog.firmy.ui.screen.detail.widget

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import sk.devprog.firmy.R
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailItemUiModel
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailSectionUiModel
import sk.devprog.firmy.ui.theme.AppTheme
import sk.devprog.firmy.ui.theme.Typography
import sk.devprog.firmy.util.toTextResource

@Composable
fun DetailSection(
    section: CompanyDetailSectionUiModel,
    modifier: Modifier = Modifier,
    spacingBetweenItems: Dp = 2.dp
) {
    val clipboardManager = LocalClipboardManager.current
    var expanded by remember { mutableStateOf(false) }
    var dropdownVisible by remember { mutableStateOf(false) }
    val hasOldItems = remember { section.allItems.any { it.validTo != null } }

    Box(modifier = modifier
        .fillMaxWidth()
        .clickable(onClickLabel = stringResource(id = R.string.detail_section_more_details)) {
            expanded = !expanded
        }
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp)) {
            Text(
                text = section.title.asString(),
                style = Typography.labelSmall,
                fontWeight = FontWeight.Black
            )

            AnimatedContent(
                targetState = expanded,
                transitionSpec = { fadeIn() togetherWith fadeOut() }) {
                Column(modifier = Modifier.weight(1f)) {
                    if (it) {
                        section.allItems.forEach { item ->
                            Text(text = item.text)
                            Text(
                                text = item.validFrom.orEmpty() + item.validTo?.let { " - $it" }
                                    .orEmpty(),
                                style = Typography.labelSmall,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = (spacingBetweenItems * 2))
                            )
                        }
                    } else {
                        section.validItems.forEach { item ->
                            Text(
                                text = item.text,
                                modifier = Modifier.padding(bottom = spacingBetweenItems)
                            )
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.TopEnd),
            visible = hasOldItems && !expanded,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_outline_history),
                contentDescription = null,
                tint = Color.Gray
            )
        }
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.TopEnd),
            visible = expanded,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.clickable(onClickLabel = stringResource(id = R.string.detail_section_actions)) {
                    dropdownVisible = true
                }
            )
            DropdownMenu(
                expanded = dropdownVisible,
                onDismissRequest = { dropdownVisible = false }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.detail_section_copy_latest)) },
                    onClick = {
                        clipboardManager.setText(AnnotatedString(section.validItems.joinToString("\n") { it.text }))
                        dropdownVisible = false
                    })
                DropdownMenuItem(
                    text = { Text(stringResource(id = R.string.detail_section_copy_all)) },
                    onClick = {
                        clipboardManager.setText(AnnotatedString(section.allItems.joinToString("\n") { it.text }))
                        dropdownVisible = false
                    })
            }
        }
    }
}

@Preview
@Composable
private fun DetailSectionPreview() {
    AppTheme {
        DetailSection(
            CompanyDetailSectionUiModel(
                title = "Title".toTextResource(),
                allItems = listOf(
                    CompanyDetailItemUiModel(AnnotatedString("A"), "AA"),
                    CompanyDetailItemUiModel(AnnotatedString("B"), "BB"),
                )
            )
        )
    }
}
