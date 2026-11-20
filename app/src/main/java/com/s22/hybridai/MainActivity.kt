package com.s22.hybridai

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.s22.hybridai.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var aiEngineRouter: AiEngineRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        aiEngineRouter = AiEngineRouter(this)

        binding.btnEnableAccessibility.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }

        binding.btnRun.setOnClickListener {
            val prompt = binding.etPrompt.text?.toString().orEmpty()
            val hybridEnabled = binding.switchHybridMode.isChecked
            val serviceStatus = if (DeviceControlService.isServiceEnabled()) {
                "DeviceControlService: Connected"
            } else {
                "DeviceControlService: Not enabled"
            }

            val result = aiEngineRouter.executeCommand(prompt, hybridEnabled)
            binding.tvOutput.text = "$result\n\n$serviceStatus"
        }
    }
}
