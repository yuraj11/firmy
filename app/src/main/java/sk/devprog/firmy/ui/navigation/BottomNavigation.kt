package sk.devprog.firmy.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import sk.devprog.firmy.R
import sk.devprog.firmy.ui.navigation.graph.FavoritesNavigation
import sk.devprog.firmy.ui.navigation.graph.MoreNavigation
import sk.devprog.firmy.ui.navigation.graph.SearchNavigation

sealed class BottomNavigation(
    val navigationRoute: String,
    @StringRes val titleResId: Int,
    val iconNormal: ImageVector,
    val iconSelected: ImageVector
) {
    data object Search : BottomNavigation(
        navigationRoute = SearchNavigation.Search.route,
        titleResId = R.string.navigation_search,
        iconNormal = Icons.Outlined.Search,
        iconSelected = Icons.Filled.Search,
    )

    data object Favorites : BottomNavigation(
        navigationRoute = FavoritesNavigation.Favorites.route,
        titleResId = R.string.navigation_favorites,
        iconNormal = Icons.Outlined.FavoriteBorder,
        iconSelected = Icons.Filled.Favorite
    )

    data object About : BottomNavigation(
        navigationRoute = MoreNavigation.More.route,
        titleResId = R.string.navigation_more,
        iconNormal = Icons.Outlined.Menu,
        iconSelected = Icons.Filled.Menu
    )

    companion object {
        val ALL_ITEMS = listOf(Search, Favorites, About)
    }
}

fun List<BottomNavigation>.toBottomNavigationItem() =
    map { BottomNavigationItem(it, 0) }

data class BottomNavigationItem(
    val navigation: BottomNavigation,
    val badgeCount: Int
)
