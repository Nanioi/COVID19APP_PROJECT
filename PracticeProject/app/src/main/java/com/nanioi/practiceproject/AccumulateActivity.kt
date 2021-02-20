package com.nanioi.practiceproject

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_domestic_status.*
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLDecoder
import java.net.URLEncoder


class AccumulateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_domestic_status)

        AccumulateTask().execute()

        summary_btn.setOnClickListener {
            startActivity(Intent(this, SummaryActivity::class.java))
        }
        new_btn.setOnClickListener {
            startActivity(Intent(this, NewActivity::class.java))
        }
        precaution_btn.setOnClickListener {
            startActivity(Intent(this, PrecautionActivity::class.java))
        }
    }
}

class AccumulateTask() : AsyncTask<Any?, Any?, Array<RequestParameter>?>() {
    override fun onPostExecute(result: Array<RequestParameter>?) { // 여기서 차트그리기
        super.onPostExecute(result)
    }

    override fun doInBackground(vararg params: Any?): Array<RequestParameter>? {

        var apiKey = "Tdo19TVJxANWay1HQ1dxcwGA5sJ73wYF%2FVfvQaLyA1iBPWkttg74N9jzEUDGlG6J3ItutuWKuzOutjEblPuQIg%3D%3D"
        var keyDecode = URLDecoder.decode(apiKey, "UTF-8")
        val urlBuilder = StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson") /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + URLEncoder.encode(keyDecode, "UTF-8")) /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")) /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")) /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt", "UTF-8") + "=" + URLEncoder.encode("20200310", "UTF-8")) /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt", "UTF-8") + "=" + URLEncoder.encode("20200315", "UTF-8")) /*검색할 생성일 범위의 종료*/
        urlBuilder.append("&_returnType=json")

        val url: URL = URL(urlBuilder.toString())
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

        connection.requestMethod = "GET"
        connection.setRequestProperty("Content-Type", "application/json")

        var buffer = ""
        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            val reader = BufferedReader(
                    InputStreamReader(
                            connection.inputStream,
                            "UTF-8"
                    )
            )
            buffer = reader.readLine()  // string 응답받은거  만든거
            Log.d("dataaa", "inputStream" + buffer)


        }
//
//        val data = Gson().fromJson(buffer, Array<RequestParameter>::class.java)
//        val cnt = data[0].decide_cnt
//        Log.d("connn", "cnt : " + cnt)

        return null
    }
}


//        accumulate_chart.setDrawGridBackground(true)
//        accumulate_chart.setBackgroundColor(Color.BLACK)
//        accumulate_chart.setGridBackgroundColor(Color.BLACK)



//        val rd: BufferedReader
//        rd = if (connection.getResponseCode() >= 200 && connection.getResponseCode() <= 300) {
//            BufferedReader(InputStreamReader(connection.getInputStream()))
//        } else {
//            BufferedReader(InputStreamReader(connection.getErrorStream()))
//        }
//        val sb = java.lang.StringBuilder()
//        var line: String?
//        while (rd.readLine().also { line = it } != null) {
//            sb.append(line)
//        }
//        rd.close()
//        connection.disconnect()
//        println(sb.toString())


//        try {
//            val data = Jsoup.connect(buffer).get()
//            Log.d("Tag", "msg : " + data)
//        } catch (e:Exception) {
//            e.printStackTrace()
//        }
//        val data = Jsoup.connect(buffer).get()
//        Log.d("Tag", "msg : " + data)


//class ListAdapter(
//        val dtList: Array<RequestParameter>,
//        val inflater: LayoutInflater
//)