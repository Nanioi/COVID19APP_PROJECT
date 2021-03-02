package com.nanioi.practiceproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_prediction.*

class PredictionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prediction)

        summary_btn.setOnClickListener {
            startActivity(Intent(this, SummaryActivity::class.java))
        }
        accumulate_btn.setOnClickListener {
            startActivity(Intent(this, AccumulateActivity::class.java))
        }
        precaution_btn.setOnClickListener {
            startActivity(Intent(this, PrecautionActivity::class.java))
        }
    }
}