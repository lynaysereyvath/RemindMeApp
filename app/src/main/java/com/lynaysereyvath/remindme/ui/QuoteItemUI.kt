package com.lynaysereyvath.remindme.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun QuoteItemUI(name: String, message: String) {
    Column {
        Text(text = name)
        Text(text = message)
    }
}

@Composable
@Preview
fun QuoteItemUIPreview() {
    QuoteItemUI(name = "ABC", message ="Message")
}