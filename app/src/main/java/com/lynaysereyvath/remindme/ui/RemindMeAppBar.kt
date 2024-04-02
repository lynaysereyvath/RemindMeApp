package com.lynaysereyvath.remindme.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindMeAppBar(
    canNavigateUp: Boolean,
    canSearch: Boolean,
    searchClicked: () -> Unit,
    onBack: () -> Unit
) {
    TopAppBar(
        title = { },
        actions = {
            if (!canNavigateUp) {
                if (canSearch) {
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
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Search Icon"
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp)
                    )
                } else {
                    IconButton(onClick = { searchClicked() }) {
                        Icon(Icons.Filled.Search, "Search")
                    }
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.MoreVert, "Search")
                }
            }
        },
        navigationIcon = {
            if (canNavigateUp) {
                IconButton(onClick = { onBack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "arrow back")
                }
            }
        }
    )
}
