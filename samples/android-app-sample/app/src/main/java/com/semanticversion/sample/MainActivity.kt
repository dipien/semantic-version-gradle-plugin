package com.semanticversion.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.versionName).text = packageManager.getPackageInfo(packageName, 0).versionName
        val versionCode = findViewById<TextView>(R.id.versionCode)
        versionCode.text = packageManager.getPackageInfo(packageName, 0).versionCode.toString()
    }
}
