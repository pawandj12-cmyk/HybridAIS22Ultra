package com.s22.hybridai

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class AiEngineRouter(private val context: Context) {

    fun isNetworkAvailable(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun executeCommand(prompt: String, hybridModeEnabled: Boolean): String {
        val cleanPrompt = prompt.trim()
        if (cleanPrompt.isEmpty()) {
            return "Please enter a command or prompt first."
        }

        val hasInternet = isNetworkAvailable()
        return if (hybridModeEnabled && hasInternet) {
            "[HYBRID / ONLINE ENGINE]\nProcessed prompt: \"$cleanPrompt\"\nStatus: Ready for cloud/hybrid model routing."
        } else {
            val reason = if (!hybridModeEnabled) {
                "Hybrid toggle is OFF (Offline-only mode forced)."
            } else {
                "No active internet connection detected; falling back to offline engine."
            }
            "[OFFLINE ENGINE]\n$reason\nLocal response for: \"$cleanPrompt\""
        }
    }
}
