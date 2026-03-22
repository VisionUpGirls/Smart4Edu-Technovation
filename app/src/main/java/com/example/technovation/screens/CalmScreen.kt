package com.example.technovation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CalmScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    ScreenColumn(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            CalmTile(
                title = "Equal Breathing",
                description = "Balanced breathing for calm focus.",
                background = Brush.verticalGradient(
                    listOf(
                        Color(0xFF5B4BB7),
                        Color(0xFF7B6CF6)
                    )
                ),
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.padding(7.dp))

            CalmTile(
                title = "Box Breathing",
                description = "Structured rhythm for stress.",
                background = Brush.verticalGradient(
                    listOf(
                        Color(0xFF3E2F8F),
                        Color(0xFF6A5AE0)
                    )
                ),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            CalmTile(
                title = "4-7-8 Breathing",
                description = "Slow pace for relaxation.",
                background = Brush.verticalGradient(
                    listOf(
                        Color(0xFF432F85),
                        Color(0xFF7B6CF6)
                    )
                ),
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.padding(7.dp))

            CalmTile(
                title = "Breath Holding",
                description = "Test your breath capacity.",
                background = Brush.verticalGradient(
                    listOf(
                        Color(0xFF5B4BB7),
                        Color(0xFFE48A2E)
                    )
                ),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun CalmTile(
    title: String,
    description: String,
    background: Brush,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .heightIn(min = 200.dp)
            .background(background, RoundedCornerShape(28.dp))
            .clickable { onClick() }
            .padding(22.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = Color.White.copy(alpha = 0.95f),
                fontWeight = FontWeight.Bold,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = description,
                color = Color.White.copy(alpha = 0.90f),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}