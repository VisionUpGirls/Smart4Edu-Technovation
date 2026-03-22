package com.example.technovation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.technovation.Routes
import kotlin.math.abs
import androidx.compose.ui.graphics.luminance

@Composable
fun HomeScreen(
    navController: NavController,
    username: String,
    modifier: Modifier = Modifier
) {
    val tips = listOf(
        "Nu trebuie să fii grozav ca să începi. Trebuie să începi ca să devii grozav.",
        "Fiecare exercițiu rezolvat azi e un punct câștigat mâine.",
        "Oboseala trece. Mândria rămâne.",
        "Notele nu te definesc, dar munca ta te reprezintă.",
        "Dacă azi înveți când nu ai chef, mâine vei reuși când contează."
    )

    val dayIndex = remember {
        val days = (System.currentTimeMillis() / 86_400_000L).toInt()
        abs(days) % tips.size
    }
    val dailyTip = tips[dayIndex]

    val isLight = MaterialTheme.colorScheme.background.luminance() > 0.5f

    val gradStart = if (isLight) Color(0xFF6C63FF) else Color(0xFF4A3FCF)
    val gradEnd = if (isLight) Color(0xFF8B7CFF) else Color(0xFF6A5AE0)

    ScreenColumn(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = if (username.isBlank()) "Hello!" else "Hello, $username!",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Your Evaluarea Națională dashboard",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(listOf(gradStart, gradEnd)),
                    shape = RoundedCornerShape(26.dp)
                )
                .padding(18.dp)
        ) {
            Column {
                Text(
                    text = "Daily motivation",
                    color = Color.White.copy(alpha = 0.90f),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = dailyTip,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                title = "Streak",
                value = "3 days",
                hint = "Keep it going",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Last score",
                value = "78/100",
                hint = "See Progress tab",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        CardSurface {
            Text(
                text = "Today's focus",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.70f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Finish a 10-minute practice sprint",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { navController.navigate(Routes.PRACTICE) },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Go to Practice", fontWeight = FontWeight.Bold, maxLines = 1)
            }
        }

        Spacer(modifier = Modifier.height(18.dp))
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    hint: String,
    modifier: Modifier = Modifier
) {
    CardSurface(modifier = modifier, paddingDp = 14) {
        Text(
            title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.70f)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            value,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            hint,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.70f)
        )
    }
}