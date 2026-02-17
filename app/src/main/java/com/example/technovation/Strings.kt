package com.example.technovation

enum class Lang { RO, EN }

data class Strings(
    val welcome: String,
    val loginSubtitle: String,
    val username: String,
    val password: String,
    val enterUsername: String,
    val enterPassword: String,
    val login: String,
    val signup: String,
    val dontHaveAccount: String,

    val homeTitle: String,
    val dashboardSubtitle: String,
    val todayFocus: String,
    val startPractice: String,

    val settings: String,
    val language: String,
    val romanian: String,
    val english: String,
)

private val STR_RO = Strings(
    welcome = "Welcome!",
    loginSubtitle = "Log in to your account!",
    username = "Username",
    password = "Password",
    enterUsername = "Enter username",
    enterPassword = "Enter password",
    login = "Log In",
    signup = "Sign Up",
    dontHaveAccount = "Don’t have an account? Create one!",

    homeTitle = "Smart4Edu",
    dashboardSubtitle = "Your Evaluarea Națională dashboard",
    todayFocus = "Today’s focus",
    startPractice = "Start Practice",

    settings = "Settings",
    language = "Language",
    romanian = "Română",
    english = "English",
)

private val STR_EN = Strings(
    welcome = "Welcome!",
    loginSubtitle = "Log in to your account!",
    username = "Username",
    password = "Password",
    enterUsername = "Enter username",
    enterPassword = "Enter password",
    login = "Log In",
    signup = "Sign Up",
    dontHaveAccount = "Don’t have an account? Create one!",

    homeTitle = "Smart4Edu",
    dashboardSubtitle = "Your National Exam dashboard",
    todayFocus = "Today's focus",
    startPractice = "Start Practice",

    settings = "Settings",
    language = "Language",
    romanian = "Romanian",
    english = "English",
)

fun stringsFor(lang: Lang): Strings = if (lang == Lang.RO) STR_RO else STR_EN
