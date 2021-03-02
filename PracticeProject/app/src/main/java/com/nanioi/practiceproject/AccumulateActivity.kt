package com.nanioi.practiceproject

import android.app.DatePickerDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_accumulate.*
import java.util.*

class AccumulateActivity : AppCompatActivity() {
    var dt : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accumulate)

        summary_btn.setOnClickListener {
            startActivity(Intent(this, SummaryActivity::class.java))
        }
        predic_btn.setOnClickListener {
            startActivity(Intent(this, PredictionActivity::class.java))
        }
        precaution_btn.setOnClickListener {
            startActivity(Intent(this, PrecautionActivity::class.java))
        }
        calendar_btn.setOnClickListener {
            val today = GregorianCalendar()
            val year: Int = today.get(Calendar.YEAR)
            val month: Int = today.get(Calendar.MONTH)
            val date: Int = today.get(Calendar.DATE)
            val dlg = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    dt =0
                    dt+=year
                    dt*=100
                    dt+=(month+1)
                    dt*=100
                    dt+=dayOfMonth
                    XmlParsingTask().execute()
                }
            }, year, month, date)
            dlg.show()
        }
        new_act_btn.setOnClickListener {
            startActivity(Intent(this, NewActivity::class.java))
        }
        XmlParsingTask().execute()

    }
    inner class XmlParsingTask() : AsyncTask<Any?, Any?, List<ResponseElement>>() {
        override fun onPostExecute(result: List<ResponseElement>) {
            super.onPostExecute(result)

            val data: ResponseElement = result[0]
            val data_prev: ResponseElement = result[1]
            val decide_new_cnt = ((data.decideCnt!!).toInt() - (data_prev.decideCnt!!).toInt())
            val exam_new_cnt = ((data.examCnt!!).toInt() - (data_prev.examCnt!!).toInt())
            val clear_new_cnt = ((data.clearCnt!!).toInt() - (data_prev.clearCnt!!).toInt())
            val death_new_cnt = ((data.deathCnt!!).toInt() - (data_prev.deathCnt!!).toInt())

            decide.setText(data.decideCnt)
            decide_new.setText("+" + decide_new_cnt.toString())
            exam.setText(data.examCnt)
            if(exam_new_cnt >= 0){
                exam_new.setText("+" + exam_new_cnt.toString())
            }else{
                exam_new.setText(exam_new_cnt.toString())
            }
            clear.setText(data.clearCnt)
            clear_new.setText("+" + clear_new_cnt.toString())
            death.setText(data.deathCnt)
            death_new.setText("+" + death_new_cnt.toString())
            state_dt.setText(data.stateDt)
            state_time.setText(data.stateTime)

            setLineChart(accumulate_chart,result,this@AccumulateActivity)
        }
        override fun doInBackground(vararg params: Any?): List<ResponseElement> {
            return readFeed(parsingData(dt))
        }
    }
}