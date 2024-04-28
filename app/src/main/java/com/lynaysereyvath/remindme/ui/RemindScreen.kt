package com.lynaysereyvath.remindme.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lynaysereyvath.remindme.ui.add.AddLayout
import com.lynaysereyvath.remindme.ui.home.HomeScreenLayout
import com.lynaysereyvath.remindme.ui.schedule.SetScheduleScreen
import com.lynaysereyvath.remindme.ui.theme.RemindMeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindMeScreen(
    navController: NavHostController
) {

    val backEntry by navController.currentBackStackEntryAsState()
    val currentScreen = RemindMeAppScreen.valueOf(
        backEntry?.destination?.route ?: RemindMeAppScreen.Home.name
    )


    NavHost(
        navController = navController,
        startDestination = RemindMeAppScreen.Home.name,
        modifier = Modifier
    )
    {
        composable(route = RemindMeAppScreen.Home.name)
        {
            HomeScreenLayout(onItemClicked = { /*TODO*/ }, navController = navController)
        }
        composable(route = RemindMeAppScreen.Add.name)
        {
            AddLayout( navController = navController)
        }
        composable(route = RemindMeAppScreen.Schedule.name) {
            SetScheduleScreen(navController = navController)
        }
    }

}


@Preview
@Composable
fun RemindMeScreenPreview() {
    RemindMeTheme {
        Surface {
            RemindMeScreen(navController = rememberNavController())
        }
    }
}