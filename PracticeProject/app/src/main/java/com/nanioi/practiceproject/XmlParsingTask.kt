package com.nanioi.practiceproject

import android.graphics.Color
import android.os.AsyncTask
import android.util.Log
import android.util.Xml
import android.widget.TextView
import androidx.core.graphics.blue
import androidx.core.graphics.red
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.net.URLDecoder
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


private val ns: String? = null


data class ResponseElement(
        val clearCnt: String?,
        val deathCnt: String?,
        val decideCnt: String?,
        val examCnt: String?,
        val stateDt: String?,
        val stateTime: String?
)
class ChartDt(
        val decideDt: String,
        val clearDt: String
)
class XmlParsingTask(
        val decide: TextView,
        val decide_new: TextView,
        val exam: TextView,
        val exam_new: TextView,
        val clear: TextView,
        val clear_new: TextView,
        val death: TextView,
        val death_new: TextView,
        val state_dt: TextView,
        val state_time: TextView,
        val accumulate_chart: LineChart
) : AsyncTask<Any?, Any?, List<ResponseElement>>() {
    override fun onPostExecute(result: List<ResponseElement>) {
        super.onPostExecute(result)
        val data : ResponseElement = result[0]
        val data_prev : ResponseElement = result[1]
        val decide_new_cnt  = ((data.decideCnt!!).toInt() - (data_prev.decideCnt!!).toInt()).toString()
        val exam_new_cnt  = ((data.examCnt!!).toInt() - (data_prev.examCnt!!).toInt()).toString()
        val clear_new_cnt  = ((data.clearCnt!!).toInt() - (data_prev.clearCnt!!).toInt()).toString()
        val death_new_cnt  = ((data.deathCnt!!).toInt() - (data_prev.deathCnt!!).toInt()).toString()
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

        setChart(accumulate_chart, result)

    }
    override fun doInBackground(vararg params: Any?): List<ResponseElement> {

        var xpp: XmlPullParser

        var apiKey = "Tdo19TVJxANWay1HQ1dxcwGA5sJ73wYF%2FVfvQaLyA1iBPWkttg74N9jzEUDGlG6J3ItutuWKuzOutjEblPuQIg%3D%3D"
        var pageNo = "1"
        val numOfRows = "10"
        val startCreateDt: String = "20200310"
        val endCreateDt: String = "20210222"

        var keyDecode = URLDecoder.decode(apiKey, "UTF-8")
        val urlBuilder = StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson") /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + URLEncoder.encode(keyDecode, "UTF-8")) /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")) /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8")) /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt", "UTF-8") + "=" + URLEncoder.encode(startCreateDt, "UTF-8")) /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt", "UTF-8") + "=" + URLEncoder.encode(endCreateDt, "UTF-8")) /*검색할 생성일 범위의 종료*/

        val url: URL = URL(urlBuilder.toString())

        val parser: XmlPullParser = Xml.newPullParser()
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        parser.setInput(InputStreamReader(url.openStream(), "UTF-8"))
        parser.nextTag()

        return readFeed(parser)
    }
}
@Throws(XmlPullParserException::class, IOException::class)
private fun readFeed(parser: XmlPullParser): List<ResponseElement> {
    val entries = mutableListOf<ResponseElement>()

    parser.require(XmlPullParser.START_TAG, ns, "response")
    while (parser.next() != XmlPullParser.END_TAG) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            continue
        }
        if (parser.name == "item") {
            entries.add(readEntry(parser))
        } else {
            skip(parser)
        }
    }
    return entries
}

@Throws(XmlPullParserException::class, IOException::class)
private fun readEntry(parser: XmlPullParser): ResponseElement {
    parser.require(XmlPullParser.START_TAG, ns, "item")
    var clearCnt: String? = null
    var deathCnt: String? = null
    var decideCnt: String? = null
    var examCnt: String? = null
    var stateDt: String? = null
    var stateTime: String? = null

    while (parser.next() != XmlPullParser.END_TAG) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            continue
        }
        when (parser.name) {
            "clearCnt" -> clearCnt = readClearCnt(parser)
            "deathCnt" -> deathCnt = readDeathCnt(parser)
            "decideCnt" -> decideCnt = readDecideCnt(parser)
            "examCnt" -> examCnt = readExamCnt(parser)
            "stateDt" -> stateDt = readStateDt(parser)
            "stateTime" -> stateTime = readStateTime(parser)
            else -> skip(parser)
        }
    }
    return ResponseElement(clearCnt, deathCnt, decideCnt, examCnt, stateDt, stateTime)
}

// Processes title tags in the feed.
@Throws(IOException::class, XmlPullParserException::class)
private fun readStateDt(parser: XmlPullParser): String {
    parser.require(XmlPullParser.START_TAG, ns, "stateDt")
    val stateDt = readText(parser)
    parser.require(XmlPullParser.END_TAG, ns, "stateDt")
    return stateDt
}

// Processes link tags in the feed.
@Throws(IOException::class, XmlPullParserException::class)
private fun readClearCnt(parser: XmlPullParser): String {
    parser.require(XmlPullParser.START_TAG, ns, "clearCnt")
    val clearCnt = readText(parser)
    parser.require(XmlPullParser.END_TAG, ns, "clearCnt")
    return clearCnt
}

// Processes summary tags in the feed.
@Throws(IOException::class, XmlPullParserException::class)
private fun readStateTime(parser: XmlPullParser): String {
    parser.require(XmlPullParser.START_TAG, ns, "stateTime")
    val stateTime = readText(parser)
    parser.require(XmlPullParser.END_TAG, ns, "stateTime")
    return stateTime
}

