package com.lynaysereyvath.remindme.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lynaysereyvath.remindme.R
import com.lynaysereyvath.remindme.ui.RemindMeAppScreen
import com.lynaysereyvath.remindme.ui.RemindMeNavigationActions
import com.lynaysereyvath.remindme.ui.theme.Surface


@Composable
fun HomeScreenLayout(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    remindMeNavigationActions: RemindMeNavigationActions,
    openDrawer: () -> Unit,
) {

    val viewModel = hiltViewModel<HomeScreenViewModel>()

    LaunchedEffect(key1 = true, block = {
        viewModel.getQuoteList()
    })


    Scaffold(
        modifier = Modifier
            .background(Color.Blue),
        topBar = {
            HomeTopAppBar(viewModel, openDrawer)
        },
        bottomBar = {
            BottomAppBar(actions = {
                /**
                IconButton(onClick = { }) {
                    Icon(painterResource(R.drawable.outline_check_box_24), "Schedule")
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(R.drawable.outline_brush_24), "Setting")
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(R.drawable.outline_mic_24), "Setting")
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painterResource(R.drawable.outline_photo_24), "Setting")
                }
                */
            }, floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        remindMeNavigationActions.navigateToAddQuote(null)
                    },
                    elevation = FloatingActionButtonDefaults.elevation(0.dp)
                ) {
                    Icon(Icons.Filled.Edit, "Add")
                }
            })
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { padding ->

        LazyColumn(modifier = modifier.padding(padding))
        {
            items(viewModel.quoteList, key = { it.quote.id }) {
                Log.i("HomeScreen", it.quote.id.toString())
                QuoteItemUI(
                    it,
                    isSelectableOnClick = viewModel.isSelectableOnClick,
                    onSelectedStateChanged = viewModel.toggleSelection,
                    onClicked = { id ->
                        remindMeNavigationActions.navigateToAddQuote(id)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
//    HomeScreenLayout(navController = rememberNavController()) { }
}