package sk.devprog.firmy.ui.screen.main

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import sk.devprog.firmy.ui.theme.AppTheme

class MainScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun checkBottomNavigationIsShown() {
        composeTestRule.setContent {
            AppTheme {
                MainScreen()
            }
        }
        composeTestRule.onNodeWithText("Search").assertIsDisplayed()
    }
}