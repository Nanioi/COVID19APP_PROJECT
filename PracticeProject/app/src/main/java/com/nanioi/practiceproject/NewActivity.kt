package com.nanioi.practiceproject

import android.app.DatePickerDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new.*
import java.util.*

class NewActivity : AppCompatActivity() {
    var setdt: Int = 0
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
        calendar_btn.setOnClickListener {
            val today = GregorianCalendar()
            val year: Int = today.get(Calendar.YEAR)
            val month: Int = today.get(Calendar.MONTH)
            val date: Int = today.get(Calendar.DATE)
            val dlg = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    setdt = 0
                    setdt += year
                    setdt *= 100
                    setdt += (month+1)
                    setdt *= 100
                    setdt += dayOfMonth
                    new_XmlParsingTask().execute()
                }
            }, year, month, date)
            dlg.show()
        }
        new_XmlParsingTask().execute()
    }
    inner class new_XmlParsingTask() : AsyncTask<Any?, Any?, List<ResponseElement>>() {
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

            setBarChart(new_chart, result, this@NewActivity)
        }
        override fun doInBackground(vararg params: Any?): List<ResponseElement> {
            return readFeed(parsingData(setdt))
        }
    }
}
