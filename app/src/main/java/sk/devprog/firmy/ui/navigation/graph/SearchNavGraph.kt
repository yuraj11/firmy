package sk.devprog.firmy.ui.navigation.graph

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import sk.devprog.firmy.ui.navigation.BottomNavigationItem
import sk.devprog.firmy.ui.screen.detail.DetailScreen
import sk.devprog.firmy.ui.screen.search.SearchScreen

sealed class SearchNavigation(val route: String) {
    object Search : SearchNavigation("search")

    object Detail : SearchNavigation("detail/{id}?title={title}") {
        val args = listOf(
            navArgument("id") {
                type = NavType.IntType
            },
            navArgument("title") {
                type = NavType.StringType
                defaultValue = ""
            }
        )

        fun createRoute(id: Int, title: String): String = "detail/$id?title=${Uri.encode(title)}"
    }
}

fun NavGraphBuilder.searchNavigation(
    navController: NavController,
    updateBottomNavigationBadge: (BottomNavigationItem) -> Unit,
) {
    composable(SearchNavigation.Search.route) {
        SearchScreen(
            onItemClick = { id, title ->
                navController.navigate(SearchNavigation.Detail.createRoute(id, title))
            },
            updateBottomNavigationBadge = updateBottomNavigationBadge
        )
    }

    composable(SearchNavigation.Detail.route, SearchNavigation.Detail.args) {
        DetailScreen(onBackClicked = { navController.navigateUp() })
    }
}
