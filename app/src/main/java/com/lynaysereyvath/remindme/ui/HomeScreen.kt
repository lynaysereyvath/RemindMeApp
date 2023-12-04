package com.lynaysereyvath.remindme.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lynaysereyvath.remindme.ui.theme.RemindMeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenLayout(isSearchTextFieldVisible: Boolean)
{
    Scaffold(
        topBar = {
                 TopAppBar(
                     title = {  },
                     actions = {
                         if (isSearchTextFieldVisible)
                         {
                             OutlinedTextField(
                                 value = "Type f",
                                 onValueChange = { },
                                 singleLine = true,
                                 colors = TextFieldDefaults.outlinedTextFieldColors(
                                     containerColor = Color.Gray,
                                     focusedBorderColor = Color.Transparent,
                                     unfocusedBorderColor = Color.Transparent,
                                     errorBorderColor = Color.Transparent
                                 ),
                                 shape = MaterialTheme.shapes.small.copy(
                                     bottomEnd = CornerSize(50),
                                     bottomStart = CornerSize(50),
                                     topStart = CornerSize(50),
                                     topEnd = CornerSize(50)
                                 ),
                                 leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon")},
                                 modifier = Modifier.weight(1f).padding(start = 10.dp)
                             )
                         }
                         else
                         {
                             IconButton(onClick = { /*TODO*/ }) {
                                 Icon(Icons.Filled.Search, "Search")
                             }
                         }
                         IconButton(onClick = { /*TODO*/ }) {
                             Icon(Icons.Filled.MoreVert, "Search")
                         }
                     })
        },
        bottomBar = {
            BottomAppBar() {
                Column(modifier = Modifier.weight(1f)) {
                    Row {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Filled.DateRange, "Schedule")
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Filled.Settings, "Setting")
                        }
                    }
                }
                Column(modifier = Modifier.padding(horizontal = 5.dp)) {
                    Row {
                        FloatingActionButton(onClick = { /*TODO*/ }, elevation = FloatingActionButtonDefaults.elevation(0.dp)) {
                            Icon(Icons.Filled.Edit, "Add")
                        }
                    }
                }

            }
        },
        floatingActionButton = {
        },
        floatingActionButtonPosition = FabPosition.End,

    ) {

        LazyColumn(modifier = Modifier.padding(it))
        {

        }

    }
}

@Preview
@Composable
fun HomeScreenLayoutPreview()
{
    RemindMeTheme {
        HomeScreenLayout(true)
    }
}