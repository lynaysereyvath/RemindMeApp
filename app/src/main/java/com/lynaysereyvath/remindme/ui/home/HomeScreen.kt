package com.lynaysereyvath.remindme.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.lynaysereyvath.remindme.R
import com.lynaysereyvath.remindme.ui.RemindMeAppScreen
import com.lynaysereyvath.remindme.ui.RemindMeNavigationActions


@OptIn(ExperimentalMaterial3Api::class)
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

    val quoteList by viewModel.quoteList.collectAsStateWithLifecycle()

    val keyWord by viewModel.searchKeyword.collectAsStateWithLifecycle()
    val onKeyWordEntered: (value: String) -> Unit = remember {
        return@remember viewModel::setSearchKeyWord
    }


    Scaffold(
        modifier = Modifier.safeContentPadding(),
        topBar = {
            TopAppBar(title = { Text("Search your quote", fontSize = 16.sp) }, navigationIcon = {
                IconButton(
                    onClick = openDrawer
                ) {
                    Icon(Icons.Outlined.Menu, "Search")
                }

            }, actions = {

                IconButton(
                    onClick = openDrawer,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_view_agenda_24), "filter",
                    )
                }

                IconButton(
                    onClick = openDrawer
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
            )
        },
        bottomBar = {
            BottomAppBar(actions = {
                IconButton(onClick = { navController.navigate(RemindMeAppScreen.Schedule.name) }) {
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
            }, floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        remindMeNavigationActions.navigateToAddQuote()
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
            items(quoteList)
            { quote ->
                QuoteItemUI(quote) {
//                    navController.navigate("${RemindMeAppScreen.Add.name}?id=${quote.id}")
                }
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
//    HomeScreenLayout(navController = rememberNavController()) { }
}