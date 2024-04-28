package com.lynaysereyvath.remindme.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.lynaysereyvath.remindme.ui.RemindMeAppScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenLayout(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onItemClicked: () -> Unit
) {

    val viewModel = hiltViewModel<HomeScreenViewModel>()

    LaunchedEffect(key1 = true, block = {
        viewModel.getQuoteList()
    })

    val quoteList by viewModel.quoteList.collectAsStateWithLifecycle()

    val keyWord by viewModel.searchKeyword.collectAsStateWithLifecycle()
    val onKeyWordEntered : (value: String) -> Unit = remember {
        return@remember viewModel::setSearchKeyWord
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                actions = {
                    OutlinedTextField(
                        value = keyWord,
                        onValueChange = { onKeyWordEntered(it) },
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.Transparent,
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

                }
            )
        },
        bottomBar = {
            BottomAppBar() {
                Column(modifier = Modifier.weight(1f)) {
                    Row {
                        IconButton(onClick = { navController.navigate(RemindMeAppScreen.Schedule.name) }) {
                            Icon(Icons.Filled.DateRange, "Schedule")
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Filled.Settings, "Setting")
                        }
                    }
                }
                Column(modifier = Modifier.padding(horizontal = 5.dp)) {
                    Row {
                        FloatingActionButton(
                            onClick = { navController.navigate(RemindMeAppScreen.Add.name) },
                            elevation = FloatingActionButtonDefaults.elevation(0.dp)
                        ) {
                            Icon(Icons.Filled.Edit, "Add")
                        }
                    }
                }

            }
        },
        floatingActionButtonPosition = FabPosition.End,

        ) {

        LazyColumn(modifier = modifier.padding(it))
        {
            items(quoteList)
            {
                QuoteItemUI(name = it.author, message = it.message)
            }
        }
    }
}
