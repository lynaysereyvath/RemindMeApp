package com.lynaysereyvath.remindme.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lynaysereyvath.remindme.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(viewModel: HomeScreenViewModel, openDrawer: () -> Unit) {
    TopAppBar(title = {
        if (!viewModel.isItemsSelected) Text(
            "Search your quote",
            fontSize = 16.sp
        )
    }, navigationIcon = {
        if (viewModel.isItemsSelected) {
            IconButton(
                onClick = viewModel.cancelAllSelections
            ) {
                Icon(Icons.Outlined.Close, "cancel selections")
            }
        } else {
            IconButton(
                onClick = openDrawer
            ) {
                Icon(Icons.Outlined.Menu, "menu")
            }
        }


    }, actions = {

        if (viewModel.isItemsSelected) {
            IconButton(
                onClick = {},
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Icon(
                    painterResource(R.drawable.outline_keep_24), "filter",
                )
            }

            IconButton(
                onClick = {}
            ) {
                Icon(
                    painterResource(R.drawable.outline_add_alert_24),
                    "profile",
                )
            }
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painterResource(R.drawable.outline_palette_24),
                    "profile",
                )
            }
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painterResource(R.drawable.outline_label_24),
                    "profile",
                )
            }
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painterResource(R.drawable.outline_add_alert_24),
                    "profile",
                )
            }
        } else {
            IconButton(
                onClick = {},
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_view_agenda_24), "filter",
                )
            }

            IconButton(
                onClick = {}
            ) {
                Icon(
                    Icons.Outlined.Person,
                    "profile",
                    modifier = Modifier
                        .background(Color.White, CircleShape)
                        .clip(CircleShape)
                )
            }
        }
    }
    )
}