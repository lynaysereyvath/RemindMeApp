package com.lynaysereyvath.remindme.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lynaysereyvath.remindme.domain.QuoteEntity
import com.lynaysereyvath.remindme.ui.theme.RemindMeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLayout(onFinished: () -> Unit, onBack: () -> Unit) {

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

    Column {
        TextField(
            value = name,
            onValueChange = { onNameEntered(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            placeholder = { Text(text = "Author") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
        )
        TextField(
            value = message,
            onValueChange = { onMessageEntered(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .weight(1f),
            placeholder = { Text("Quote") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(fontSize = 14.sp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { onSubmit(QuoteEntity(author = name, message = message)) },
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 20.dp)
        ) {
            Text(text = "Save")
        }
    }
}

@Composable
@Preview
fun AddLayoutPreview() {
    RemindMeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AddLayout(onFinished = {}, onBack = {})
        }
    }
}