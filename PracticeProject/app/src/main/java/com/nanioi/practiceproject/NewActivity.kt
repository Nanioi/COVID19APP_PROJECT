package com.nanioi.practiceproject

import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.activity_new.*
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

class NewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        summary_btn.setOnClickListener {
            startActivity(Intent(this, SummaryActivity::class.java))
        }
        accumulate_btn.setOnClickListener {
            startActivity(Intent(this, AccumulateActivity::class.java))
        }
        precaution_btn.setOnClickListener {
            startActivity(Intent(this, PrecautionActivity::class.java))
        }
        new_XmlParsingTask().execute()
    }
    inner class new_XmlParsingTask() : AsyncTask<Any?, Any?, List<ResponseElement>>() {
        override fun onPostExecute(result: List<ResponseElement>) {
            super.onPostExecute(result)

            val data: ResponseElement = result[0]
            val data_prev: ResponseElement = result[1]
            val decide_new_cnt = ((data.decideCnt!!).toInt() - (data_prev.decideCnt!!).toInt()).toString()
            val exam_new_cnt = ((data.examCnt!!).toInt() - (data_prev.examCnt!!).toInt()).toString()
            val clear_new_cnt = ((data.clearCnt!!).toInt() - (data_prev.clearCnt!!).toInt()).toString()
            val death_new_cnt = ((data.deathCnt!!).toInt() - (data_prev.deathCnt!!).toInt()).toString()

            decide.setText(data.decideCnt)
            decide_new.setText("+" + decide_new_cnt)
            exam.setText(data.examCnt)
            exam_new.setText(exam_new_cnt)
            clear.setText(data.clearCnt)
            clear_new.setText("+" + clear_new_cnt)
            death.setText(data.deathCnt)
            death_new.setText("+" + death_new_cnt)
            state_dt.setText(data.stateDt)
            state_time.setText(data.stateTime)

            setBarChart(new_chart, result)
        }

        override fun doInBackground(vararg params: Any?): List<ResponseElement> {
            return readFeed(parsingData())
        }
    }
}
