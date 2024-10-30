package com.lynaysereyvath.remindme.ui

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object RemindMeRoute {
    const val Quotes = "quotes"
    const val Settings = "settings"
    const val Reminders = "reminders"
    const val CreateNewLabel = "createNewLabel"
    const val Archive = "archive"
    const val Trash = "trash"
    const val HelpAndFeedback = "helpAndFeedback"
    const val AddQuote = "AddQuote"
}

class RemindMeNavigationActions(navController: NavHostController) {
    val navigateToQuote = {
        navController.navigate(RemindMeRoute.Quotes) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
    val navigateToReminders = {
        navController.navigate(RemindMeRoute.Reminders) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToAddQuote: (id: Int?) -> Unit = { id ->
        navController.navigate(if (id != null) "${RemindMeRoute.AddQuote}/$id" else RemindMeRoute.AddQuote) {
            launchSingleTop = true
        }
    }
}