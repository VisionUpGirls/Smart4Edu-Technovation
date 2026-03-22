package com.example.technovation.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HelpScreen(navController: NavController, modifier: Modifier = Modifier) {
    ScreenColumn(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TopBackBar(title = "Help", onBack = { navController.popBackStack() })
        Spacer(Modifier.height(12.dp))
        CardSurface {
            Text("Quick tips:", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Text("• Practice: pick a subject and answer questions.")
            Text("• Calm: breathing tools for focus.")
            Text("• Progress: track your weekly progress and skills.")
        }
    }
}