package sk.devprog.firmy.ui.screen.main.widget

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import sk.devprog.firmy.ui.navigation.BottomNavigation
import sk.devprog.firmy.ui.navigation.BottomNavigationItem
import sk.devprog.firmy.ui.navigation.toBottomNavigationItem
import sk.devprog.firmy.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBottomNavigation(
    navController: NavHostController,
    navigationItems: List<BottomNavigationItem>,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    if (LocalInspectionMode.current || navigationItems.any {
            navBackStackEntry.isActiveRoute(it.navigation.navigationRoute)
        }) {

        NavigationBar(modifier) {
            navigationItems.forEach { item ->
                val title = stringResource(id = item.navigation.titleResId)
                val selected = navBackStackEntry.isActiveRoute(item.navigation.navigationRoute)

                NavigationBarItem(
                    icon = {
                        BadgedBox(badge = {
                            if (item.badgeCount > 0) {
                                Badge { Text(item.badgeCount.toString()) }
                            }
                        }) {
                            Icon(
                                imageVector = if (selected) item.navigation.iconSelected else item.navigation.iconNormal,
                                contentDescription = title
                            )
                        }
                    },
                    label = { Text(text = title) },
                    selected = selected,
                    onClick = {
                        navController.navigate(item.navigation.navigationRoute) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

private fun NavBackStackEntry?.isActiveRoute(route: String): Boolean =
    this?.destination?.hierarchy?.any { it.route == route } == true

@Preview
@Composable
private fun MainBottomNavigationPreview() {
    AppTheme {
        MainBottomNavigation(
            navController = rememberNavController(),
            navigationItems = BottomNavigation.ALL_ITEMS.toBottomNavigationItem()
        )
    }
}
