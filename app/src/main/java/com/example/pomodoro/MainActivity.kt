package com.example.pomodoro

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.w_200)
        }

        //binding
        val progressView = findViewById<FancyProgress>(R.id.fancyTimer)
        progressView.callback = object : FancyProgress.ProgressListener {
            override fun progress(value: String) {
                findViewById<TextView>(R.id.tvProgress).text = value
            }
        }
        //start the count down when click in button
        findViewById<ImageView>(R.id.btnPlay).setOnClickListener {
            progressView.start()
        }

        findViewById<ImageView>(R.id.btnPause).setOnClickListener {
            progressView.pause()
        }
        //time in milliseconds
        progressView.setTime(60000)
    }
}