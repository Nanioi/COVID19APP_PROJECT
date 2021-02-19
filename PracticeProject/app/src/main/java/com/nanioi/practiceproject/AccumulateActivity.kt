package com.nanioi.practiceproject

import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_domestic_status.*
import org.jsoup.Jsoup
import org.w3c.dom.Document
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.text.Html as Html1


class AccumulateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_domestic_status)

//
//        accumulate_chart.setDrawGridBackground(true)
//        accumulate_chart.setBackgroundColor(Color.BLACK)
//        accumulate_chart.setGridBackgroundColor(Color.BLACK)

        summary_btn.setOnClickListener {
            startActivity(Intent(this,SummaryActivity::class.java))
        }
        new_btn.setOnClickListener {
            startActivity(Intent(this,NewActivity::class.java))
        }
        precaution_btn.setOnClickListener {
            startActivity(Intent(this, PrecautionActivity::class.java))
        }
    }
}
//class BackgroundAsyncTask(
//    val progressbar : ProgressBar,
//    val progressText : TextView
//): AsyncTask<Int, Int, Int>(){
//    // params -> doInBackground에서 사용할 타입 , 첫번째 타입 string등 가능
//    // progress -> onProgressUPdate 에서 사용할 타입
//    // result -> onPostExecute에서 사용할 타입
//
//    override fun onPreExecute() {
//    }//작업의 시작전
//
//    override fun doInBackground(vararg params: Int?): Int? {
//        //val document : org.jsoup.nodes.Document? = Jsoup.connect(Html1).get()
//        while(isCancelled()== false){ //완료되기전에 취소가 되었는지 아닌지. oncancelled()과 다름
//            try{
//
//                Thread.sleep(100)
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
//        }
//        return null
//    }
//    //percent가 100인경우 리턴됨
//
//    override fun onProgressUpdate(vararg values: Int?) {
//        super.onProgressUpdate(*values)
//    }
//
//    override fun onPostExecute(result: Int?) {
//    }// doIn에서 리턴한 값이 인자로 들어옴
//
//    override fun onCancelled() {
//    }
//}