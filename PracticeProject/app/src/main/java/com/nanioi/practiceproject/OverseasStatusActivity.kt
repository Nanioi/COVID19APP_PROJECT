package com.nanioi.practiceproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_overseas_status_acticity.*

class OverseasStatusActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overseas_status_acticity)

        summary_btn.setOnClickListener {
            startActivity(Intent(this,SummaryActivity::class.java))
        }
        domestic_btn.setOnClickListener {
            startActivity(Intent(this, DomesticStatusActivity::class.java))
        }
        precaution_btn.setOnClickListener {
            startActivity(Intent(this, PrecautionActivity::class.java))
        }
    }
}