package com.example.technovation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

private data class ProgressSlice(val label: String, val value: Float, val color: Color)
private data class SkillRow(val name: String, val value: Float)

@Composable
fun ProgressScreen(navController: NavController, modifier: Modifier = Modifier) {
    val sections = listOf(
        ProgressSlice("Matematică", 0.55f, Color(0xFF5B4BDB)),
        ProgressSlice("Română", 0.35f, Color(0xFF8A70F8)),
        ProgressSlice("Recap", 0.10f, Color(0xFFF2B544))
    )

    val latestMathScore = 82
    val latestRomanianScore = 74

    val mathSkills = listOf(
        SkillRow("Variante Matematică", 0.72f),
        SkillRow("Geometrie", 0.58f),
        SkillRow("Ecuații", 0.61f),
        SkillRow("Procente", 0.54f)
    )

    val romanianSkills = listOf(
        SkillRow("Variante Română", 0.66f),
        SkillRow("Rezumat", 0.57f),
        SkillRow("Text liric", 0.62f),
        SkillRow("Caracterizare", 0.49f)
    )

    ScreenColumn(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Your latest results", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ResultBlock(
                title = "Matematică",
                score = latestMathScore,
                hint = "Based on your last quiz",
                modifier = Modifier.weight(1f)
            )
            ResultBlock(
                title = "Română",
                score = latestRomanianScore,
                hint = "Based on your last quiz",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        CardSurface {
            Text("This week", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DonutChart(slices = sections, modifier = Modifier.size(140.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    sections.forEach { s ->
                        LegendRow(label = s.label, color = s.color, percent = s.value)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Skill progress", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(12.dp))

        SkillBlock(title = "Matematică", skills = mathSkills)
        Spacer(Modifier.height(12.dp))
        SkillBlock(title = "Română", skills = romanianSkills)
    }
}

@Composable
private fun ResultBlock(
    title: String,
    score: Int,
    hint: String,
    modifier: Modifier = Modifier
) {
    CardSurface(modifier = modifier) {
        Text(title, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Spacer(Modifier.height(10.dp))
        Text(
            text = "$score/100",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = hint,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun SkillBlock(
    title: String,
    skills: List<SkillRow>
) {
    CardSurface {
        Text(title, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(12.dp))
        skills.forEachIndexed { idx, row ->
            SkillProgressRow(row.name, row.value)
            if (idx != skills.lastIndex) Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun SkillProgressRow(title: String, value: Float) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(title, modifier = Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text("${(value * 100).toInt()}%", maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Spacer(modifier = Modifier.height(6.dp))
        ProgressBar(value)
    }
}

@Composable
private fun DonutChart(slices: List<ProgressSlice>, modifier: Modifier = Modifier) {
    val stroke = 18f
    val holeColor = MaterialTheme.colorScheme.surface

    Canvas(modifier = modifier) {
        var startAngle = -90f
        slices.forEach { s ->
            val sweep = 360f * s.value
            drawArc(
                color = s.color,
                startAngle = startAngle,
                sweepAngle = sweep,
                useCenter = false,
                style = Stroke(width = stroke)
            )
            startAngle += sweep
        }
        drawCircle(
            color = holeColor,
            radius = size.minDimension / 2.6f
        )
    }
}

@Composable
private fun LegendRow(label: String, color: Color, percent: Float) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color, RoundedCornerShape(3.dp))
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(label, modifier = Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text("${(percent * 100).toInt()}%", maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}