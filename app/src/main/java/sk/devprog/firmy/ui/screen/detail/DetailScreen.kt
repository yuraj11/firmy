package sk.devprog.firmy.ui.screen.detail

import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import sk.devprog.firmy.R
import sk.devprog.firmy.ui.common.widget.ErrorLayout
import sk.devprog.firmy.ui.common.widget.LoadingLayout
import sk.devprog.firmy.ui.screen.detail.DetailScreenViewModel.Effect
import sk.devprog.firmy.ui.screen.detail.DetailScreenViewModel.UiAction
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailItemUiModel
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailSectionUiModel
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailUiModel
import sk.devprog.firmy.ui.screen.detail.widget.ActivitiesInfoTab
import sk.devprog.firmy.ui.screen.detail.widget.BasicCompanyInfoTab
import sk.devprog.firmy.ui.screen.detail.widget.FinancesInfoTab
import sk.devprog.firmy.ui.screen.detail.widget.OtherInfoTab
import sk.devprog.firmy.ui.screen.detail.widget.PeopleInfoTab
import sk.devprog.firmy.ui.theme.AppTheme
import sk.devprog.firmy.util.toTextResource

@Composable
fun DetailScreen(
    onBackClicked: () -> Unit,
    viewModel: DetailScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is Effect.ShareDetails -> {
                    val textToShare = buildString {
                        viewModel.uiState.value.model?.allSections?.map { section ->
                            appendLine("[${section.title.asString(context)}]")
                            appendLine(section.validItems.joinToString("\n") { it.text })
                            appendLine()
                        }
                    }
                    context.shareText(textToShare)
                }
            }
        }
    }

    val state by viewModel.uiState.collectAsState()
    DetailScreenContent(
        uiState = state,
        onBackClicked = onBackClicked,
        onAddToFavoriteClicked = {
            viewModel.handleAction(UiAction.AddRemoveFavorite)
        },
        onShareClicked = {
            viewModel.handleAction(UiAction.Share)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DetailScreenContent(
    uiState: DetailScreenViewModel.UiState,
    onBackClicked: () -> Unit,
    onAddToFavoriteClicked: () -> Unit,
    onShareClicked: () -> Unit,
) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = uiState.title, maxLines = 2, overflow = TextOverflow.Ellipsis) },
            navigationIcon = {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.top_app_bar_back)
                    )
                }
            },
            actions = {
                IconButton(onClick = onShareClicked, enabled = uiState.model != null) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = stringResource(id = R.string.detail_share)
                    )
                }
                IconButton(onClick = onAddToFavoriteClicked, enabled = uiState.model != null) {
                    Icon(
                        imageVector = if (uiState.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.detail_remove_add_favorite)
                    )
                }
            }
        )
    }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            val model = uiState.model
            if (uiState.isLoading) {
                LoadingLayout()
            } else if (uiState.error != null) {
                ErrorLayout(error = uiState.error)
            } else if (model != null) {
                val coroutineScope = rememberCoroutineScope()
                val tabs = buildList {
                    add(TabItem.Basic)
                    if (model.activitiesTab.isNotEmpty()) {
                        add(TabItem.Activities)
                    }
                    if (model.peopleTab.isNotEmpty()) {
                        add(TabItem.People)
                    }
                    if (model.financesTab.isNotEmpty()) {
                        add(TabItem.Finances)
                    }
                    if (model.otherTab.isNotEmpty()) {
                        add(TabItem.Other)
                    }
                }
                val pagerState = rememberPagerState(pageCount = { tabs.size })

                TabRow(selectedTabIndex = pagerState.currentPage) {
                    tabs.forEachIndexed { index, item ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.iconRes),
                                    contentDescription = stringResource(id = item.textRes)
                                )
                            },
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(
                                        index
                                    )
                                }
                            }
                        )
                    }
                }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.Top
                ) {
                    when (tabs[it]) {
                        TabItem.Basic -> {
                            BasicCompanyInfoTab(
                                items = model.basicTab,
                                name = uiState.title,
                                coordinates = uiState.mapCoordinates
                            )
                        }
                        TabItem.Activities -> ActivitiesInfoTab(model.activitiesTab)
                        TabItem.People -> PeopleInfoTab(model.peopleTab)
                        TabItem.Finances -> FinancesInfoTab(model.financesTab)
                        TabItem.Other -> OtherInfoTab(model.otherTab)
                    }
                }
            }
        }
    }
}

private fun Context.shareText(text: String) {
    val sendIntent: Intent = Intent(Intent.ACTION_SEND)
        .putExtra(Intent.EXTRA_TEXT, text)
        .setType("text/plain")
    startActivity(Intent.createChooser(sendIntent, null))
}

private sealed class TabItem(
    @get:StringRes val textRes: Int,
    @get:DrawableRes val iconRes: Int,
) {
    data object Basic: TabItem(
        textRes = R.string.detail_tab_basic,
        iconRes = R.drawable.ic_outline_info
    )

    data object Activities: TabItem(
        textRes = R.string.detail_tab_activities,
        iconRes = R.drawable.ic_outline_work
    )

    data object People: TabItem(
        textRes = R.string.detail_tab_people,
        iconRes = R.drawable.ic_outline_people
    )

    data object Finances: TabItem(
        textRes = R.string.detail_tab_finances,
        iconRes = R.drawable.ic_outline_attach_money
    )

    data object Other: TabItem(
        textRes = R.string.detail_tab_other,
        iconRes = R.drawable.ic_outline_more_horiz
    )
}

@Preview
@Composable
private fun DetailScreenPreview() {
    val items = listOf(
        CompanyDetailSectionUiModel(
            "Title".toTextResource(),
            listOf(CompanyDetailItemUiModel(AnnotatedString("Test")))
        )
    )
    AppTheme {
        DetailScreenContent(
            uiState = DetailScreenViewModel.UiState(
                title = "Company",
                model = CompanyDetailUiModel(
                    id = 1,
                    basicTab = items,
                    activitiesTab = items,
                    peopleTab = items,
                    financesTab = items,
                    otherTab = items,
                )
            ),
            onBackClicked = {},
            onAddToFavoriteClicked = {},
            onShareClicked = {}
        )
    }
}
