package com.lynaysereyvath.remindme.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lynaysereyvath.remindme.R
import com.lynaysereyvath.remindme.data.local.deleteKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(viewModel: HomeScreenViewModel, openDrawer: () -> Unit, goToSearch: () -> Unit) {

    var expanded by remember { mutableStateOf(false) }
//    LaunchedEffect(expanded) {
//        if (!expanded) {
//            viewModel.cancelAllSelections()
//        }
//    }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    TopAppBar(
        modifier = Modifier.clickable(onClick = goToSearch),
        title = {
            if (!viewModel.isItemsSelected) Text(
                "Search your quote",
                fontSize = 16.sp,
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
//            IconButton(
//                onClick = {},
//                modifier = Modifier.padding(horizontal = 10.dp)
//            ) {
//                Icon(
//                    painterResource(R.drawable.outline_keep_24), "filter",
//                )
//            }
//
//            IconButton(
//                onClick = {}
//            ) {
//                Icon(
//                    painterResource(R.drawable.outline_add_alert_24),
//                    "profile",
//                )
//            }
//            IconButton(
//                onClick = {
//                }
//            ) {
//                Icon(
//                    painterResource(R.drawable.outline_palette_24),
//                    "profile",
//                )
//            }
//            IconButton(
//                onClick = {}
//            ) {
//                Icon(
//                    painterResource(R.drawable.outline_label_24),
//                    "profile",
//                )
//            }
                Box {
                    IconButton(
                        onClick = {
                            expanded = true
                        }
                    ) {
                        Icon(
                            Icons.Outlined.MoreVert,
                            "more",
                        )
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = {
                        expanded = false
                        viewModel.cancelAllSelections()
                    }) {
//                    DropdownMenuItem(text = { Text("Archive") }, onClick = { expanded = false })
                        DropdownMenuItem(text = { Text("Delete") }, onClick = {
                            expanded = false
                            viewModel.delete() {
                                coroutineScope.launch(Dispatchers.IO) {
                                    context.deleteKeys(it)
                                }
                            }
                        })
//                    DropdownMenuItem(text = { Text("Make a copy") }, onClick = { expanded = false })
//                    DropdownMenuItem(text = { Text("Send") }, onClick = { expanded = false })
//                    DropdownMenuItem(
//                        text = { Text("Copy to Google Docs") },
//                        onClick = { expanded = false })
                    }
                }
            } else {
//            IconButton(
//                onClick = {},
//                modifier = Modifier.padding(horizontal = 10.dp)
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.outline_view_agenda_24), "filter",
//                )
//            }
//
//            IconButton(
//                onClick = {}
//            ) {
//                Icon(
//                    Icons.Outlined.Person,
//                    "profile",
//                    modifier = Modifier
//                        .background(Color.White, CircleShape)
//                        .clip(CircleShape)
//                )
//            }
            }
        }
    )
}