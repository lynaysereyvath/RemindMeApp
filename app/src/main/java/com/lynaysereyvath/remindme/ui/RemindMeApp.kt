package com.lynaysereyvath.remindme.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class RemindMeAppScreen(val title: String)
{
    Home("home"),
    Add("add"),
    Schedule("schedule")
}

@Composable
fun RemindMeApp(navController: NavHostController = rememberNavController())
{
    RemindMeScreen(navController = navController)
}