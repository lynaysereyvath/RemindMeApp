package com.lynaysereyvath.remindme.ui.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lynaysereyvath.remindme.R
import com.lynaysereyvath.remindme.domain.QuoteEntity
import com.lynaysereyvath.remindme.ui.theme.RemindMeTheme
import com.lynaysereyvath.remindme.ui.theme.Surface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLayout(navController: NavController) {

    val viewModel = hiltViewModel<AddViewModel>()
    val name by viewModel.name.collectAsStateWithLifecycle()
    val message by viewModel.message.collectAsStateWithLifecycle()
    val onNameEntered: (value: String) -> Unit = remember {
        return@remember viewModel::setName
    }
    val onMessageEntered: (value: String) -> Unit = remember {
        return@remember viewModel::setMessage
    }
    val onSubmit: (value: QuoteEntity) -> Unit = remember {
        return@remember viewModel::insertQuoteEntity
    }

    val id = navController.currentBackStackEntry?.arguments?.getInt("id")

    val transparentContainerColor = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
    )

    LaunchedEffect(key1 = true, block = {
        if (id != null && id != -1) {
            viewModel.getQuote(id)
        }
    })

    Scaffold(
        modifier = Modifier.background(Surface),
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "arrow back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                    }) {
                        Icon(
                            painterResource(R.drawable.outline_keep_24),
                            contentDescription = "arrow back"
                        )
                    }
                    IconButton(onClick = {
                    }) {
                        Icon(
                            painterResource(R.drawable.outline_add_alert_24),
                            contentDescription = "arrow back"
                        )
                    }
                    IconButton(onClick = {
                    }) {
                        Icon(
                            painterResource(R.drawable.outline_archive_24),
                            contentDescription = "arrow back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Outlined.MoreVert, contentDescription = "")
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            painterResource(R.drawable.outline_add_box_24),
                            contentDescription = ""
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            painterResource(R.drawable.outline_palette_24),
                            contentDescription = ""
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            painterResource(R.drawable.outline_text_format_24),
                            contentDescription = ""
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        onSubmit(QuoteEntity(author = name, message = message))
                        navController.popBackStack()
                    }) {
                        Icon(
                            painterResource(R.drawable.outline_forward_to_inbox_24),
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ) {

        Column(modifier = Modifier.padding(it)) {
            TextField(
                value = name,
                onValueChange = { n -> onNameEntered(n) },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Author",
                    )
                },
                colors = transparentContainerColor,
                textStyle = TextStyle(fontWeight = FontWeight.Medium, fontSize = 16.sp),
            )
            TextField(
                value = message,
                onValueChange = { value -> onMessageEntered(value) },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        "Quote",
                    )
                },
                colors = transparentContainerColor,
            )
        }
    }

}
