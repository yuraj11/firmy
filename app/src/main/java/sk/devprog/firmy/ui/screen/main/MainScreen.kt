package sk.devprog.firmy.ui.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import sk.devprog.firmy.ui.navigation.BottomNavigation
import sk.devprog.firmy.ui.navigation.toBottomNavigationItem
import sk.devprog.firmy.ui.screen.main.widget.MainBottomNavigation
import sk.devprog.firmy.ui.screen.main.widget.MainNavigator
import sk.devprog.firmy.ui.theme.AppTheme

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val bottomItems =
        remember { mutableStateOf(BottomNavigation.ALL_ITEMS.toBottomNavigationItem()) }

    Scaffold(bottomBar = {
        MainBottomNavigation(navController, bottomItems.value)
    }) { padding ->
        MainNavigator(
            navController = navController,
            modifier = Modifier.padding(padding),
            updateBottomNavigationBadge = { update ->
                bottomItems.value = bottomItems.value.map { currentItem ->
                    if (currentItem.navigation == update.navigation) {
                        currentItem.copy(badgeCount = update.badgeCount)
                    } else {
                        currentItem
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}
