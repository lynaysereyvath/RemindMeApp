package com.lynaysereyvath.remindme.ui.home

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuoteItemUI(
    quote: QuoteState,
    isSelectableOnClick: () -> Boolean,
    onClicked: (id: Long) -> Unit,
    onSelectedStateChanged: (id: Long, isSelected: Boolean) -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    val isSelected = quote.isSelected

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 15.dp)
            .clip(MaterialTheme.shapes.medium)
            .combinedClickable(onClick = {
                if (isSelected)
                    onSelectedStateChanged(quote.quote.id, false)
                else {
                    if (isSelectableOnClick()) {
                        onSelectedStateChanged(quote.quote.id, true)
                    } else {
                        onClicked(quote.quote.id)
                    }
                }
            }, onLongClick = {
                onSelectedStateChanged(quote.quote.id, !isSelected)
            }, interactionSource = interactionSource, indication = ripple(bounded = true)),
        border = BorderStroke(
            if (isSelected) 3.dp else 1.dp,
            if (isSelected) Color.DarkGray else MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "\"${quote.quote.message}\"",
                lineHeight = 20.sp,
            )
            Text(text = quote.quote.author, modifier = Modifier.padding(top = 20.dp))

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
//        QuoteItemUI(
//            QuoteEntity(
//                author = "ABC DEF",
//                message = "Message Message Message Message Message Message Message Message Message Message Message Message Message Message Message Message "
//            ),
//            onSelectedStateChanged = {},
//            onClicked = {}
//        )

    }
}