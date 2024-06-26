package com.lynaysereyvath.remindme.ui.add

import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import com.lynaysereyvath.remindme.domain.QuoteEntity
import com.lynaysereyvath.remindme.ui.RemindMeAppScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLayout(navController: NavController, id: Int) {

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

    val titleTextStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
    val paragraphTextStyle = TextStyle(fontSize = 14.sp)
    val transparentContainerColor = TextFieldDefaults.textFieldColors(
        containerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
    )
    
    LaunchedEffect(key1 = true, block = {
        if (id != -1) {
            viewModel.getQuote(id)
        }
    })

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack(RemindMeAppScreen.Home.name, inclusive = false)
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "arrow back")
                    }
                }
            )
        }) {

        Column(modifier = Modifier.padding(it)) {
            TextField(
                value = name,
                onValueChange = { onNameEntered(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                placeholder = {
                    Text(
                        text = "Author",
                        style = titleTextStyle,
                        color = Color.LightGray
                    )
                },
                colors = transparentContainerColor,
                textStyle = titleTextStyle
            )
            TextField(
                value = message,
                onValueChange = { onMessageEntered(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .weight(1f),
                placeholder = {
                    Text(
                        "Quote",
                        style = paragraphTextStyle,
                        color = Color.LightGray
                    )
                },
                colors = transparentContainerColor,
                textStyle = paragraphTextStyle
            )
            Button(
                onClick = {
                    onSubmit(QuoteEntity(author = name, message = message))
                    navController.popBackStack(RemindMeAppScreen.Home.name, inclusive = false)
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .fillMaxWidth(),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.LightGray
                )
            ) {
                Text(text = "Save")
            }
        }
    }

}

//@Composable
//@Preview
//fun AddLayoutPreview() {
//    RemindMeTheme {
//        Surface(modifier = Modifier.fillMaxSize()) {
//            AddLayout(onFinished = {}, onBack = {})
//        }
//    }
//}