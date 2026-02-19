package com.example.technovation

import java.net.URLEncoder
import java.net.URLDecoder

object Routes {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val HOME = "home"
    const val PRACTICE = "practice"
    const val CALM = "calm"
    const val CHAT = "chat"
    const val PROGRESS = "progress"

    const val SETTINGS = "settings"
    const val HELP = "help"
    const val ABOUT = "about"

    // --- Practice nested routes ---
    const val PRACTICE_MENU = "practice_menu/{subject}"
    const val PRACTICE_TOPIC = "practice_topic/{subject}/{topic}"
    const val PRACTICE_QUIZ = "practice_quiz/{subject}/{topic}"

    // Helpers to build routes safely
    fun practiceMenuRoute(subject: String): String =
        "practice_menu/${encode(subject)}"

    fun practiceTopicRoute(subject: String, topic: String): String =
        "practice_topic/${encode(subject)}/${encode(topic)}"

    fun practiceQuizRoute(subject: String, topic: String): String =
        "practice_quiz/${encode(subject)}/${encode(topic)}"

    fun decodeArg(arg: String): String = URLDecoder.decode(arg, "UTF-8")
    private fun encode(s: String): String = URLEncoder.encode(s, "UTF-8")
}
