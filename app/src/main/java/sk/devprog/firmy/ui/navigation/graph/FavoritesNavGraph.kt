package sk.devprog.firmy.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import sk.devprog.firmy.ui.screen.favorites.FavoritesScreen

sealed class FavoritesNavigation(val route: String) {
    object Favorites : FavoritesNavigation("favorites")
}

fun NavGraphBuilder.favoritesNavigation(navController: NavController) {
    composable(FavoritesNavigation.Favorites.route) {
        FavoritesScreen(onItemClick = { id, title ->
            navController.navigate(SearchNavigation.Detail.createRoute(id, title))
        })
    }
}
