package com.lynaysereyvath.remindme.ui

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lynaysereyvath.remindme.ui.add.AddLayout
import com.lynaysereyvath.remindme.ui.home.HomeScreenLayout
import com.lynaysereyvath.remindme.ui.schedule.SetScheduleScreen
import com.lynaysereyvath.remindme.ui.search.SearchScreen

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
        composable(
            route = RemindMeRoute.Quotes,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popExitTransition = { fadeOut() }) {
            HomeScreenLayout(
                navController = navController,
                remindMeNavigationActions = navigationActions,
                openDrawer = openDrawer
            )
        }
        composable(
            route = RemindMeRoute.Reminders,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popExitTransition = { fadeOut() }) {
            SetScheduleScreen(navController = navController, openDrawer = openDrawer)
        }

        composable(
            route = "${RemindMeRoute.AddQuote}?id={id}", arguments = listOf(navArgument("id") {
                type = NavType.LongType
                defaultValue = -1
            }),
            enterTransition = { fadeIn() },
            popExitTransition = { fadeOut() }) {
            AddLayout(navController = navController)
        }

        composable(
            route = RemindMeRoute.Search,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popExitTransition = { fadeOut() }) {
            SearchScreen(navController, navigationActions)
        }
    }
}