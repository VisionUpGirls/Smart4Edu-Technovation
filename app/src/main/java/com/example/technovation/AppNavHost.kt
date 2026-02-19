package com.example.technovation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType

@Composable
fun AppNavHost(
    navController: NavHostController,
    isLoggedIn: Boolean,
    username: String,
    onLoggedIn: (String) -> Unit,
    lang: String,
    onToggleLanguage: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Routes.HOME else Routes.LOGIN,
        modifier = modifier
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                navController = navController,
                onLoggedIn = onLoggedIn
            )
        }

        composable(Routes.SIGNUP) { SignUpScreen(navController = navController) }

        composable(Routes.HOME) {
            HomeScreen(navController = navController, username = username)
        }

        composable(Routes.PRACTICE) { PracticeScreen(navController = navController) }
        composable(Routes.CALM) { CalmScreen(navController = navController) }
        composable(Routes.CHAT) { ChatScreen(navController = navController) }
        composable(Routes.PROGRESS) { ProgressScreen(navController = navController) }

        // ✅ NEW: Practice menu (subject)
        composable(
            route = Routes.PRACTICE_MENU,
            arguments = listOf(navArgument("subject") { type = NavType.StringType })
        ) { backStack ->
            val subject = Routes.decodeArg(backStack.arguments?.getString("subject") ?: "")
            PracticeMenuScreen(navController = navController, subject = subject)
        }

        // ✅ NEW: Practice topic screen
        composable(
            route = Routes.PRACTICE_TOPIC,
            arguments = listOf(
                navArgument("subject") { type = NavType.StringType },
                navArgument("topic") { type = NavType.StringType }
            )
        ) { backStack ->
            val subject = Routes.decodeArg(backStack.arguments?.getString("subject") ?: "")
            val topic = Routes.decodeArg(backStack.arguments?.getString("topic") ?: "")
            PracticeTopicScreen(navController = navController, subject = subject, topic = topic)
        }

        // ✅ NEW: Quiz screen
        composable(
            route = Routes.PRACTICE_QUIZ,
            arguments = listOf(
                navArgument("subject") { type = NavType.StringType },
                navArgument("topic") { type = NavType.StringType }
            )
        ) { backStack ->
            val subject = Routes.decodeArg(backStack.arguments?.getString("subject") ?: "")
            val topic = Routes.decodeArg(backStack.arguments?.getString("topic") ?: "")
            PracticeQuizScreen(navController = navController, subject = subject, topic = topic)
        }

        composable(Routes.SETTINGS) {
            SettingScreen(
                navController = navController,
                lang = lang,
                onToggleLanguage = onToggleLanguage
            )
        }

        composable(Routes.HELP) { HelpScreen(navController = navController) }
        composable(Routes.ABOUT) { AboutScreen(navController = navController) }
    }
}
