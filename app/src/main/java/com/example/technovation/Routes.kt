package com.example.technovation

import java.net.URLEncoder
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

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

    // NEW: practice submenu + topic routes
    const val PRACTICE_MENU = "practice_menu/{subject}"
    const val PRACTICE_TOPIC = "practice_topic/{subject}/{topic}"

    fun practiceMenuRoute(subject: String): String {
        val s = URLEncoder.encode(subject, StandardCharsets.UTF_8.toString())
        return "practice_menu/$s"
    }

    fun practiceTopicRoute(subject: String, topic: String): String {
        val s = URLEncoder.encode(subject, StandardCharsets.UTF_8.toString())
        val t = URLEncoder.encode(topic, StandardCharsets.UTF_8.toString())
        return "practice_topic/$s/$t"
    }

    fun decodeArg(value: String): String =
        URLDecoder.decode(value, StandardCharsets.UTF_8.toString())
}
