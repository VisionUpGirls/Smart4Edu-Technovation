package com.example.technovation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

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
        // Auth
        composable(Routes.LOGIN) {
            LoginScreen(
                navController = navController,
                onLoggedIn = onLoggedIn
            )
        }

        composable(Routes.SIGNUP) {
            SignUpScreen(navController = navController)
        }

        // Main tabs
        composable(Routes.HOME) {
            HomeScreen(navController = navController, username = username)
        }

        composable(Routes.PRACTICE) {
            PracticeScreen(navController = navController)
        }

        composable(Routes.CALM) {
            CalmScreen(navController = navController)
        }

        composable(Routes.CHAT) {
            ChatScreen(navController = navController)
        }

        composable(Routes.PROGRESS) {
            ProgressScreen(navController = navController)
        }

        // NEW: Practice subject menu (submenu)
        composable(
            route = Routes.PRACTICE_MENU,
            arguments = listOf(navArgument("subject") { type = NavType.StringType })
        ) { backStackEntry ->
            val subjectRaw = backStackEntry.arguments?.getString("subject").orEmpty()
            val subject = Routes.decodeArg(subjectRaw)
            PracticeMenuScreen(navController = navController, subject = subject)
        }

        // NEW: Practice topic screen (placeholder)
        composable(
            route = Routes.PRACTICE_TOPIC,
            arguments = listOf(
                navArgument("subject") { type = NavType.StringType },
                navArgument("topic") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val subject = Routes.decodeArg(backStackEntry.arguments?.getString("subject").orEmpty())
            val topic = Routes.decodeArg(backStackEntry.arguments?.getString("topic").orEmpty())
            PracticeTopicScreen(navController = navController, subject = subject, topic = topic)
        }

        // Drawer screens
        composable(Routes.SETTINGS) {
            SettingScreen(
                navController = navController,
                lang = lang,
                onToggleLanguage = onToggleLanguage
            )
        }

        composable(Routes.HELP) {
            HelpScreen(navController = navController)
        }

        composable(Routes.ABOUT) {
            AboutScreen(navController = navController)
        }
    }
}
