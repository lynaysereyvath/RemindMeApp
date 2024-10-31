package com.lynaysereyvath.remindme.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.lynaysereyvath.remindme.R
import com.lynaysereyvath.remindme.ui.theme.Typography
import kotlinx.coroutines.coroutineScope

@Composable
fun AppDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: RemindMeNavigationActions,
    closeDrawer: () -> Unit
) {

    ModalDrawerSheet(drawerState = drawerState, modifier = modifier) {

        Text(
            "RemindMe",
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            style = Typography.titleLarge,
            fontWeight = FontWeight.Medium
        )

        NavigationDrawerItem(
            label = { Text("Quotes") },
            onClick = {
                closeDrawer()
                navigationActions.navigateToQuote()
            },
            selected = currentRoute == RemindMeRoute.Quotes,
            modifier = Modifier,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.outline_lightbulb_24),
                    contentDescription = "lightbulb"
                )
            },
            colors = NavigationDrawerItemDefaults.colors(),
        )
        NavigationDrawerItem(
            label = { Text("Reminders") },
            onClick = {
                closeDrawer()
                navigationActions.navigateToReminders()
            },
            selected = currentRoute == RemindMeRoute.Reminders,
            modifier = Modifier,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Notifications, contentDescription = "reminder"
                )
            },
            colors = NavigationDrawerItemDefaults.colors(),
        )
        /**
        NavigationDrawerItem(
            label = { Text("Create new label") },
            onClick = {
                closeDrawer()
            },
            selected = currentRoute == RemindMeRoute.CreateNewLabel,
            modifier = Modifier,
            icon = {
                Icon(
                    Icons.Outlined.Add, "add"
                )
            },
            colors = NavigationDrawerItemDefaults.colors(),
        )
        NavigationDrawerItem(
            label = { Text("Archive") },
            onClick = {
                closeDrawer()
            },
            selected = currentRoute == RemindMeRoute.Archive,
            modifier = Modifier,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.outline_archive_24),
                    contentDescription = "archive"
                )
            },
            colors = NavigationDrawerItemDefaults.colors(),
        )
        NavigationDrawerItem(
            label = { Text("Trash") },
            onClick = {
                closeDrawer()
            },
            selected = currentRoute == RemindMeRoute.Trash,
            modifier = Modifier,
            icon = {
                Icon(
                    Icons.Outlined.Delete, "trash"
                )
            },
            colors = NavigationDrawerItemDefaults.colors(),
        )
        NavigationDrawerItem(
            label = { Text("Settings") },
            onClick = {
                closeDrawer()
            },
            selected = currentRoute == RemindMeRoute.Settings,
            modifier = Modifier,
            icon = {
                Icon(Icons.Outlined.Settings, "setting")
            },
            colors = NavigationDrawerItemDefaults.colors(),
        )
        NavigationDrawerItem(
            label = { Text("Help & feedback") },
            onClick = {
                closeDrawer()
            },
            selected = currentRoute == RemindMeRoute.HelpAndFeedback,
            modifier = Modifier,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.outline_help_outline_24),
                    contentDescription = "help"
                )
            },
            colors = NavigationDrawerItemDefaults.colors(),
        )
        */

    }
}

@Preview
@Composable
fun AppDrawerPreview() {
    AppDrawer(
        drawerState = rememberDrawerState(DrawerValue.Closed),
        currentRoute = RemindMeRoute.Quotes,
        navigationActions = RemindMeNavigationActions(rememberNavController()),
        closeDrawer = {}
    )
}