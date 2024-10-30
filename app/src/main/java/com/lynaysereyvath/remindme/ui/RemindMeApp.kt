package com.lynaysereyvath.remindme.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lynaysereyvath.remindme.ui.theme.RemindMeTheme
import kotlinx.coroutines.launch

enum class RemindMeAppScreen(val title: String) {
    Home("home"),
    Add("add"),
    Schedule("schedule")
}

@Composable
fun RemindMeApp() {

    RemindMeTheme {
        val navController = rememberNavController()
        val coroutineScope = rememberCoroutineScope()
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val navigationActions = remember(navController) { RemindMeNavigationActions(navController) }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: RemindMeRoute.Quotes

        ModalNavigationDrawer(
            drawerContent = {
                AppDrawer(
                    drawerState = drawerState,
                    currentRoute = currentRoute,
                    navigationActions = navigationActions,
                    closeDrawer = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }
                )
            },
            drawerState = drawerState,
            gesturesEnabled = currentRoute == RemindMeRoute.Quotes || currentRoute == RemindMeRoute.Reminders
        ) {
            RemindMeNavGraph(navController = navController, navigationActions = navigationActions) {
                coroutineScope.launch {
                    drawerState.open()
                }
            }
        }

    }


}