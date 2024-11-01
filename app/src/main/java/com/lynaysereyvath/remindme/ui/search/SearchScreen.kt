package com.lynaysereyvath.remindme.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lynaysereyvath.remindme.data.local.deleteKeys
import com.lynaysereyvath.remindme.ui.RemindMeNavigationActions
import com.lynaysereyvath.remindme.ui.home.QuoteItemUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    remindMeNavigationActions: RemindMeNavigationActions
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val viewModel: SearchViewModel = hiltViewModel()

    val searchTerm by viewModel.searchTerm.collectAsState()
    val textFieldValue =
        remember { mutableStateOf(TextFieldValue(searchTerm, TextRange(searchTerm.length))) }
    val onSearchTermChange: (value: String) -> Unit = remember {
        return@remember viewModel::setSearchKeyWord
    }

    val searchResults by viewModel.searchResults.collectAsState()

    var canClearText by remember { mutableStateOf(false) }
    val clearText = {
        textFieldValue.value = TextFieldValue("")
        onSearchTermChange("")
    }
    LaunchedEffect(searchTerm) {
        viewModel.search(searchTerm)
        if (searchTerm.isNotBlank()) {
            canClearText = true
        } else {
            canClearText = false
        }
    }

    var moreMenuExpanded by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(true) {
        focusRequester.requestFocus()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = textFieldValue.value,
                        onValueChange = { value ->
                            textFieldValue.value = value
                            onSearchTermChange(value.text)
                        },
                        colors = TextFieldDefaults.colors().copy(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        ),
                        placeholder = { Text("Search your quotes") },
                        maxLines = 1,
                        textStyle = TextStyle.Default.copy(fontSize = 18.sp),
                        modifier = Modifier.focusRequester(focusRequester)
                    )
                },
                navigationIcon = {
                    if (viewModel.isItemsSelected) {
                        IconButton(onClick = viewModel.cancelAllSelections) {
                            Icon(
                                Icons.Outlined.Close,
                                contentDescription = "cancel selection"
                            )
                        }
                    } else {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = "navigate up"
                            )
                        }
                    }
                },
                actions = {
                    if (viewModel.isItemsSelected) {
                        IconButton(onClick = {
                            moreMenuExpanded = true
                        }) { Icon(Icons.Outlined.MoreVert, "more") }
                    } else if (canClearText)
                        IconButton(onClick = clearText) {
                            Icon(
                                Icons.Outlined.Close,
                                "clear text"
                            )
                        }

                    if (moreMenuExpanded) {
                        Box {
                            DropdownMenu(
                                expanded = moreMenuExpanded,
                                onDismissRequest = { moreMenuExpanded = false }) {
                                DropdownMenuItem(
                                    text = { Text("Delete") },
                                    onClick = {
                                        moreMenuExpanded = false
                                        viewModel.delete { ids ->
                                            coroutineScope.launch(Dispatchers.IO) {
                                                context.deleteKeys(ids)
                                            }
                                        }
                                    })
                            }
                        }
                    }
                })
        }) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(viewModel.quotesList) { quote ->
                QuoteItemUI(
                    quote = quote,
                    onClicked = {
                        remindMeNavigationActions.navigateToAddQuote(it)
                    },
                    isSelectableOnClick = viewModel.isSelectableOnClick,
                    onSelectedStateChanged = viewModel.toggleSelection,
                )
            }
        }
    }
}