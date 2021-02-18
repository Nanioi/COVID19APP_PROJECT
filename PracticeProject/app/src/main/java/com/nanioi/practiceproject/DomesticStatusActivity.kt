package com.nanioi.practiceproject

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import kotlinx.android.synthetic.main.activity_domestic_status.*


class DomesticStatusActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_domestic_status)

        chart.setDrawGridBackground(true)
        chart.setBackgroundColor(Color.BLACK)
        chart.setGridBackgroundColor(Color.BLACK)

        summary_btn.setOnClickListener {
            startActivity(Intent(this,SummaryActivity::class.java))
        }
        overseas_btn.setOnClickListener {
            startActivity(Intent(this,OverseasStatusActivity::class.java))
        }
        precaution_btn.setOnClickListener {
            startActivity(Intent(this, PrecautionActivity::class.java))
        }
    }
}