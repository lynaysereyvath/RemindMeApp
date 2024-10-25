package com.lynaysereyvath.remindme.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lynaysereyvath.remindme.domain.QuoteEntity

@Composable
fun QuoteItemUI(quote: QuoteEntity, onClicked: (id: Int) -> Unit) {

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        onClick = {
            onClicked(quote.id)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "\"${quote.message}\"",
                lineHeight = 20.sp,
            )
            Text(text = quote.author, modifier = Modifier.padding(top = 20.dp))

        }
    }
}

@Composable
@Preview
fun QuoteItemUIPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        QuoteItemUI(
            QuoteEntity(
                author = "ABC DEF",
                message = "Message Message Message Message Message Message Message Message Message Message Message Message Message Message Message Message "
            )
        ) {}

    }
}