package com.nanioi.practiceproject

import android.os.AsyncTask
import android.util.Xml
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.net.URLDecoder
import java.net.URLEncoder

private val ns: String? = null


data class ResponseElement(
        val clearCnt: String?,
        val deathCnt: String?,
        val decideCnt: String?,
        val examCnt: String?,
        val stateDt : String?,
        val stateTime : String?
)

class XmlParsingTask(
        val decide : TextView,
        val decide_new : TextView,
        val exam : TextView,
        val exam_new : TextView,
        val clear : TextView,
        val clear_new : TextView,
        val death : TextView,
        val death_new : TextView,
        val state_dt : TextView,
        val state_time : TextView,
        val accumulate_chart : LineChart
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
        decide_new.setText("+"+decide_new_cnt)
        exam.setText(data.examCnt)
        exam_new.setText(exam_new_cnt)
        clear.setText(data.clearCnt)
        clear_new.setText("+"+clear_new_cnt)
        death.setText(data.deathCnt)
        death_new.setText("+"+death_new_cnt)
        state_dt.setText(data.stateDt)
        state_time.setText(data.stateTime)
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
    return ResponseElement(clearCnt, deathCnt, decideCnt, examCnt,stateDt,stateTime)
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