package com.n_droidev.stepscircularprogressbar

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.n_droidev.stepscircularprogressbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val colors = intArrayOf(
            Color.GREEN,
            Color.MAGENTA,
            Color.YELLOW,
            Color.BLUE
        )

        Handler(Looper.getMainLooper()).postDelayed({
            binding.stepsProgress.setData(9, dynamicStepColors = colors)
        }, 2000)
    }
}