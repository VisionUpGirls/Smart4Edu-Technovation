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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

/* ---------------------------
   LOGIN
---------------------------- */

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
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Sign Up (coming soon)",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.BoldHello
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Already have an account? Log in",
            modifier = Modifier.clickable { navController.navigate(Routes.LOGIN) }
        )
    }
}

/* ---------------------------
   HOME
---------------------------- */

@Composable
fun HomeScreen(
    navController: NavController,
    username: String,
    modifier: Modifier = Modifier
) {
    val gradStart = Color(0xFF2A1F5E)
    val gradEnd = Color(0xFF432F85)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Smart4Edu",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = if (username.isBlank()) "Hello!" else "Hello, $username!",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Your Evaluarea Națională dashboard",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(26.dp))
                .background(Brush.horizontalGradient(listOf(gradStart, gradEnd)))
                .padding(18.dp)
        ) {
            Column {
                Text(
                    text = "Today’s focus",
                    color = Color.White.copy(alpha = 0.9f),
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Finish a 10-minute practice sprint",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Pick Română or Matematică, answer a few questions, then check your progress.",
                    color = Color.White.copy(alpha = 0.88f)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = { navController.navigate(Routes.PRACTICE) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text("Start Practice", color = Color(0xFF2A1F5E), fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            StatCard(
                title = "Streak",
                value = "3 days",
                hint = "Keep it going",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            StatCard(
                title = "Last score",
                value = "78/100",
                hint = "See Progress tab",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        StatCardWide(
            title = "Suggested next step",
            value = "Română: acord + punctuație",
            hint = "Do 10 quick questions"
        )

        Spacer(modifier = Modifier.height(22.dp))
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    hint: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(14.dp)
    ) {
        Text(title, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        Spacer(modifier = Modifier.height(6.dp))
        Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(6.dp))
        Text(hint, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
    }
}

@Composable
private fun StatCardWide(
    title: String,
    value: String,
    hint: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(title, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        Spacer(modifier = Modifier.height(8.dp))
        Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(6.dp))
        Text(hint, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
    }
}

/* ---------------------------
   SETTINGS / HELP / ABOUT
---------------------------- */

@Composable
fun SettingsScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Text(
            "• Theme is controlled from the top-right toggle.",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "• Add account options here later (profile, password, etc.).",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
    }
}

@Composable
fun HelpScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Help", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Text("Quick tips:", fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))
        Text("• Practice: pick a subject and answer questions.")
        Text("• Calm: breathing tools for focus.")
        Text("• Progress: track your weekly progress and skills.")
    }
}

@Composable
fun AboutScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("About", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Text("Smart4Edu", fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(6.dp))
        Text("An app to support Evaluarea Națională practice and progress tracking.")
        Spacer(Modifier.height(10.dp))
        Text("Version: 1.3.5", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))
    }
}

/* ---------------------------
   PRACTICE
---------------------------- */

@Composable
fun PracticeScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Choose what you want to practice:",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
        )

        Spacer(modifier = Modifier.height(20.dp))

        PracticeTile(
            title = "Limba română",
            subtitle = "Gramatică • Lectură • Exprimare",
            background = Color(0xFF5B4BB7),
            onClick = { navController.navigate(Routes.practiceMenuRoute("Limba română")) }
        )

        Spacer(modifier = Modifier.height(16.dp))

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
            .height(140.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(background)
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = subtitle,
                color = Color.White.copy(alpha = 0.85f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
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
                "Înțelegerea textului"
            )

            "Matematică" -> listOf(
                "Fracții",
                "Proporții & Procente",
                "Ecuații",
                "Geometrie",
                "Probleme (aplicații)"
            )

            else -> listOf("Set 1", "Set 2", "Set 3")
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = subject,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = "Choose a topic:",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
        )

        Spacer(Modifier.height(16.dp))

        topics.forEach { topic ->
            TopicCard(
                title = topic,
                onClick = { navController.navigate(Routes.practiceTopicRoute(subject, topic)) }
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun TopicCard(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun PracticeTopicScreen(
    navController: NavController,
    subject: String,
    topic: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(subject, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text(topic, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "This is a placeholder screen for: $topic.\nNext you can add:\n• lesson summary\n• practice questions\n• quiz mode",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}

/* ---------------------------
   CALM
---------------------------- */

@Composable
fun CalmScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column {
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
                overflow = TextOverflow.Clip
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = description,
                color = Color.White.copy(alpha = 0.9f),
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 4,
                overflow = TextOverflow.Clip
            )
        }
    }
}

/* ---------------------------
   CHAT
---------------------------- */

@Composable
fun ChatScreen(navController: NavController, modifier: Modifier = Modifier) {
    val messages = listOf(
        ChatMsg("assistant", "Salut! Cu ce te pot ajuta pentru Evaluarea Națională?"),
        ChatMsg("user", "Nu înțeleg fracțiile. Poți explica?"),
        ChatMsg("assistant", "Sigur. O fracție arată o parte dintr-un întreg. Exemplu: 3/4 înseamnă 3 părți din 4."),
        ChatMsg("user", "Și cum compar 2/3 cu 3/5?"),
        ChatMsg("assistant", "Le aduci la același numitor sau folosești înmulțirea în cruce. Hai să facem împreună.")
    )

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(12.dp)
        ) {
            messages.forEach { msg ->
                ChatBubble(isUser = msg.role == "user", text = msg.text)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ChatChips()
    }
}

private data class ChatMsg(val role: String, val text: String)

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
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(text = label, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

/* ---------------------------
   PROGRESS
---------------------------- */

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

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        // Latest results
        Text("Latest results", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            ResultBlock(
                title = "Matematică",
                score = latestMathScore,
                hint = "Based on your last quiz",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            ResultBlock(
                title = "Română",
                score = latestRomanianScore,
                hint = "Based on your last quiz",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Suggestions
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            Text("Suggestions", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("• Revise fractions and percentages.")
            Text("• Do 10 grammar questions daily (acord + punctuație).")
            Text("• Try one full test every weekend.")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // This week
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            DonutChart(slices = sections, modifier = Modifier.size(140.dp))
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text("This week", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(10.dp))
                sections.forEach { s ->
                    LegendRow(label = s.label, color = s.color, percent = s.value)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Skill progress split into 2 blocks
        Text("Skill progress", fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            SkillBlock(
                title = "Matematică",
                skills = mathSkills,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            SkillBlock(
                title = "Română",
                skills = romanianSkills,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private data class ProgressSlice(val label: String, val value: Float, val color: Color)
private data class SkillRow(val name: String, val value: Float)

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
        Text(label, modifier = Modifier.weight(1f))
        Text("${(percent * 100).toInt()}%")
    }
}

@Composable
private fun SkillProgressRow(title: String, value: Float) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(title, modifier = Modifier.weight(1f))
            Text("${(value * 100).toInt()}%")
        }
        Spacer(modifier = Modifier.height(6.dp))

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
                    .background(Color(0xFF7B6CF6))
            )
        }
    }
}

/* ---------------------------
   HELPERS (THESE FIX THE "UNRESOLVED REFERENCE" ERRORS)
---------------------------- */

@Composable
private fun ResultBlock(
    title: String,
    score: Int,
    hint: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(title, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Score: $score/100",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = hint,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun SkillBlock(
    title: String,
    skills: List<SkillRow>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(title, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(12.dp))

        skills.forEachIndexed { index, row ->
            SkillProgressRow(row.name, row.value)
            if (index != skills.lastIndex) Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
