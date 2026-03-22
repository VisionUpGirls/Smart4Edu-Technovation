package com.example.technovation.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AboutScreen(navController: NavController, modifier: Modifier = Modifier) {
    ScreenColumn(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TopBackBar(title = "About", onBack = { navController.popBackStack() })
        Spacer(Modifier.height(12.dp))
        CardSurface {
            Text("Smart4Edu", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))
            Text("An app to support Evaluarea Națională practice and progress tracking.")
            Spacer(Modifier.height(10.dp))
            Text(
                "Version: 1.5.7",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.80f)
            )
        }
    }
}