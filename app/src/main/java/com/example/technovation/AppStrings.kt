package com.example.technovation

class AppStrings(lang: String) {
    private val ro = lang == "ro"

    // Bottom bar + Top bar
    val home = if (ro) "Acasă" else "Home"
    val practice = if (ro) "Practică" else "Practice"
    val calm = "Calm"          // keep same
    val chat = "Chat"          // keep same
    val progress = if (ro) "Progres" else "Progress"

    // Drawer pages
    val settings = if (ro) "Setări" else "Settings"
    val help = if (ro) "Ajutor" else "Help"
    val about = if (ro) "Despre" else "About"

    // Drawer header / actions
    val account = if (ro) "Cont" else "Account"
    val guest = if (ro) "Oaspete" else "Guest"
    val logout = if (ro) "Deconectare" else "Log out"

    // Optional titles you might use in screens
    val smart4edu = "Smart4Edu"
}
