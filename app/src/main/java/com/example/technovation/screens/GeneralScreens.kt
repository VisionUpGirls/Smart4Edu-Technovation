package com.example.technovation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.technovation.R
import com.example.technovation.Routes
import androidx.compose.foundation.layout.size

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
                .background(Color.White, RoundedCornerShape(20.dp))
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

            errorState.value?.let {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = it, color = Color(0xFFB00020))
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .height(3.dp)
                .background(accentStripe, RoundedCornerShape(2.dp))
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
            Text("Log In", color = Color.White, fontWeight = FontWeight.Bold)
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
            Text("Sign Up", color = linkBlue, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SignUpScreen(navController: NavController, modifier: Modifier = Modifier) {
    ScreenColumn(modifier = modifier.padding(16.dp)) {
        TopBackBar(title = "Sign Up", onBack = { navController.popBackStack() })
        Spacer(modifier = Modifier.height(12.dp))
        CardSurface {
            Text("Sign Up (coming soon)", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Already have an account? Log in",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { navController.navigate(Routes.LOGIN) }
            )
        }
    }
}