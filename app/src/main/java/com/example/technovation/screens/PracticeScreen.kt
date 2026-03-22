package com.example.technovation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.technovation.Routes

/* ============================================================
   PRACTICE MAIN
   ============================================================ */

private val romanianVariants = listOf(
    "Varianta 1",
    "Varianta 2",
    "Varianta 3",
    "Varianta 4",
    "Varianta 5"
)

private val mathVariants = listOf(
    "Varianta 1",
    "Varianta 2",
    "Varianta 3",
    "Varianta 4",
    "Varianta 5"
)

@Composable
fun PracticeScreen(navController: NavController, modifier: Modifier = Modifier) {
    ScreenColumn(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Choose a subject",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        PracticeTile(
            title = "Limba română",
            subtitle = "Variante EN 1–5",
            background = Color(0xFF5B4BB7),
            onClick = { navController.navigate(Routes.practiceMenuRoute("Limba română")) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        PracticeTile(
            title = "Matematică",
            subtitle = "Variante EN 1–5",
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
            .background(background.copy(alpha = 0.96f), RoundedCornerShape(26.dp))
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
                    .background(Color.White.copy(alpha = 0.18f), RoundedCornerShape(999.dp))
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
    val variants = remember(subject) {
        when (subject) {
            "Limba română" -> romanianVariants
            "Matematică" -> mathVariants
            else -> listOf("Varianta 1")
        }
    }

    ScreenColumn(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TopBackBar(
            title = subject,
            subtitle = "Choose a variant",
            onBack = { navController.popBackStack() }
        )
        Spacer(Modifier.height(12.dp))

        variants.forEach { variant ->
            TopicCard(
                title = variant,
                onClick = { navController.navigate(Routes.practiceTopicRoute(subject, variant)) }
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
    CardSurface(modifier = Modifier.clickable { onClick() }) {
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                Text("Start Quiz", fontWeight = FontWeight.Bold, maxLines = 1)
            }
        }
    }
}

/* ============================================================
   QUIZ
   ============================================================ */

private data class QuizQuestion(
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

private data class QuestionReference(
    val label: String,
    val text: String
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

    val optionTextColor = when (state) {
        OptionState.Correct, OptionState.Wrong -> Color.Black
        else -> MaterialTheme.colorScheme.onSurface
    }

    val labelBoxColor = when (state) {
        OptionState.Correct -> Color(0xFFB8E6C7)
        OptionState.Wrong -> Color(0xFFF1B7B7)
        OptionState.Normal -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
    }

    val labelTextColor = when (state) {
        OptionState.Correct, OptionState.Wrong -> Color.Black
        else -> MaterialTheme.colorScheme.onSurface
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor, RoundedCornerShape(18.dp))
            .border(2.dp, borderColor, RoundedCornerShape(18.dp))
            .clickable(enabled = !locked) { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .background(labelBoxColor, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                color = labelTextColor
            )
        }

        Spacer(Modifier.height(0.dp))
        Spacer(modifier = Modifier.padding(horizontal = 6.dp))

        Text(
            text = text,
            modifier = Modifier.weight(1f),
            color = optionTextColor,
            maxLines = 8,
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
    var showReferenceDialog by remember { mutableStateOf(false) }

    if (questions.isEmpty()) {
        ScreenColumn(modifier = modifier.padding(16.dp)) {
            TopBackBar(title = "Quiz", subtitle = "$subject • $topic", onBack = { navController.popBackStack() })
            Spacer(Modifier.height(12.dp))
            CardSurface { Text("No quiz available yet for this variant.") }
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
                showReferenceDialog = false
            },
            onBack = { navController.popBackStack() }
        )
        return
    }

    val q = questions[index]
    val total = questions.size
    val progress = (index + 1).toFloat() / total.toFloat()
    val labels = listOf("A", "B", "C", "D")
    val reference = questionReferenceFor(subject, topic, index)

    if (showReferenceDialog && reference != null) {
        AlertDialog(
            onDismissRequest = { showReferenceDialog = false },
            confirmButton = {
                TextButton(onClick = { showReferenceDialog = false }) {
                    Text("Close")
                }
            },
            title = {
                Text(
                    text = reference.label,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 420.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = reference.text,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                    )
                }
            },
            shape = RoundedCornerShape(24.dp)
        )
    }

    ScreenColumn(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TopBackBar(title = "Quiz", subtitle = "$subject • $topic", onBack = { navController.popBackStack() })
        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Pill("${index + 1} / $total")
            Pill("Score: $correctCount")
        }

        Spacer(Modifier.height(10.dp))
        ProgressBar(progress)

        if (reference != null) {
            Spacer(Modifier.height(14.dp))
            OutlinedButton(
                onClick = { showReferenceDialog = true },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Read text", fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(Modifier.height(14.dp))

        CardSurface {
            Text("Question ${index + 1}", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(
                q.prompt,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 12,
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
                    maxLines = 12,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.height(12.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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
                        showReferenceDialog = false
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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
   QUESTION REFERENCES
   ============================================================ */

private fun questionReferenceFor(
    subject: String,
    topic: String,
    questionIndex: Int
): QuestionReference? {
    return when (subject) {
        "Limba română" -> {
            when (topic) {
                "Varianta 1" -> {
                    val textA = """
Un băiețel îmbrăcat elegant, cu pălărie de paie și bilet prins la piept, stătea lângă mama și bunica lui în gară. Cele două doamne îl priveau cu mândrie și îi explicau mereu ce trebuie să facă „ca un domn adevărat”.

Când trenul a sosit, copilul a început să alerge agitat pe peron, atingând bagajele oamenilor și punând întrebări tuturor. Mama îl ruga să stea liniștit, dar el răspundea obraznic și se lăuda că merge singur la București.

În vagon, băiatul s-a așezat lângă fereastră și a început să tragă semnalul de alarmă, crezând că este o jucărie. Pasagerii s-au speriat, iar conductorul a venit imediat. Cele două doamne au dat vina pe personalul trenului și au spus că băiatul este foarte bine crescut.
                    """.trimIndent()

                    val textB = """
Educația copiilor începe în familie și se formează prin reguli clare și consecvente. Specialiștii arată că lipsa limitelor îi face pe copii să creadă că orice comportament este acceptabil.

Atunci când adulții scuză permanent greșelile copiilor, aceștia nu învață responsabilitatea faptelor lor. În schimb, explicarea consecințelor îi ajută să înțeleagă normele sociale.

Copiii care primesc îndrumare echilibrată se adaptează mai ușor la mediul școlar și social.
                    """.trimIndent()

                    when (questionIndex) {
                        0, 1, 2, 3 -> QuestionReference("Textul A", textA)
                        4 -> QuestionReference("Textul B", textB)
                        5, 6, 7 -> QuestionReference("Textul A", textA)
                        else -> null
                    }
                }

                "Varianta 2" -> {
                    val textA = """
În luminiș, prepelița își strângea puii aproape de ea. Îi învăța să caute hrană și să se ferească de primejdii. Unul dintre pui era însă mai zburdalnic și se îndepărta adesea.

Într-o zi, un zgomot puternic s-a auzit din pădure. Mama a fugit repede și i-a chemat sub aripi. Toți au ascultat, numai puiul neascultător a rămas în urmă, curios.

După ce liniștea s-a așternut, prepelița l-a căutat îndelung. L-a găsit rănit și tremurând. L-a încălzit și l-a ocrotit, deși el nu înțelesese la timp primejdia.
                    """.trimIndent()

                    val textB = """
Specialiștii afirmă că animalele își protejează instinctiv puii. În primele săptămâni de viață, aceștia depind complet de părinți pentru hrană și siguranță.

Observațiile biologilor arată că puii care nu respectă semnalele de avertizare sunt mai expuși pericolelor. Învățarea prin experiență este esențială pentru supraviețuire.

De aceea, comportamentul părinților are rol decisiv în dezvoltarea și adaptarea puilor la mediul înconjurător.
                    """.trimIndent()

                    when (questionIndex) {
                        0, 1, 2 -> QuestionReference("Textul A", textA)
                        3, 4 -> QuestionReference("Textul B", textB)
                        5, 6, 7 -> QuestionReference("Textul A", textA)
                        else -> null
                    }
                }

                "Varianta 3" -> {
                    val textA = """
Într-o dimineață de vară, Nică a ieșit tiptil din casă, știind că mama nu i-ar da voie să plece la scăldat. Soarele încă nu urcase bine pe cer, iar satul era liniștit.

Ajuns la râu, băiatul și-a aruncat hainele pe iarbă și a intrat repede în apă. Se juca, stropea și râdea fără grijă.

Deodată, a auzit glasul mamei chemându-l de departe. Speriat, a ieșit în grabă, dar hainele nu mai erau unde le lăsase. Atunci a înțeles că joaca lui urma să aibă urmări.
                    """.trimIndent()

                    val textB = """
Copilăria este perioada în care curiozitatea îi determină pe copii să exploreze mediul înconjurător. Psihologii spun că dorința de aventură este normală și contribuie la dezvoltare.

Totuși, lipsa supravegherii poate duce la situații neplăcute sau periculoase. De aceea, adulții stabilesc reguli pentru protecția copiilor.

Respectarea regulilor ajută copilul să înțeleagă responsabilitatea propriilor acțiuni.
                    """.trimIndent()

                    when (questionIndex) {
                        0, 1, 2 -> QuestionReference("Textul A", textA)
                        3, 4 -> QuestionReference("Textul B", textB)
                        5, 6, 7 -> QuestionReference("Textul A", textA)
                        else -> null
                    }
                }

                "Varianta 4" -> {
                    val textA = """
Sub cerul cald de vară lin,
Pădurea doarme legănată,
Iar vântul trece încetinind
Prin iarba verde parfumată.

Pe lac se oglindește luna,
Și noaptea pare fără glas,
Doar greierii își spun într-una
Povestea lor la ceas de ceas.
                    """.trimIndent()

                    val textB = """
Natura are un efect benefic asupra stării emoționale a oamenilor. Specialiștii afirmă că sunetele liniștite și peisajele naturale reduc stresul și cresc capacitatea de concentrare.

Observarea cerului, a apei sau a pădurii creează o stare de calm și echilibru interior. Din acest motiv, plimbările în natură sunt recomandate pentru relaxare.
                    """.trimIndent()

                    when (questionIndex) {
                        0, 1, 2, 3 -> QuestionReference("Textul A", textA)
                        4 -> QuestionReference("Textul B", textB)
                        5, 6, 7 -> QuestionReference("Textul A", textA)
                        else -> null
                    }
                }

                "Varianta 5" -> {
                    val textA = """
Într-o după-amiază, naratorul merge în vizită la o prietenă care avea un băiețel foarte răsfățat. Copilul nu stătea locului nicio clipă: trăgea de perdele, răsturna scaunele și punea întrebări fără oprire.

Mama îl lăuda mereu și spunea că este foarte inteligent. În timp ce musafirul încerca să poarte o conversație, băiatul îi golea buzunarele și îi examina lucrurile cu mare curiozitate.

La plecare, copilul a spart o ceașcă, iar mama a dat vina pe servitoare. Musafirul a plecat grăbit, promițându-și să nu mai revină prea curând.
                    """.trimIndent()

                    val textB = """
Specialiștii afirmă că limitele sunt esențiale în educația copiilor. Lipsa regulilor poate duce la comportamente neadecvate în societate.

Copiii învață prin consecințe și prin exemplele adulților. Atitudinea părinților influențează modul în care aceștia interacționează cu ceilalți oameni.
                    """.trimIndent()

                    when (questionIndex) {
                        0, 1, 2 -> QuestionReference("Textul A", textA)
                        3, 4 -> QuestionReference("Textul B", textB)
                        else -> null
                    }
                }

                else -> null
            }
        }

        else -> null
    }
}

/* ============================================================
   QUESTION BANK
   ============================================================ */

private fun quizQuestionsFor(subject: String, topic: String): List<QuizQuestion> {
    return when (subject) {
        "Limba română" -> romanianVariantQuiz(topic)
        "Matematică" -> mathVariantQuiz(topic)
        else -> emptyList()
    }
}

/* -------------------- ROMANIAN VARIANTS -------------------- */

private fun romanianVariantQuiz(variant: String): List<QuizQuestion> {
    return when (variant) {
        "Varianta 1" -> romanianVariant1()
        "Varianta 2" -> romanianVariant2()
        "Varianta 3" -> romanianVariant3()
        "Varianta 4" -> romanianVariant4()
        "Varianta 5" -> romanianVariant5()
        else -> romanianVariant1()
    }
}

private fun romanianVariant1(): List<QuizQuestion> = listOf(
    QuizQuestion(
        prompt = "În textul „Domnul Goe…”, sensul cuvântului „agitat” este:",
        options = listOf("liniștit", "neliniștit / fără astâmpăr", "politicos", "somnoros"),
        correctIndex = 1,
        explanation = "„Agitat” înseamnă neliniștit, nervos, energic, fără astâmpăr."
    ),
    QuizQuestion(
        prompt = "Cine îl însoțește pe copil la gară?",
        options = listOf("mama și bunica", "tatăl și unchiul", "doar bunica", "mama și conductorul"),
        correctIndex = 0,
        explanation = "Copilul este însoțit de mama și bunica lui."
    ),
    QuizQuestion(
        prompt = "De ce vine conductorul în compartiment?",
        options = listOf(
            "copilul a spart fereastra",
            "copilul a tras semnalul de alarmă",
            "trenul s-a oprit în gară",
            "mama a cerut ajutor"
        ),
        correctIndex = 1,
        explanation = "Conductorul vine deoarece copilul a tras semnalul de alarmă."
    ),
    QuizQuestion(
        prompt = "Care este o trăsătură morală potrivită pentru băiat?",
        options = listOf("neascultător", "timid", "harnic", "sincer"),
        correctIndex = 0,
        explanation = "Băiatul este neascultător și obraznic: nu stă liniștit și răspunde urât."
    ),
    QuizQuestion(
        prompt = "Care este ideea principală din al doilea paragraf al textului B?",
        options = listOf(
            "călătoria cu trenul este utilă",
            "scuzarea permanentă a copiilor îi împiedică să învețe responsabilitatea",
            "copiii trebuie lăudați mereu",
            "regulile sunt inutile"
        ),
        correctIndex = 1,
        explanation = "Aceasta este ideea principală cerută și în barem."
    ),
    QuizQuestion(
        prompt = "Verbul „a venit” este la:",
        options = listOf(
            "indicativ, perfect compus",
            "indicativ, imperfect",
            "conjunctiv, prezent",
            "imperativ"
        ),
        correctIndex = 0,
        explanation = "„A venit” este la modul indicativ, timpul perfect compus."
    ),
    QuizQuestion(
        prompt = "Transformarea la plural pentru „Băiatul a tras semnalul de alarmă.” este:",
        options = listOf(
            "Băieții a tras semnalele de alarmă.",
            "Băieții au tras semnalele de alarmă.",
            "Băieții au tras semnalul de alarmă.",
            "Băiatul au tras semnalele de alarmă."
        ),
        correctIndex = 1,
        explanation = "Aceasta este forma corectă la plural."
    ),
    QuizQuestion(
        prompt = "Funcția sintactică a grupului „în vagon” este:",
        options = listOf("atribut", "nume predicativ", "complement circumstanțial de loc", "subiect"),
        correctIndex = 2,
        explanation = "Arată locul, deci este complement circumstanțial de loc."
    )
)

private fun romanianVariant2(): List<QuizQuestion> = listOf(
    QuizQuestion(
        prompt = "În textul despre „Puiul”, secvența „s-a așternut liniștea” înseamnă:",
        options = listOf("a început furtuna", "s-a făcut liniște", "puiul a plecat", "mama a strigat"),
        correctIndex = 1,
        explanation = "Sensul este „s-a făcut liniște / s-a făcut tăcere”."
    ),
    QuizQuestion(
        prompt = "O trăsătură a prepeliței este:",
        options = listOf("indiferentă", "protectoare", "leneșă", "răutăcioasă"),
        correctIndex = 1,
        explanation = "Prepelița este protectoare: își strânge puii, îi cheamă sub aripi și îl ocrotește pe puiul rănit."
    ),
    QuizQuestion(
        prompt = "De ce rămâne puiul în urmă?",
        options = listOf("era bolnav", "era curios / neascultător", "s-a rătăcit din întâmplare", "dormea"),
        correctIndex = 1,
        explanation = "Puiul rămâne în urmă fiind curios și neascultător."
    ),
    QuizQuestion(
        prompt = "Conform textului B, puii care nu respectă semnalele de avertizare sunt:",
        options = listOf("mai liniștiți", "mai puternici", "mai expuși pericolelor", "mai apreciați"),
        correctIndex = 2,
        explanation = "Textul informativ spune clar că sunt mai expuși pericolelor."
    ),
    QuizQuestion(
        prompt = "Rolul părinților conform textului B este să asigure:",
        options = listOf("doar jocul", "protecția și învățarea", "doar hrana", "doar libertatea totală"),
        correctIndex = 1,
        explanation = "Părinții au rol decisiv în protecție, dezvoltare și învățare."
    ),
    QuizQuestion(
        prompt = "Verbul „a fugit” este la:",
        options = listOf("indicativ, perfect compus", "indicativ, imperfect", "conjunctiv", "imperativ"),
        correctIndex = 0,
        explanation = "„A fugit” este tot indicativ, perfect compus."
    ),
    QuizQuestion(
        prompt = "Transformarea la plural pentru „Puiul nu ascultă semnalul.” este:",
        options = listOf(
            "Puii nu ascultă semnalele.",
            "Puii nu ascultă semnalul.",
            "Puiul nu ascultă semnalele.",
            "Puii nu ascultă semnalelor."
        ),
        correctIndex = 0,
        explanation = "Forma corectă este „Puii nu ascultă semnalele.”"
    ),
    QuizQuestion(
        prompt = "În enunțul „Puiul este curios.”, adjectivul „curios” este:",
        options = listOf("atribut", "complement direct", "nume predicativ", "subiect"),
        correctIndex = 2,
        explanation = "După verbul copulativ „este”, adjectivul are funcția de nume predicativ."
    )
)

private fun romanianVariant3(): List<QuizQuestion> = listOf(
    QuizQuestion(
        prompt = "Secvența „a ieșit tiptil” înseamnă:",
        options = listOf("foarte repede", "pe furiș / fără zgomot", "cu mare curaj", "cu întârziere"),
        correctIndex = 1,
        explanation = "„Tiptil” înseamnă pe furiș, fără zgomot."
    ),
    QuizQuestion(
        prompt = "Unde merge Nică?",
        options = listOf("la școală", "la târg", "la râu / la scăldat", "la bunici"),
        correctIndex = 2,
        explanation = "Nică pleacă la scăldat, la râu."
    ),
    QuizQuestion(
        prompt = "De ce pleacă fără voie?",
        options = listOf(
            "pentru că s-a rătăcit",
            "pentru că mama nu i-ar da voie",
            "pentru că îl cheamă profesorul",
            "pentru că merge la cumpărături"
        ),
        correctIndex = 1,
        explanation = "Textul spune clar că știa că mama nu i-ar da voie."
    ),
    QuizQuestion(
        prompt = "Care este ideea principală din al doilea paragraf al textului B?",
        options = listOf(
            "lipsa supravegherii poate duce la pericole",
            "vara este frumoasă",
            "râurile sunt periculoase",
            "copiii nu trebuie să se joace"
        ),
        correctIndex = 0,
        explanation = "Acesta este mesajul central al paragrafului al doilea."
    ),
    QuizQuestion(
        prompt = "Rolul regulilor pentru copii este să ofere:",
        options = listOf("doar distracție", "protecție și responsabilitate", "pedepse", "interdicții fără sens"),
        correctIndex = 1,
        explanation = "Regulile îi protejează și îi învață responsabilitatea."
    ),
    QuizQuestion(
        prompt = "Textul este narativ deoarece:",
        options = listOf(
            "prezintă doar sentimente",
            "prezintă o succesiune de întâmplări cu personaje și narator",
            "conține doar informații științifice",
            "nu are acțiune"
        ),
        correctIndex = 1,
        explanation = "Aceasta este formula-schelet pentru argumentarea textului narativ."
    ),
    QuizQuestion(
        prompt = "Una dintre cele 3 dovezi pentru textul narativ este:",
        options = listOf("prezența unei formule matematice", "prezența unui narator", "numărul de strofe", "rima"),
        correctIndex = 1,
        explanation = "Cele 3 dovezi sunt: acțiune, personaje, narator."
    ),
    QuizQuestion(
        prompt = "Funcția sintactică a cuvântului „repede” din text este:",
        options = listOf("complement circumstanțial de mod", "atribut", "subiect", "nume predicativ"),
        correctIndex = 0,
        explanation = "Arată modul în care se desfășoară acțiunea."
    )
)

private fun romanianVariant4(): List<QuizQuestion> = listOf(
    QuizQuestion(
        prompt = "În poezia dată, sensul cuvântului „lin” este:",
        options = listOf("aspru", "liniștit / calm", "puternic", "întunecat"),
        correctIndex = 1,
        explanation = "„Lin” înseamnă calm, liniștit."
    ),
    QuizQuestion(
        prompt = "Două elemente ale naturii prezente în poezie sunt:",
        options = listOf(
            "trenul și gara",
            "pădurea și lacul",
            "camera și fereastra",
            "drumul și orașul"
        ),
        correctIndex = 1,
        explanation = "În poezie apar pădurea, lacul, luna, iarba, cerul."
    ),
    QuizQuestion(
        prompt = "Momentul zilei surprins în textul A este:",
        options = listOf("dimineața", "amiaza", "noaptea", "după-amiaza"),
        correctIndex = 2,
        explanation = "Apar luna, noaptea și greierii."
    ),
    QuizQuestion(
        prompt = "Starea transmisă de peisaj este:",
        options = listOf("agitație", "liniște / pace", "furie", "teamă"),
        correctIndex = 1,
        explanation = "Peisajul transmite calm și echilibru."
    ),
    QuizQuestion(
        prompt = "Conform textului B, natura are asupra oamenilor un efect de:",
        options = listOf("stres", "obosire", "calm și reducere a stresului", "panică"),
        correctIndex = 2,
        explanation = "Textul informativ spune că natura reduce stresul și crește concentrarea."
    ),
    QuizQuestion(
        prompt = "Mesajul textului liric se obține prin metoda:",
        options = listOf("VERS → AUTOR", "SENTIMENT → IDEE DE VIAȚĂ", "RIMĂ → STROFĂ", "TIMP → SPAȚIU"),
        correctIndex = 1,
        explanation = "Aceasta este metoda din ghid."
    ),
    QuizQuestion(
        prompt = "Care formulare este corectă într-o analiză lirică?",
        options = listOf(
            "poetul este trist pentru că...",
            "textul sugerează că...",
            "autorul apare ca personaj",
            "poezia nu transmite nimic"
        ),
        correctIndex = 1,
        explanation = "Nu spui „poetul este trist”, ci „textul sugerează că...”."
    ),
    QuizQuestion(
        prompt = "În analiza textului liric vorbești despre:",
        options = listOf("eul liric / vocea ficțională", "naratorul omniscient obligatoriu", "conductor", "personaj principal realist"),
        correctIndex = 0,
        explanation = "În textul liric folosești eul liric sau vocea ficțională."
    )
)

private fun romanianVariant5(): List<QuizQuestion> = listOf(
    QuizQuestion(
        prompt = "Expresia „nu stătea locului” înseamnă:",
        options = listOf("era foarte cuminte", "era agitat / nu se oprea", "era obosit", "era atent"),
        correctIndex = 1,
        explanation = "În context, înseamnă că nu se oprea și era foarte agitat."
    ),
    QuizQuestion(
        prompt = "Care dintre următoarele este o acțiune a copilului din „Vizită…”?",
        options = listOf("citea liniștit", "trăgea de perdele", "scria o scrisoare", "dormea"),
        correctIndex = 1,
        explanation = "Copilul trăgea de perdele, răsturna scaunele și punea întrebări."
    ),
    QuizQuestion(
        prompt = "Atitudinea mamei față de copil este că:",
        options = listOf("îl ceartă mereu", "îl laudă și îl răsfață", "îl ignoră", "îl pedepsește"),
        correctIndex = 1,
        explanation = "Mama îl laudă și îi scuză comportamentul."
    ),
    QuizQuestion(
        prompt = "În rezumat trebuie să folosești:",
        options = listOf(
            "opinii personale",
            "citate din text",
            "persoana a III-a și ideile principale",
            "analiza sentimentelor"
        ),
        correctIndex = 2,
        explanation = "Rezumatul cere obiectivitate, idei principale, ordine și persoana a III-a."
    ),
    QuizQuestion(
        prompt = "Care regulă de aur pentru rezumat este corectă?",
        options = listOf(
            "interpretezi textul",
            "comentezi finalul",
            "scrii doar faptele importante",
            "adaugi dialog inventat"
        ),
        correctIndex = 2,
        explanation = "Rezumatul înseamnă fapte pe scurt, fără comentarii sau interpretări."
    ),
    QuizQuestion(
        prompt = "Formula de aur pentru argumentare este:",
        options = listOf(
            "afirmație + explicație + exemplu",
            "titlu + citat + concluzie",
            "idee + idee + idee",
            "dialog + comentariu"
        ),
        correctIndex = 0,
        explanation = "Aceasta este structura corectă pentru argumentare."
    ),
    QuizQuestion(
        prompt = "Corectorii apreciază mai ales:",
        options = listOf(
            "răspunsuri foarte lungi",
            "claritate, ordine și scris lizibil",
            "termeni foarte complicați",
            "pagini întregi la fiecare răspuns"
        ),
        correctIndex = 1,
        explanation = "În ghid apare exact: claritate + ordine + scris lizibil."
    ),
    QuizQuestion(
        prompt = "Profesorii caută în primul rând:",
        options = listOf("structură clară", "citări multe", "biografia autorului", "fraze cât mai lungi"),
        correctIndex = 0,
        explanation = "Secretul profilor: caută structură clară."
    )
)

/* -------------------- MATH VARIANTS -------------------- */

private fun mathVariantQuiz(variant: String): List<QuizQuestion> {
    return when (variant) {
        "Varianta 1" -> mathVariant1()
        "Varianta 2" -> mathVariant2()
        "Varianta 3" -> mathVariant3()
        "Varianta 4" -> mathVariant4()
        "Varianta 5" -> mathVariant5()
        else -> mathVariant1()
    }
}

private fun mathVariant1(): List<QuizQuestion> = listOf(
    QuizQuestion(
        prompt = "Rezultatul calculului (36 - 4·5):2 + 9 este:",
        options = listOf("11", "13", "17", "19"),
        correctIndex = 2,
        explanation = "(36 - 20):2 + 9 = 16:2 + 9 = 8 + 9 = 17."
    ),
    QuizQuestion(
        prompt = "Dacă x / 16 = 3 / 4, atunci x este:",
        options = listOf("12", "16", "20", "24"),
        correctIndex = 0,
        explanation = "x = 16 × 3 / 4 = 12."
    ),
    QuizQuestion(
        prompt = "Reuniunea A ∪ B pentru A={1,2,3,4,5,6} și B={3,6,9} este:",
        options = listOf(
            "{3,6}",
            "{1,2,4,5}",
            "{1,2,3,4,5,6,9}",
            "{1,2,3,4,5,6}"
        ),
        correctIndex = 2,
        explanation = "La reuniune punem toate elementele distincte din ambele mulțimi."
    ),
    QuizQuestion(
        prompt = "Scrierea zecimală a fracției 7/8 este:",
        options = listOf("0,7", "0,875", "0,(875)", "0,87(5)"),
        correctIndex = 1,
        explanation = "7 ÷ 8 = 0,875."
    ),
    QuizQuestion(
        prompt = "Dacă a = 2^4 + 6 și b = 34, media aritmetică este:",
        options = listOf("25", "28", "30", "26"),
        correctIndex = 1,
        explanation = "a = 16 + 6 = 22, iar (22 + 34)/2 = 28."
    ),
    QuizQuestion(
        prompt = "Pentru a = -3 + 5√2 - 5√2, afirmația „a este negativ” este:",
        options = listOf("adevărată", "falsă", "nu se poate stabili", "a este pozitiv"),
        correctIndex = 0,
        explanation = "5√2 - 5√2 = 0, deci a = -3, care este negativ."
    ),
    QuizQuestion(
        prompt = "Pe un cerc, dacă arcul mic AB are 60°, unghiul la centru corespunzător este:",
        options = listOf("30°", "60°", "120°", "180°"),
        correctIndex = 1,
        explanation = "Unghiul la centru are aceeași măsură ca arcul corespunzător."
    ),
    QuizQuestion(
        prompt = "Un tetraedru regulat are muchia 6 cm. Aria totală este:",
        options = listOf("9√3 cm²", "36√3 cm²", "54√3 cm²", "72√3 cm²"),
        correctIndex = 1,
        explanation = "Aria unei fețe este (√3/4)·6² = 9√3, iar aria totală este 4·9√3 = 36√3."
    )
)

private fun mathVariant2(): List<QuizQuestion> = listOf(
    QuizQuestion(
        prompt = "(72 - 8·7):4 + 6 =",
        options = listOf("8", "9", "10", "12"),
        correctIndex = 2,
        explanation = "72 - 56 = 16, 16 : 4 = 4, iar 4 + 6 = 10."
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
        explanation = "3/5 = 6/10, iar 6/10 + 7/10 = 13/10."
    ),
    QuizQuestion(
        prompt = "Media aritmetică a numerelor 18 și 26 este:",
        options = listOf("20", "21", "22", "23"),
        correctIndex = 2,
        explanation = "(18 + 26) / 2 = 22."
    ),
    QuizQuestion(
        prompt = "√98 =",
        options = listOf("7√2", "14√2", "49√2", "2√49"),
        correctIndex = 0,
        explanation = "√98 = √(49·2) = 7√2."
    ),
    QuizQuestion(
        prompt = "Pentru a = √27 - 3√3, afirmația „a = 0” este:",
        options = listOf("adevărată", "falsă", "a este pozitiv", "a este negativ"),
        correctIndex = 0,
        explanation = "√27 = 3√3, deci a = 0."
    ),
    QuizQuestion(
        prompt = "Volumul unui cub de muchie 4 cm este:",
        options = listOf("16", "32", "48", "64"),
        correctIndex = 3,
        explanation = "4³ = 64."
    ),
    QuizQuestion(
        prompt = "Într-un triunghi dreptunghic cu catetele 9 și 12, ipotenuza este:",
        options = listOf("13", "14", "15", "16"),
        correctIndex = 2,
        explanation = "√(9²+12²)=√225=15."
    )
)

private fun mathVariant3(): List<QuizQuestion> = listOf(
    QuizQuestion(
        prompt = "(48 - 6·5):3 + 7 =",
        options = listOf("9", "11", "13", "15"),
        correctIndex = 2,
        explanation = "(48 - 30):3 + 7 = 18:3 + 7 = 6 + 7 = 13."
    ),
    QuizQuestion(
        prompt = "Dacă x/18 = 5/6, atunci x =",
        options = listOf("12", "15", "18", "20"),
        correctIndex = 1,
        explanation = "x = 18 × 5 / 6 = 15."
    ),
    QuizQuestion(
        prompt = "A={1,2,3,4,5,6}, B={2,4,8}. Reuniunea A∪B este:",
        options = listOf("{2,4}", "{1,2,3,4,5,6,8}", "{1,3,5,6,8}", "{1,2,3,4,5,6}"),
        correctIndex = 1,
        explanation = "La reuniune punem toate elementele distincte."
    ),
    QuizQuestion(
        prompt = "3/20 scris zecimal este:",
        options = listOf("0,15", "0,2", "0,03", "0,015"),
        correctIndex = 0,
        explanation = "3 ÷ 20 = 0,15."
    ),
    QuizQuestion(
        prompt = "Dacă a=2^5-4 și b=26, media aritmetică (a+b)/2 este:",
        options = listOf("24", "25", "26", "27"),
        correctIndex = 3,
        explanation = "a = 32 - 4 = 28, iar (28 + 26)/2 = 27."
    ),
    QuizQuestion(
        prompt = "Pentru a = √50 - √18 - √8, afirmația „a = 0” este:",
        options = listOf("adevărată", "falsă", "a este pozitiv", "a este negativ"),
        correctIndex = 0,
        explanation = "√50 = 5√2, √18 = 3√2, √8 = 2√2, deci a = 0."
    ),
    QuizQuestion(
        prompt = "Triunghi dreptunghic ABC în A, cu ∠C=30° și BC=16 cm. Atunci AB =",
        options = listOf("8 cm", "8√3 cm", "16 cm", "16√3 cm"),
        correctIndex = 0,
        explanation = "Într-un triunghi dreptunghic, cateta opusă unghiului de 30° este jumătate din ipotenuză."
    ),
    QuizQuestion(
        prompt = "Pe un cerc, arcul mic AB are 120°. Unghiul înscris care îl subîntinde este:",
        options = listOf("30°", "60°", "90°", "120°"),
        correctIndex = 1,
        explanation = "Unghiul înscris este jumătate din măsura arcului."
    )
)

private fun mathVariant4(): List<QuizQuestion> = listOf(
    QuizQuestion(
        prompt = "(54 - 6·7):3 + 5 =",
        options = listOf("7", "9", "11", "13"),
        correctIndex = 1,
        explanation = "(54 - 42):3 + 5 = 12:3 + 5 = 4 + 5 = 9."
    ),
    QuizQuestion(
        prompt = "Dacă x/24 = 3/8, atunci x =",
        options = listOf("6", "9", "12", "15"),
        correctIndex = 1,
        explanation = "x = 24 × 3 / 8 = 9."
    ),
    QuizQuestion(
        prompt = "5/4 - 3/8 =",
        options = listOf("5/8", "7/8", "9/8", "11/8"),
        correctIndex = 1,
        explanation = "5/4 = 10/8, iar 10/8 - 3/8 = 7/8."
    ),
    QuizQuestion(
        prompt = "Media numerelor 12 și 20 este:",
        options = listOf("14", "15", "16", "18"),
        correctIndex = 2,
        explanation = "(12 + 20)/2 = 16."
    ),
    QuizQuestion(
        prompt = "√72 =",
        options = listOf("6√2", "4√3", "3√8", "12√2"),
        correctIndex = 0,
        explanation = "√72 = √(36·2) = 6√2."
    ),
    QuizQuestion(
        prompt = "Pentru a = √32 - √8, afirmația „a este număr natural” este:",
        options = listOf("adevărată", "falsă", "a=0", "a<0"),
        correctIndex = 1,
        explanation = "√32 = 4√2 și √8 = 2√2, deci a = 2√2, care nu este număr natural."
    ),
    QuizQuestion(
        prompt = "Perimetrul unui pătrat de latură 7 cm este:",
        options = listOf("14", "21", "28", "49"),
        correctIndex = 2,
        explanation = "P = 4 × 7 = 28."
    ),
    QuizQuestion(
        prompt = "Aria unui dreptunghi de 12 cm și 4 cm este:",
        options = listOf("32", "40", "48", "56"),
        correctIndex = 2,
        explanation = "A = L × l = 12 × 4 = 48."
    )
)

private fun mathVariant5(): List<QuizQuestion> = listOf(
    QuizQuestion(
        prompt = "(45 - 3·8):3 + 6 =",
        options = listOf("9", "11", "13", "15"),
        correctIndex = 2,
        explanation = "(45 - 24):3 + 6 = 21:3 + 6 = 7 + 6 = 13."
    ),
    QuizQuestion(
        prompt = "Dacă x/14 = 4/7, atunci x =",
        options = listOf("6", "8", "10", "12"),
        correctIndex = 1,
        explanation = "x = 14 × 4 / 7 = 8."
    ),
    QuizQuestion(
        prompt = "2^4 + 3^2 =",
        options = listOf("23", "25", "27", "29"),
        correctIndex = 1,
        explanation = "16 + 9 = 25."
    ),
    QuizQuestion(
        prompt = "Scrierea zecimală a lui 7/20 este:",
        options = listOf("0,35", "0,7", "0,07", "0,035"),
        correctIndex = 0,
        explanation = "7/20 = 0,35."
    ),
    QuizQuestion(
        prompt = "Media aritmetică a numerelor 18 și 26 este:",
        options = listOf("20", "21", "22", "23"),
        correctIndex = 2,
        explanation = "(18 + 26)/2 = 22."
    ),
    QuizQuestion(
        prompt = "√72 - √18 =",
        options = listOf("3√2", "4√2", "5√2", "6√2"),
        correctIndex = 0,
        explanation = "√72 = 6√2, √18 = 3√2, deci diferența este 3√2."
    ),
    QuizQuestion(
        prompt = "Perimetrul unui triunghi echilateral este 45 cm. Latura este:",
        options = listOf("10 cm", "12 cm", "15 cm", "18 cm"),
        correctIndex = 2,
        explanation = "45 / 3 = 15."
    ),
    QuizQuestion(
        prompt = "Într-un triunghi dreptunghic cu ipotenuza 10 cm și o catetă 6 cm, cealaltă catetă este:",
        options = listOf("6", "7", "8", "9"),
        correctIndex = 2,
        explanation = "Prin Pitagora: √(100 - 36) = √64 = 8."
    )
)