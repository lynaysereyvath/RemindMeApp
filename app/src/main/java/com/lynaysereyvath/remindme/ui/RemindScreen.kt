package com.lynaysereyvath.remindme.ui

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lynaysereyvath.remindme.ui.add.AddLayout
import com.lynaysereyvath.remindme.ui.home.HomeScreenLayout
import com.lynaysereyvath.remindme.ui.schedule.SetScheduleScreen
import com.lynaysereyvath.remindme.ui.theme.RemindMeTheme

@Composable
fun RemindMeScreen(
    navController: NavHostController
) {

    val enterTransition = slideInHorizontally { fullWidth: Int ->
        -fullWidth
    }
    val exitTransition = slideOutHorizontally { fullWidth: Int ->
        fullWidth
    }
    val popEnterTransition = slideInHorizontally { it }
    val popExitTransition = slideOutHorizontally { -it }



    NavHost(
        navController = navController,
        startDestination = RemindMeAppScreen.Home.name,
        modifier = Modifier
    )
    {

        composable(
            route = RemindMeAppScreen.Home.name,
            enterTransition = { enterTransition },
            exitTransition = { exitTransition },
            popEnterTransition = { popEnterTransition },
            popExitTransition = { popExitTransition }
        )
        {
            HomeScreenLayout(navController = navController)
        }
        composable(
            route = "${RemindMeAppScreen.Add.name}?id={id}",
            enterTransition = { enterTransition },
            exitTransition = { exitTransition },
            popEnterTransition = { slideInHorizontally { it } },
            popExitTransition = { slideOutHorizontally { -it } },
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
                defaultValue =  -1
            })
        )
        {
            AddLayout(navController = navController, it.arguments?.getInt("id") ?: -1)
        }
        composable(route = RemindMeAppScreen.Schedule.name,
            enterTransition = { enterTransition },
            exitTransition = { exitTransition },
            popEnterTransition = { slideInHorizontally { it } },
            popExitTransition = { slideOutHorizontally { -it } }) {
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