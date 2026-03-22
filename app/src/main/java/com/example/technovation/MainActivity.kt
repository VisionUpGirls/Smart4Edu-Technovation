package com.example.technovation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.technovation.ui.theme.TechnovationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val scope = rememberCoroutineScope()

            val themePrefs = remember { ThemePrefs(context) }
            val settingsPrefs = remember { SettingsPrefs(context) }

            val isDarkMode by themePrefs.isDarkThemeFlow.collectAsState(initial = false)
            val lang by settingsPrefs.languageFlow.collectAsState(initial = "ro")

            var isLoggedIn by remember { mutableStateOf(false) }
            var username by remember { mutableStateOf("") }

            val navController = rememberNavController()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            val showChrome = currentRoute !in setOf(Routes.LOGIN, Routes.SIGNUP)

            TechnovationTheme(darkTheme = isDarkMode) {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        if (showChrome) {
                            ModalDrawerSheet(modifier = Modifier.widthIn(max = 260.dp)) {
                                DrawerContent(
                                    lang = lang,
                                    username = username,
                                    currentRoute = currentRoute,
                                    onNavigate = { route ->
                                        scope.launch { drawerState.close() }
                                        navController.navigate(route) {
                                            launchSingleTop = true
                                        }
                                    },
                                    onLogout = {
                                        scope.launch { drawerState.close() }
                                        isLoggedIn = false
                                        username = ""
                                        navController.navigate(Routes.LOGIN) {
                                            popUpTo(0) { inclusive = true }
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) {
                    Scaffold(
                        topBar = {
                            if (showChrome) {
                                CenterAlignedTopAppBar(
                                    title = {
                                        Text(text = topBarTitle(currentRoute, lang))
                                    },
                                    navigationIcon = {
                                        IconButton(
                                            onClick = { scope.launch { drawerState.open() } }
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_username),
                                                contentDescription = "Open drawer",
                                                tint = Color.Unspecified,
                                                modifier = Modifier
                                                    .size(34.dp)
                                                    .clip(CircleShape)
                                            )
                                        }
                                    },
                                    actions = {
                                        IconButton(
                                            onClick = {
                                                scope.launch {
                                                    themePrefs.setDarkTheme(!isDarkMode)
                                                }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = if (isDarkMode) {
                                                    Icons.Filled.LightMode
                                                } else {
                                                    Icons.Filled.DarkMode
                                                },
                                                contentDescription = "Toggle theme"
                                            )
                                        }
                                    }
                                )
                            }
                        },
                        bottomBar = {
                            if (showChrome) {
                                AppBottomBar(
                                    navController = navController,
                                    currentRoute = currentRoute,
                                    lang = lang
                                )
                            }
                        }
                    ) { innerPadding ->
                        AppNavHost(
                            navController = navController,
                            isLoggedIn = isLoggedIn,
                            username = username,
                            onLoggedIn = { name ->
                                username = name
                                isLoggedIn = true
                                navController.navigate(Routes.HOME) {
                                    popUpTo(Routes.LOGIN) { inclusive = true }
                                    launchSingleTop = true
                                }
                            },
                            lang = lang,
                            onToggleLanguage = {
                                scope.launch {
                                    settingsPrefs.setLanguage(
                                        if (lang == "ro") "en" else "ro"
                                    )
                                }
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DrawerContent(
    lang: String,
    username: String,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {
    Row(modifier = Modifier.padding(16.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.ic_username),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = if (username.isBlank()) {
                    if (lang == "ro") "Oaspete" else "Guest"
                } else {
                    username
                },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = if (lang == "ro") "Cont" else "Account",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }

    Divider()

    DrawerItem(
        label = if (lang == "ro") "Acasă" else "Home",
        icon = Icons.Filled.Home,
        selected = currentRoute == Routes.HOME,
        onClick = { onNavigate(Routes.HOME) }
    )

    DrawerItem(
        label = if (lang == "ro") "Practică" else "Practice",
        icon = Icons.Filled.School,
        selected = currentRoute == Routes.PRACTICE,
        onClick = { onNavigate(Routes.PRACTICE) }
    )

    DrawerItem(
        label = "Calm",
        icon = Icons.Filled.SelfImprovement,
        selected = currentRoute == Routes.CALM,
        onClick = { onNavigate(Routes.CALM) }
    )

    DrawerItem(
        label = "Chat",
        icon = Icons.Filled.Chat,
        selected = currentRoute == Routes.CHAT,
        onClick = { onNavigate(Routes.CHAT) }
    )

    DrawerItem(
        label = if (lang == "ro") "Progres" else "Progress",
        icon = Icons.Filled.BarChart,
        selected = currentRoute == Routes.PROGRESS,
        onClick = { onNavigate(Routes.PROGRESS) }
    )

    Spacer(modifier = Modifier.height(8.dp))
    Divider()
    Spacer(modifier = Modifier.height(8.dp))

    DrawerItem(
        label = if (lang == "ro") "Setări" else "Settings",
        icon = Icons.Filled.Settings,
        selected = currentRoute == Routes.SETTINGS,
        onClick = { onNavigate(Routes.SETTINGS) }
    )

    DrawerItem(
        label = if (lang == "ro") "Ajutor" else "Help",
        icon = Icons.Filled.Help,
        selected = currentRoute == Routes.HELP,
        onClick = { onNavigate(Routes.HELP) }
    )

    DrawerItem(
        label = if (lang == "ro") "Despre" else "About",
        icon = Icons.Filled.Info,
        selected = currentRoute == Routes.ABOUT,
        onClick = { onNavigate(Routes.ABOUT) }
    )

    Spacer(modifier = Modifier.height(8.dp))
    Divider()
    Spacer(modifier = Modifier.height(8.dp))

    DrawerItem(
        label = if (lang == "ro") "Deconectare" else "Log out",
        icon = Icons.Filled.Logout,
        selected = false,
        onClick = onLogout
    )
}

@Composable
private fun DrawerItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(label) },
        icon = { Icon(icon, contentDescription = null) },
        selected = selected,
        onClick = onClick,
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}

@Composable
private fun AppBottomBar(
    navController: NavHostController,
    currentRoute: String?,
    lang: String
) {
    val items = listOf(
        BottomItem(
            label = if (lang == "ro") "Practică" else "Practice",
            route = Routes.PRACTICE,
            icon = Icons.Filled.School
        ),
        BottomItem(
            label = "Calm",
            route = Routes.CALM,
            icon = Icons.Filled.SelfImprovement
        ),
        BottomItem(
            label = if (lang == "ro") "Acasă" else "Home",
            route = Routes.HOME,
            icon = Icons.Filled.Home
        ),
        BottomItem(
            label = "Chat",
            route = Routes.CHAT,
            icon = Icons.Filled.Chat
        ),
        BottomItem(
            label = if (lang == "ro") "Progres" else "Progress",
            route = Routes.PROGRESS,
            icon = Icons.Filled.BarChart
        )
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label, maxLines = 1) },
                alwaysShowLabel = true
            )
        }
    }
}

private fun topBarTitle(currentRoute: String?, lang: String): String {
    return when (currentRoute) {
        Routes.HOME -> if (lang == "ro") "Acasă" else "Home"
        Routes.PRACTICE -> if (lang == "ro") "Practică" else "Practice"
        Routes.CALM -> "Calm"
        Routes.CHAT -> "Chat"
        Routes.PROGRESS -> if (lang == "ro") "Progres" else "Progress"
        Routes.SETTINGS -> if (lang == "ro") "Setări" else "Settings"
        Routes.HELP -> if (lang == "ro") "Ajutor" else "Help"
        Routes.ABOUT -> if (lang == "ro") "Despre" else "About"
        else -> "Smart4Edu"
    }
}

private data class BottomItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)