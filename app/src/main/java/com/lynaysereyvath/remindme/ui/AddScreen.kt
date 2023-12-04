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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lynaysereyvath.remindme.ui.theme.RemindMeTheme

data class Quote(
    val author: String,
    val quote: String
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLayout(onSubmit: (Quote) -> Unit)
{
    var author: String by rememberSaveable {
        mutableStateOf("")
    }
    var quote: String by rememberSaveable {
        mutableStateOf("")
    }
    Column {
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = author,
            onValueChange = { author = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            label = { Text(text = "Author")}
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = quote,
            onValueChange = { quote = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .weight(1f),
            label = { Text("Quote")},
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.End).padding(end = 20.dp)) {
            Text(text = "Save")
        }
    }
}

@Composable
@Preview
fun AddLayoutPreview()
{
    RemindMeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AddLayout {}
        }
    }
}