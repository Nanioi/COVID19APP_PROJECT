package com.nanioi.practiceproject

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_domestic_status.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLDecoder
import java.net.URLEncoder
import javax.xml.parsers.DocumentBuilderFactory


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
class AccumulateTask() : AsyncTask<Any?, Any?, String>() {
    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }

    override fun doInBackground(vararg params: Any?):String{

        var xpp : XmlPullParser

        var apiKey = "Tdo19TVJxANWay1HQ1dxcwGA5sJ73wYF%2FVfvQaLyA1iBPWkttg74N9jzEUDGlG6J3ItutuWKuzOutjEblPuQIg%3D%3D"
        var keyDecode = URLDecoder.decode(apiKey, "UTF-8")
        val urlBuilder = StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson") /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + URLEncoder.encode(keyDecode, "UTF-8")) /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")) /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")) /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt", "UTF-8") + "=" + URLEncoder.encode("20200310", "UTF-8")) /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt", "UTF-8") + "=" + URLEncoder.encode("20200315", "UTF-8")) /*검색할 생성일 범위의 종료*/

        val url: URL = URL(urlBuilder.toString())

        var factory : XmlPullParserFactory = XmlPullParserFactory.newInstance()
        xpp = factory.newPullParser()
        xpp.setInput(InputStreamReader(url.openStream(), "UTF-8"))

        xpp.next()
        var eventType : Int = xpp.eventType
        var buffer : StringBuffer = StringBuffer()
        var tag : String

        while(eventType != XmlPullParser.END_DOCUMENT){
            when(eventType){
                XmlPullParser.START_DOCUMENT ->{
                    buffer.append("parsing start \n\n")
                }
                XmlPullParser.START_TAG->{
                    tag = xpp.name

                    if(tag.equals("item"))
                    else if(tag.equals("accExamCnt")){
                        buffer.append("누적검사 수 : ")
                        xpp.next()
                        buffer.append(xpp.text)
                        buffer.append("\n")
                    }else if(tag.equals("clearCnt")) {
                        buffer.append("격리해제 수 : ")
                        xpp.next()
                        buffer.append(xpp.text)
                        buffer.append("\n")
                    }else if(tag.equals("createDt")) {
                        buffer.append("등록일시 : ")
                        xpp.next()
                        buffer.append(xpp.text)
                        buffer.append("\n")
                    }else if(tag.equals("deathCnt")) {
                        buffer.append("사망자 수 : ")
                        xpp.next()
                        buffer.append(xpp.text)
                        buffer.append("\n")
                    }else if(tag.equals("decideCnt")) {
                        buffer.append("확진자 수 : ")
                        xpp.next()
                        buffer.append(xpp.text)
                        buffer.append("\n")
                    }else if(tag.equals("examCnt")) {
                        buffer.append("검사진행 수 : ")
                        xpp.next()
                        buffer.append(xpp.text)
                        buffer.append("\n")
                    }
                }
                XmlPullParser.TEXT ->{
                }
                XmlPullParser.END_TAG->{
                    tag=xpp.name
                    if(tag.equals("item"))buffer.append("\n")
                }
            }
            eventType=xpp.next()
        }
        buffer.append("parsing end\n")

        Log.d("connnnnn","data : "+buffer.toString())

        return buffer.toString()
    }
}