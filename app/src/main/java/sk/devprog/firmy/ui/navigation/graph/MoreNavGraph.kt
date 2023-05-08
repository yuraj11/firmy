package sk.devprog.firmy.ui.navigation.graph

import androidx.compose.ui.platform.UriHandler
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import sk.devprog.firmy.ui.screen.licenses.LicensesScreen
import sk.devprog.firmy.ui.screen.more.MoreScreen

sealed class MoreNavigation(val route: String) {
    object More : MoreNavigation("more")
    object Licenses : MoreNavigation("licenses")
}

fun NavGraphBuilder.moreNavigation(navController: NavController, localUriHandler: UriHandler) {
    composable(MoreNavigation.More.route) {
        MoreScreen(
            onLicensesClick = { navController.navigate(MoreNavigation.Licenses.route) },
            onOpenLinkClick = { runCatching { localUriHandler.openUri(it) } }
        )
    }
    composable(MoreNavigation.Licenses.route) {
        LicensesScreen(onBackClicked = { navController.navigateUp() })
    }
}
