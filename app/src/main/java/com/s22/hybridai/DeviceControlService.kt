package com.s22.hybridai

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class DeviceControlService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Ready for screen inspection and device control events
    }

    override fun onInterrupt() {
        // Service interrupted
    }

    override fun onDestroy() {
        super.onDestroy()
        if (instance == this) {
            instance = null
        }
    }

    fun performBack(): Boolean {
        return performGlobalAction(GLOBAL_ACTION_BACK)
    }

    fun performHome(): Boolean {
        return performGlobalAction(GLOBAL_ACTION_HOME)
    }

    companion object {
        @Volatile
        var instance: DeviceControlService? = null
            private set

        fun isServiceEnabled(): Boolean = instance != null
    }
}
