package sk.devprog.firmy.ui.screen.main.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import sk.devprog.firmy.ui.navigation.BottomNavigationItem
import sk.devprog.firmy.ui.navigation.graph.SearchNavigation
import sk.devprog.firmy.ui.navigation.graph.favoritesNavigation
import sk.devprog.firmy.ui.navigation.graph.moreNavigation
import sk.devprog.firmy.ui.navigation.graph.searchNavigation

@Composable
fun MainNavigator(
    navController: NavHostController,
    updateBottomNavigationBadge: (BottomNavigationItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val localUriHandler = LocalUriHandler.current

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SearchNavigation.Search.route,
        builder = {
            searchNavigation(navController, updateBottomNavigationBadge)
            favoritesNavigation(navController)
            moreNavigation(navController, localUriHandler)
        })
}
