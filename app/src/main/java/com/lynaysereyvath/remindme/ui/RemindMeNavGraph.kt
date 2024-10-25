package com.lynaysereyvath.remindme.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lynaysereyvath.remindme.ui.add.AddLayout
import com.lynaysereyvath.remindme.ui.home.HomeScreenLayout
import com.lynaysereyvath.remindme.ui.schedule.SetScheduleScreen

@Composable
fun RemindMeNavGraph(
    navController: NavHostController = rememberNavController(),
    navigationActions: RemindMeNavigationActions,
    openDrawer: () -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = RemindMeRoute.Quotes
    ) {
        composable(route = RemindMeRoute.Quotes) {
            HomeScreenLayout(
                navController = navController,
                remindMeNavigationActions = navigationActions,
                openDrawer = openDrawer
            )
        }
        composable(route = RemindMeRoute.Reminders) {
            SetScheduleScreen(navController = navController, openDrawer = openDrawer)
        }

        composable(route = RemindMeRoute.AddQuote) {
            AddLayout(
                navController = navController
            )
        }
    }
}