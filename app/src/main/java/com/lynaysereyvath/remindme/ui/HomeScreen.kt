package com.lynaysereyvath.remindme.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun HomeScreenLayout(modifier: Modifier = Modifier, onItemClicked: () -> Unit)
{

    val viewModel = hiltViewModel<HomeScreenViewModel>()

    LaunchedEffect(key1 = true, block = {
        viewModel.getQuoteList()
    })

    val quoteList by viewModel.quoteList.collectAsStateWithLifecycle()

    LazyColumn(modifier = modifier)
    {
        items(quoteList)
        {
            QuoteItemUI(name = it.author, message = it.message)
        }
    }
}

@Composable
@Preview
fun HomeScreenLayoutPreview()
{
    HomeScreenLayout(onItemClicked = { })
}