// Processes title tags in the feed.
@Throws(IOException::class, XmlPullParserException::class)
private fun readDeathCnt(parser: XmlPullParser): String {
    parser.require(XmlPullParser.START_TAG, ns, "deathCnt")
    val deathCnt = readText(parser)
    parser.require(XmlPullParser.END_TAG, ns, "deathCnt")
    return deathCnt
}

// Processes link tags in the feed.
@Throws(IOException::class, XmlPullParserException::class)
private fun readDecideCnt(parser: XmlPullParser): String {
    parser.require(XmlPullParser.START_TAG, ns, "decideCnt")
    val decideCnt = readText(parser)
    parser.require(XmlPullParser.END_TAG, ns, "decideCnt")
    return decideCnt
}

// Processes summary tags in the feed.
@Throws(IOException::class, XmlPullParserException::class)
private fun readExamCnt(parser: XmlPullParser): String {
    parser.require(XmlPullParser.START_TAG, ns, "examCnt")
    val examCnt = readText(parser)
    parser.require(XmlPullParser.END_TAG, ns, "examCnt")
    return examCnt
}

// For the tags title and summary, extracts their text values.
@Throws(IOException::class, XmlPullParserException::class)
private fun readText(parser: XmlPullParser): String {
    var result = ""
    if (parser.next() == XmlPullParser.TEXT) {
        result = parser.text
        parser.nextTag()
    }
    return result
}

@Throws(XmlPullParserException::class, IOException::class)
private fun skip(parser: XmlPullParser) {

    if (parser.eventType != XmlPullParser.START_TAG) {
        throw IllegalStateException()
    }
    var depth = 1
    while (depth != 0) {
        if (parser.name == "items")
            break
        when (parser.next()) {
            XmlPullParser.END_TAG -> depth--
            XmlPullParser.START_TAG -> depth++
            null -> parser.next()
        }
    }
}
private fun setChart(lineChart: LineChart, result: List<ResponseElement>){
    val chart1 =  lineChart
    chart1.invalidate()
    chart1.clear()

    var decide_value :  ArrayList<Entry> = arrayListOf()
    var clear_value :  ArrayList<Entry> = arrayListOf()
    var xLabel : ArrayList<String> = arrayListOf()

    var decide :String
    var clear : String
    var statedt :String
    var c = 0f
    for(i in 354 downTo 0 step 59){
        statedt = result[i].stateDt!!
        decide   = result[i].decideCnt!!
        clear = result[i].clearCnt!!
        decide_value.add(Entry(c, decide.toFloat()))
        clear_value.add(Entry(c, clear.toFloat()))
        xLabel.add(statedt)
        c+=1f
    }

    var lineDataSet1 :LineDataSet = LineDataSet(decide_value, "누적확진자 수")
    lineDataSet1.apply {
        axisDependency = YAxis.AxisDependency.LEFT //  y값을 왼쪽으로
        color = Color.RED
        setCircleColor(getColor(Color.RED)) // 데이터 원색
        valueTextSize = 10f
        lineWidth = 2f
        circleRadius=3f // 원크기
        fillAlpha = 0 // 라인 색 투명도
        fillColor = getColor(Color.RED) // 라인색
        highLightColor = Color.BLACK
        setDrawValues(true)
    }
    var lineDataSet2 :LineDataSet = LineDataSet(clear_value, "격리해제 수")
    lineDataSet2.apply {
        axisDependency = YAxis.AxisDependency.LEFT //  y값을 왼쪽으로
        color = Color.BLUE
        setCircleColor(getColor(Color.BLUE)) // 데이터 원색
        valueTextSize = 10f
        lineWidth = 2f
        circleRadius=3f // 원크기
        fillAlpha = 0 // 라인 색 투명도
        fillColor = getColor(Color.BLUE) // 라인색
        highLightColor = Color.BLACK
        setDrawValues(true)
    }

    var lineData=LineData() // linedataset을 담는 그릇, 여러개의 라인데이터가 들어갈수 있음
    lineData.addDataSet(lineDataSet1)
    lineData.addDataSet(lineDataSet2)
    val xAxis = lineChart.xAxis
    xAxis.apply{
        position = XAxis.XAxisPosition.BOTTOM // x축 데이터위치 아래로
        textSize = 10f  // 텍스트 크기
        setDrawGridLines(false) //배경 그리드 라인 세팅
        granularity =1f // x축 데이터 표시 간격
        isGranularityEnabled= true // x축 간격을 제한하는 세분화 기능
        setValueFormatter(object : ValueFormatter() {
            val pattern = "MM/dd"
            private val mFormat = SimpleDateFormat(pattern)
            private val inputFormat = SimpleDateFormat("yyyyMMdd")
            override fun getFormattedValue(value: Float): String {
                val millis = TimeUnit.HOURS.toMillis(value.toLong())
                return mFormat.format(inputFormat.parse(xLabel[value.toInt()]))
            }
        })
    }
    lineChart.apply { //라인차트 세팅
        axisRight.isEnabled = false // y축 오른쪽 데이터 비활성화
        axisLeft.axisMinimum= 0f
        axisLeft.axisMaximum = 100000f // y축 데이터 최대값 50
        legend.apply{
            textSize = 15f // 글자크기
            verticalAlignment = Legend.LegendVerticalAlignment.TOP // 수직조정 -> 위로
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER // 수평조절 -> 가운데로
            orientation= Legend.LegendOrientation.HORIZONTAL // 범례와 차트 정렬 -> 수평
            setDrawInside(false) // 차트안에 그릴것인지
        }
    }
    lineChart.data = lineData
}