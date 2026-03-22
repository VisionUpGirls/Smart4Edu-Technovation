package com.example.technovation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

private data class ChatMsg(val role: String, val text: String)

@Composable
fun ChatScreen(navController: NavController, modifier: Modifier = Modifier) {
    val messages = listOf(
        ChatMsg("assistant", "Salut! Cu ce te pot ajuta pentru Evaluarea Națională?"),
        ChatMsg("user", "Nu înțeleg fracțiile. Poți explica?"),
        ChatMsg("assistant", "Sigur. O fracție arată o parte dintr-un întreg."),
    )

    ScreenColumn(modifier = modifier.padding(16.dp)) {
        Text("Chat", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.padding(6.dp))

        CardSurface(paddingDp = 12) {
            messages.forEach { msg ->
                ChatBubble(isUser = msg.role == "user", text = msg.text)
                Spacer(modifier = Modifier.padding(4.dp))
            }
        }

        Spacer(modifier = Modifier.padding(6.dp))
        ChatChips()
    }
}

@Composable
private fun ChatBubble(isUser: Boolean, text: String) {
    val bubbleColor = if (isUser) Color(0xFF5B40B2) else MaterialTheme.colorScheme.surfaceVariant
    val textColor = if (isUser) Color.White else MaterialTheme.colorScheme.onSurface

    androidx.compose.foundation.layout.Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .fillMaxWidth(0.86f)
                .background(bubbleColor, RoundedCornerShape(18.dp))
                .padding(12.dp)
        ) {
            Text(text = text, color = textColor)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ChatChips() {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        QuickChip("Explică pe scurt")
        QuickChip("Dă un exemplu")
        QuickChip("Mai multe exerciții")
    }
}

@Composable
private fun QuickChip(label: String) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(999.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(text = label)
    }
}