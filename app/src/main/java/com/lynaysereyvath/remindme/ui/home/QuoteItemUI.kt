package com.lynaysereyvath.remindme.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun QuoteItemUI(name: String, message: String) {
    Box(modifier = Modifier.padding(horizontal = 5.dp, vertical = 3.dp)) {

        Card(
            modifier = Modifier,
            shape = RectangleShape, elevation = CardDefaults.cardElevation(3.dp),
            colors = CardDefaults.cardColors(Color.Yellow)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 3.dp)
                    .background(Color.White)
            ) {
                Spacer(
                    modifier = Modifier
                        .width(2.dp)
                        .background(Color.Yellow)
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = message, modifier = Modifier.padding(), style = TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(text = "- $name", modifier = Modifier.padding(start = 10.dp))
                }
            }
        }
    }
}

@Composable
@Preview
fun QuoteItemUIPreview() {
    QuoteItemUI(name = "ABC DEF", message = "Message Message Message Message Message Message Message Message Message Message Message Message Message Message Message Message ")
}