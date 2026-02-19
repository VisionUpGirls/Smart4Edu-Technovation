package com.example.technovation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.math.abs

/* ============================================================
   GLOBAL UI HELPERS (FIXED: NO OVERLAP)
   ============================================================ */

@Composable
private fun ScreenColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) { content() }
}

/**
 * IMPORTANT FIX:
 * This is a Column now (not Box), so children DON'T overlap.
 */
@Composable
private fun CardSurface(
    modifier: Modifier = Modifier,
    paddingDp: Int = 16,
    verticalGapDp: Int = 0,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                RoundedCornerShape(22.dp)
            )
            .padding(paddingDp.dp),
        verticalArrangement = if (verticalGapDp > 0) Arrangement.spacedBy(verticalGapDp.dp) else Arrangement.Top
    ) {
        content()
    }
}

@Composable
private fun Pill(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun TopBackBar(
    title: String,
    subtitle: String? = null,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    CardSurface(modifier = modifier, paddingDp = 12) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (onBack != null) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { onBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Text("←", fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.width(12.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (subtitle != null) {
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.70f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun ProgressBar(value: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(value.coerceIn(0f, 1f))
                .height(10.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(MaterialTheme.colorScheme.primary)
        )
    }
}

/* ============================================================
   LOGIN
   ============================================================ */

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    onLoggedIn: (String) -> Unit
) {
    val userState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val errorState = remember { mutableStateOf<String?>(null) }

    val cardBorder = Color(0xFFE6E8F2)
    val ink = Color(0xFF0F1220)
    val accentStripe = Color(0xFFE48A2E)
    val linkBlue = Color(0xFF2E86D1)

    val gradStart = Color(0xFFC590F2)
    val gradEnd = Color(0xFF4522C1)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF6F3FF))
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.smart4edu_header),
            contentDescription = "Smart4Edu Header",
            modifier = Modifier
                .fillMaxWidth()
                .height(370.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Welcome!",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = ink
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Log in to your account!",
            style = MaterialTheme.typography.bodyMedium,
            color = ink.copy(alpha = 0.75f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .border(1.dp, cardBorder, RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            Text("Username", fontWeight = FontWeight.SemiBold, color = ink)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = userState.value,
                onValueChange = {
                    userState.value = it
                    errorState.value = null
                },
                placeholder = { Text("Enter username") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_username),
                        contentDescription = "Username icon",
                        modifier = Modifier.size(34.dp),
                        tint = Color.Unspecified
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text("Password", fontWeight = FontWeight.SemiBold, color = ink)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = passwordState.value,
                onValueChange = {
                    passwordState.value = it
                    errorState.value = null
                },
                placeholder = { Text("Enter password") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_password),
                        contentDescription = "Password icon",
                        modifier = Modifier.size(34.dp),
                        tint = Color.Unspecified
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            if (errorState.value != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = errorState.value!!, color = Color(0xFFB00020))
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .height(3.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(accentStripe)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                val u = userState.value.trim()
                val p = passwordState.value
                if (u.isEmpty() || p.isEmpty()) {
                    errorState.value = "Please enter username and password."
                } else {
                    onLoggedIn(u)
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .height(56.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(gradStart, gradEnd)),
                    shape = RoundedCornerShape(20.dp)
                ),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Text("Log In", color = Color.White, fontWeight = FontWeight.Bold, maxLines = 1)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Don’t have an account? Create one!",
            color = ink.copy(alpha = 0.75f),
            modifier = Modifier.clickable { navController.navigate(Routes.SIGNUP) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = { navController.navigate(Routes.SIGNUP) },
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .height(52.dp),
            shape = RoundedCornerShape(18.dp),
            border = BorderStroke(2.dp, linkBlue),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
        ) {
            Text("Sign Up", color = linkBlue, fontWeight = FontWeight.Bold, maxLines = 1)
        }
    }
}

@Composable
fun SignUpScreen(navController: NavController, modifier: Modifier = Modifier) {
    ScreenColumn(modifier = modifier.padding(16.dp)) {
        TopBackBar(title = "Sign Up", onBack = { navController.popBackStack() })
        Spacer(Modifier.height(12.dp))
        CardSurface {
            Text("Sign Up (coming soon)", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Already have an account? Log in",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { navController.navigate(Routes.LOGIN) }
            )
        }
    }
}

/* ============================================================
   HOME (fixed: no overlap)
   ============================================================ */

@Composable
fun HomeScreen(
    navController: NavController,
    username: String,
    modifier: Modifier = Modifier
) {
    val tips = listOf(
        "Start with 10 minutes. Starting is the hardest part.",
        "Use 25 minutes focus + 5 minutes break.",
        "Do one small task at a time: one lesson, one topic.",
        "Close notes and explain it in your own words.",
        "Mistakes are data: review and write the correct method.",
        "Sleep helps memory. Don’t sacrifice it before a test.",
        "Silence notifications for 20–30 minutes while studying.",
        "Practice beats rereading: solve, then check solutions.",
        "If stuck, take a short walk and come back.",
        "Consistency wins: a little daily beats cramming."
    )

    val dayIndex = remember {
        val days = (System.currentTimeMillis() / 86_400_000L).toInt()
        abs(days) % tips.size
    }
    val dailyTip = tips[dayIndex]

    val gradStart = Color(0xFF2A1F5E)
    val gradEnd = Color(0xFF432F85)

    ScreenColumn(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Smart4Edu",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(6.dp))

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

        // Daily tip card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(26.dp))
                .background(Brush.horizontalGradient(listOf(gradStart, gradEnd)))
                .padding(18.dp)
        ) {
            Column {
                Text(
                    text = "Daily tip",
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
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Stacked (not row)
        StatCard(
            title = "Streak",
            value = "3 days",
            hint = "Keep it going"
        )
        Spacer(Modifier.height(12.dp))
        StatCard(
            title = "Last score",
            value = "78/100",
            hint = "See Progress tab"
        )

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

/* ============================================================
   SETTINGS / HELP / ABOUT
   ============================================================ */

@Composable
fun SettingsScreen(navController: NavController, modifier: Modifier = Modifier) {
    ScreenColumn(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TopBackBar(title = "Settings", onBack = { navController.popBackStack() })
        Spacer(Modifier.height(12.dp))
        CardSurface {
            Text("• Theme is controlled from the top-right toggle.")
            Spacer(Modifier.height(8.dp))
            Text("• Add account options here later (profile, password, etc.).")
        }
    }
}

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
            Text("Version: 1.3.5", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.80f))
        }
    }
}

/* ============================================================
   PRACTICE
   ============================================================ */

@Composable
fun PracticeScreen(navController: NavController, modifier: Modifier = Modifier) {
    ScreenColumn(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Choose what you want to practice:",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        PracticeTile(
            title = "Limba română",
            subtitle = "Gramatică • Lectură • Exprimare",
            background = Color(0xFF5B4BB7),
            onClick = { navController.navigate(Routes.practiceMenuRoute("Limba română")) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        PracticeTile(
            title = "Matematică",
            subtitle = "Exerciții • Probleme",
            background = Color(0xFF3E2F8F),
            onClick = { navController.navigate(Routes.practiceMenuRoute("Matematică")) }
        )
    }
}

@Composable
private fun PracticeTile(
    title: String,
    subtitle: String,
    background: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(26.dp))
            .background(background.copy(alpha = 0.96f))
            .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(26.dp))
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Column {
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = subtitle,
                color = Color.White.copy(alpha = 0.9f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(14.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(Color.White.copy(alpha = 0.18f))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text("Open", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun PracticeMenuScreen(
    navController: NavController,
    subject: String,
    modifier: Modifier = Modifier
) {
    val topics = remember(subject) {
        when (subject) {
            "Limba română" -> listOf(
                "Gramatică",
                "Vocabular",
                "Punctuație",
                "Text argumentativ",
                "Rezumat"
            )
            "Matematică" -> listOf(
                "Fracții",
                "Proporții & Procente",
                "Ecuații",
                "Geometrie",
                "Probleme (aplicații)"
            )
            else -> listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
        }
    }

    ScreenColumn(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TopBackBar(title = subject, subtitle = "Choose a topic", onBack = { navController.popBackStack() })
        Spacer(Modifier.height(12.dp))

        topics.forEach { topic ->
            TopicCard(
                title = topic,
                onClick = { navController.navigate(Routes.practiceTopicRoute(subject, topic)) }
            )
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
private fun TopicCard(
    title: String,
    onClick: () -> Unit
) {
    CardSurface(
        modifier = Modifier
            .clickable { onClick() }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text("→", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun PracticeTopicScreen(
    navController: NavController,
    subject: String,
    topic: String,
    modifier: Modifier = Modifier
) {
    ScreenColumn(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TopBackBar(title = topic, subtitle = subject, onBack = { navController.popBackStack() })
        Spacer(Modifier.height(12.dp))

        CardSurface {
            Text("Mini test", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text(
                "Answer questions and see feedback immediately.",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(14.dp))
            Button(
                onClick = { navController.navigate(Routes.practiceQuizRoute(subject, topic)) },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().height(54.dp)
            ) {
                Text("Start Quiz", fontWeight = FontWeight.Bold, maxLines = 1)
            }
        }
    }
}

/* ============================================================
   QUIZ (fixed: no overlap + readable correct/wrong)
   ============================================================ */

private data class QuizQuestion(
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

private enum class OptionState { Normal, Correct, Wrong }

@Composable
private fun OptionCard(
    label: String,
    text: String,
    selected: Boolean,
    locked: Boolean,
    state: OptionState,
    onClick: () -> Unit
) {
    val borderColor = when (state) {
        OptionState.Normal -> if (selected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        OptionState.Correct -> Color(0xFF2E7D32)
        OptionState.Wrong -> Color(0xFFC62828)
    }

    val bgColor = when (state) {
        OptionState.Normal -> if (selected) MaterialTheme.colorScheme.surfaceVariant
        else MaterialTheme.colorScheme.surface
        OptionState.Correct -> Color(0xFFDCF7E3)
        OptionState.Wrong -> Color(0xFFF9D6D6)
    }

    // force readable text on red/green backgrounds even in dark theme:
    val optionTextColor = when (state) {
        OptionState.Correct, OptionState.Wrong -> Color.Black
        else -> MaterialTheme.colorScheme.onSurface
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(bgColor)
            .border(2.dp, borderColor, RoundedCornerShape(18.dp))
            .clickable(enabled = !locked) { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)),
            contentAlignment = Alignment.Center
        ) {
            Text(label, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        }
        Spacer(Modifier.width(12.dp))
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            color = optionTextColor,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun PracticeQuizScreen(
    navController: NavController,
    subject: String,
    topic: String,
    modifier: Modifier = Modifier
) {
    val questions = remember(subject, topic) { quizQuestionsFor(subject, topic) }

    var index by remember { mutableStateOf(0) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var locked by remember { mutableStateOf(false) }
    var correctCount by remember { mutableStateOf(0) }

    if (questions.isEmpty()) {
        ScreenColumn(modifier = modifier.padding(16.dp)) {
            TopBackBar(title = "Quiz", subtitle = "$subject • $topic", onBack = { navController.popBackStack() })
            Spacer(Modifier.height(12.dp))
            CardSurface { Text("No quiz available yet for this topic.") }
        }
        return
    }

    if (index >= questions.size) {
        QuizResultScreen(
            subject = subject,
            topic = topic,
            correct = correctCount,
            total = questions.size,
            onRetry = {
                index = 0
                selectedIndex = null
                locked = false
                correctCount = 0
            },
            onBack = { navController.popBackStack() }
        )
        return
    }

    val q = questions[index]
    val total = questions.size
    val progress = (index + 1).toFloat() / total.toFloat()
    val labels = listOf("A", "B", "C", "D")

    ScreenColumn(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TopBackBar(title = "Quiz", subtitle = "$subject • $topic", onBack = { navController.popBackStack() })
        Spacer(Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Pill("${index + 1} / $total")
            Pill("Score: $correctCount")
        }

        Spacer(Modifier.height(10.dp))
        ProgressBar(progress)

        Spacer(Modifier.height(14.dp))

        CardSurface {
            Text("Question ${index + 1}", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(
                q.prompt,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(Modifier.height(14.dp))

        q.options.forEachIndexed { i, opt ->
            val state = when {
                !locked -> OptionState.Normal
                i == q.correctIndex -> OptionState.Correct
                selectedIndex == i && i != q.correctIndex -> OptionState.Wrong
                else -> OptionState.Normal
            }

            OptionCard(
                label = labels.getOrElse(i) { (i + 1).toString() },
                text = opt,
                selected = selectedIndex == i,
                locked = locked,
                state = state,
                onClick = { selectedIndex = i }
            )
            Spacer(Modifier.height(10.dp))
        }

        if (locked) {
            val ok = selectedIndex == q.correctIndex
            CardSurface {
                Text(
                    text = if (ok) "Correct ✅" else "Incorrect ❌",
                    fontWeight = FontWeight.Bold,
                    color = if (ok) Color(0xFF2E7D32) else Color(0xFFC62828)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = q.explanation,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.height(12.dp))
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(
                onClick = { navController.popBackStack() },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.weight(1f)
            ) { Text("Back") }

            Button(
                onClick = {
                    if (!locked) {
                        val chosen = selectedIndex ?: return@Button
                        locked = true
                        if (chosen == q.correctIndex) correctCount += 1
                    } else {
                        index += 1
                        selectedIndex = null
                        locked = false
                    }
                },
                enabled = (selectedIndex != null) || locked,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(if (!locked) "Check" else "Next", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun QuizResultScreen(
    subject: String,
    topic: String,
    correct: Int,
    total: Int,
    onRetry: () -> Unit,
    onBack: () -> Unit
) {
    val percent = if (total == 0) 0 else ((correct.toFloat() / total.toFloat()) * 100f).toInt()

    ScreenColumn(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TopBackBar(title = "Results", subtitle = "$subject • $topic", onBack = onBack)
        Spacer(Modifier.height(12.dp))

        CardSurface {
            Text("Score", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(
                "$correct / $total",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(6.dp))
            Text("Accuracy: $percent%")
        }

        Spacer(Modifier.height(14.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(
                onClick = onBack,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.weight(1f)
            ) { Text("Back") }

            Button(
                onClick = onRetry,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.weight(1f)
            ) { Text("Retry", fontWeight = FontWeight.Bold) }
        }
    }
}

/* ============================================================
   PROGRESS (fixed: no overlap, all CardSurface = Column)
   ============================================================ */

private data class ProgressSlice(val label: String, val value: Float, val color: Color)
private data class SkillRow(val name: String, val value: Float)

@Composable
fun ProgressScreen(navController: NavController, modifier: Modifier = Modifier) {
    val sections = listOf(
        ProgressSlice("Matematică", 0.55f, Color(0xFF7B6CF6)),
        ProgressSlice("Română", 0.35f, Color(0xFFB7AEFF)),
        ProgressSlice("Recap", 0.10f, Color(0xFFE9B96E))
    )

    val latestMathScore = 82
    val latestRomanianScore = 74

    val mathSkills = listOf(
        SkillRow("Fracții", 0.72f),
        SkillRow("Ecuații", 0.58f),
        SkillRow("Geometrie", 0.61f),
        SkillRow("Procente", 0.54f)
    )

    val romanianSkills = listOf(
        SkillRow("Gramatică", 0.66f),
        SkillRow("Punctuație", 0.57f),
        SkillRow("Vocabular", 0.62f),
        SkillRow("Text argumentativ", 0.49f)
    )

    ScreenColumn(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Latest results", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(12.dp))

        ResultBlock(
            title = "Matematică",
            score = latestMathScore,
            hint = "Based on your last quiz"
        )
        Spacer(Modifier.height(12.dp))
        ResultBlock(
            title = "Română",
            score = latestRomanianScore,
            hint = "Based on your last quiz"
        )

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
    hint: String
) {
    CardSurface {
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
                .clip(RoundedCornerShape(3.dp))
                .background(color)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(label, modifier = Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text("${(percent * 100).toInt()}%", maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

/* ============================================================
   CALM
   ============================================================ */

@Composable
fun CalmScreen(navController: NavController, modifier: Modifier = Modifier) {
    ScreenColumn(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Calm", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            CalmTile(
                title = "Equal Breathing",
                description = "Balanced breathing for calm focus.",
                background = Brush.verticalGradient(listOf(Color(0xFF2C1E5D), Color(0xFF4F46E5))),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(14.dp))
            CalmTile(
                title = "Box Breathing",
                description = "Structured rhythm for stress.",
                background = Brush.verticalGradient(listOf(Color(0xFF1B2A5E), Color(0xFF6D28D9))),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            CalmTile(
                title = "4-7-8 Breathing",
                description = "Slow pace for relaxation.",
                background = Brush.verticalGradient(listOf(Color(0xFF3A1C56), Color(0xFF7C3AED))),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(14.dp))
            CalmTile(
                title = "Breath Holding",
                description = "Test your breath capacity.",
                background = Brush.verticalGradient(listOf(Color(0xFF1F1744), Color(0xFF4C1D95))),
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
            .clip(RoundedCornerShape(28.dp))
            .background(background)
            .clickable { onClick() }
            .padding(22.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = description,
                color = Color.White.copy(alpha = 0.9f),
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

/* ============================================================
   CHAT
   ============================================================ */

private data class ChatMsg(val role: String, val text: String)

@Composable
fun ChatScreen(navController: NavController, modifier: Modifier = Modifier) {
    val messages = listOf(
        ChatMsg("assistant", "Salut! Cu ce te pot ajuta pentru Evaluarea Națională?"),
        ChatMsg("user", "Nu înțeleg fracțiile. Poți explica?"),
        ChatMsg("assistant", "Sigur. O fracție arată o parte dintr-un întreg. Exemplu: 3/4 înseamnă 3 părți din 4."),
        ChatMsg("user", "Și cum compar 2/3 cu 3/5?"),
        ChatMsg("assistant", "Le aduci la același numitor sau folosești înmulțirea în cruce. Hai să facem împreună.")
    )

    ScreenColumn(modifier = modifier.padding(16.dp)) {
        Text("Chat", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        CardSurface(paddingDp = 12) {
            messages.forEach { msg ->
                ChatBubble(isUser = msg.role == "user", text = msg.text)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        ChatChips()
    }
}

@Composable
private fun ChatBubble(isUser: Boolean, text: String) {
    val bubbleColor = if (isUser) Color(0xFF5B40B2) else MaterialTheme.colorScheme.surfaceVariant
    val textColor = if (isUser) Color.White else MaterialTheme.colorScheme.onSurface

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.86f)
                .clip(RoundedCornerShape(18.dp))
                .background(bubbleColor)
                .padding(12.dp)
        ) {
            Text(text = text, color = textColor, maxLines = 6, overflow = TextOverflow.Ellipsis)
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
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(text = label, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

/* ============================================================
   QUESTION BANK (prototype)
   ============================================================ */

private fun quizQuestionsFor(subject: String, topic: String): List<QuizQuestion> {
    return when (subject) {
        "Matematică" -> mathQuiz(topic)
        "Limba română" -> romanianQuiz(topic)
        else -> emptyList()
    }
}

private fun mathQuiz(topic: String): List<QuizQuestion> {
    val base = listOf(
        QuizQuestion(
            prompt = "(72 - 8·7):4 + 6 =",
            options = listOf("8", "9", "10", "12"),
            correctIndex = 2,
            explanation = "72-56=16; 16:4=4; 4+6=10."
        ),
        QuizQuestion(
            prompt = "5^2 + 2^4 =",
            options = listOf("29", "33", "41", "45"),
            correctIndex = 2,
            explanation = "25 + 16 = 41."
        ),
        QuizQuestion(
            prompt = "3/5 + 7/10 =",
            options = listOf("13/10", "17/10", "19/10", "21/10"),
            correctIndex = 0,
            explanation = "3/5 = 6/10; 6/10 + 7/10 = 13/10."
        ),
        QuizQuestion(
            prompt = "Media aritmetică a numerelor 18 și 26 este:",
            options = listOf("20", "21", "22", "23"),
            correctIndex = 2,
            explanation = "(18+26)/2 = 22."
        ),
        QuizQuestion(
            prompt = "Într-un triunghi dreptunghic cu catetele 9 și 12, ipotenuza este:",
            options = listOf("13", "14", "15", "16"),
            correctIndex = 2,
            explanation = "Pitagora: √(9²+12²)=√225=15."
        )
    )

    return when (topic) {
        "Fracții" -> base.take(4) + QuizQuestion(
            prompt = "5/4 - 3/8 =",
            options = listOf("5/8", "7/8", "9/8", "11/8"),
            correctIndex = 1,
            explanation = "5/4 = 10/8; 10/8 - 3/8 = 7/8."
        )
        "Proporții & Procente" -> base.take(4) + QuizQuestion(
            prompt = "Un produs crește cu 20% apoi scade cu 20%. Prețul final este:",
            options = listOf("Egal cu inițial", "Mai mare", "Mai mic", "Nu se poate determina"),
            correctIndex = 2,
            explanation = "1.2P apoi 0.8×1.2P=0.96P → mai mic."
        )
        "Ecuații" -> base.take(4) + QuizQuestion(
            prompt = "Rezolvă: |2x - 5| = x + 1",
            options = listOf("{6}", "{4/3, 6}", "{-1, 6}", "{4/3}"),
            correctIndex = 1,
            explanation = "Cazuri: 2x-5=x+1→x=6; -(2x-5)=x+1→x=4/3."
        )
        else -> base
    }
}

private fun romanianQuiz(topic: String): List<QuizQuestion> {
    val base = listOf(
        QuizQuestion(
            prompt = "Într-un rezumat este corect să:",
            options = listOf(
                "Folosești citate",
                "Îți spui opinia",
                "Prezinți faptele pe scurt, obiectiv",
                "Analizezi figurile de stil"
            ),
            correctIndex = 2,
            explanation = "Rezumat = fapte, obiectiv, fără citate și fără opinii."
        ),
        QuizQuestion(
            prompt = "În caracterizare, o regulă sigură este:",
            options = listOf(
                "Trăsătură + dovadă din text",
                "Doar adjective fără exemple",
                "Doar citate",
                "Doar opinia ta"
            ),
            correctIndex = 0,
            explanation = "Pentru punctaj: trăsătură + exemplu/dovadă."
        ),
        QuizQuestion(
            prompt = "În text argumentativ, structura recomandată este:",
            options = listOf(
                "Afirmație + explicație + exemplu",
                "Exemplu + exemplu",
                "Doar afirmație",
                "Citate multe"
            ),
            correctIndex = 0,
            explanation = "Cea mai sigură schemă: afirmație, explicație, exemplu."
        ),
        QuizQuestion(
            prompt = "Ideea principală într-un text informativ este:",
            options = listOf(
                "Un detaliu minor",
                "Mesajul central al textului",
                "O opinie personală",
                "O întrebare"
            ),
            correctIndex = 1,
            explanation = "Ideea principală = informația centrală / mesajul textului."
        ),
        QuizQuestion(
            prompt = "„Mesajul” unui text liric este:",
            options = listOf(
                "Povestea pe scurt",
                "Ideea/semnificația generală transmisă",
                "Lista figurilor de stil",
                "Biografia autorului"
            ),
            correctIndex = 1,
            explanation = "Mesaj = semnificația/ideea generală."
        )
    )

    return when (topic) {
        "Gramatică" -> base + QuizQuestion(
            prompt = "Transformă la plural: „Noaptea este liniștită.”",
            options = listOf(
                "Noaptea sunt liniștite.",
                "Nopțile este liniștită.",
                "Nopțile sunt liniștite.",
                "Nopțile sunt liniștit."
            ),
            correctIndex = 2,
            explanation = "Plural corect: „Nopțile sunt liniștite.”"
        )
        "Rezumat" -> base + QuizQuestion(
            prompt = "În rezumat trebuie să eviți:",
            options = listOf(
                "Ideile principale",
                "Ordinea logică a întâmplărilor",
                "Opiniile personale",
                "Formulările concise"
            ),
            correctIndex = 2,
            explanation = "Rezumatul nu include opinii personale."
        )
        else -> base
    }
}
