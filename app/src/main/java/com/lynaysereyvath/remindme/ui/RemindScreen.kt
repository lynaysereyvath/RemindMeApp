package com.lynaysereyvath.remindme.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lynaysereyvath.remindme.ui.theme.RemindMeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindMeScreen(
    navController: NavHostController
) {
    var canSearch: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    val backEntry by navController.currentBackStackEntryAsState()
    val currentScreen = RemindMeAppScreen.valueOf(
        backEntry?.destination?.route ?: RemindMeAppScreen.Home.name
    )

    Scaffold(
        topBar = {
            RemindMeAppBar(
                canNavigateUp = navController.previousBackStackEntry != null,
                canSearch = canSearch,
                searchClicked = {
                    canSearch = true
                },
            ) {
                navController.popBackStack(RemindMeAppScreen.Home.name, inclusive = false)
            }
        },
        bottomBar = {
            if (navController.previousBackStackEntry == null)
            {
                RemindMeBottomAppBar(){
                    navController.navigate(RemindMeAppScreen.Add.name)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,

        ) {

        NavHost(navController = navController, startDestination = RemindMeAppScreen.Home.name, modifier = Modifier.padding(it))
        {
            composable(route = RemindMeAppScreen.Home.name)
            {
                HomeScreenLayout(onItemClicked = { /*TODO*/ })
            }
            composable(route = RemindMeAppScreen.Add.name)
            {
                AddLayout(
                    onFinished = {
                        navController.popBackStack(RemindMeAppScreen.Home.name, inclusive = false)
                    },
                    onBack = {
                        navController.popBackStack(RemindMeAppScreen.Home.name, inclusive = false)
                    })
            }
        }

    }
}


@Preview
@Composable
fun RemindMeScreenPreview()
{
    RemindMeTheme {
        Surface {
            RemindMeScreen(navController = rememberNavController())
        }
    }
}