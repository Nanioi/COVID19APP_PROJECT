package com.nanioi.practiceproject

import android.graphics.Color
import android.util.Log
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat

fun setLineChart(lineChart: LineChart, result: List<ResponseElement>) {
    val Chart = lineChart
    Chart.invalidate()
    Chart.clear()

    var decide_value: ArrayList<Entry> = arrayListOf()
    var clear_value: ArrayList<Entry> = arrayListOf()
    var xLabel: ArrayList<String> = arrayListOf()

    var decide: String
    var clear: String
    var statedt: String
    var c = 0f
    var size : Int = result.size
    size-=1
    var step_cnt : Int = size/6
    for (i in size downTo 0 step step_cnt) {
        decide = result[i].decideCnt!!
        clear = result[i].clearCnt!!
        decide_value.add(Entry(c, decide.toFloat()))
        clear_value.add(Entry(c, clear.toFloat()))
        c += 1f
        statedt = result[i].stateDt!!
        xLabel.add(statedt)
    }
    var lineDataSet1: LineDataSet = LineDataSet(decide_value, "누적확진")
    lineDataSet1.apply {
        axisDependency = YAxis.AxisDependency.LEFT //  y값을 왼쪽으로
        color = Color.RED
        setCircleColor(getColor(Color.RED)) // 데이터 원색
        valueTextSize = 10f
        lineWidth = 2f
        circleRadius = 3f // 원크기
        fillAlpha = 0 // 라인 색 투명도
        fillColor = getColor(Color.RED) // 라인색
        highLightColor = Color.BLACK
        setDrawValues(true)
    }
    var lineDataSet2: LineDataSet = LineDataSet(clear_value, "격리해제")
    lineDataSet2.apply {
        axisDependency = YAxis.AxisDependency.LEFT //  y값을 왼쪽으로
        color = Color.BLUE
        setCircleColor(getColor(Color.BLUE)) // 데이터 원색
        valueTextSize = 10f
        lineWidth = 2f
        circleRadius = 3f // 원크기
        fillAlpha = 0 // 라인 색 투명도
        fillColor = getColor(Color.BLUE) // 라인색
        highLightColor = Color.BLACK
        setDrawValues(true)
    }

    var lineData = LineData() // linedataset을 담는 그릇, 여러개의 라인데이터가 들어갈수 있음
    lineData.addDataSet(lineDataSet1)
    lineData.addDataSet(lineDataSet2)

    val xAxis = lineChart.xAxis
    xAxis.apply {
        position = XAxis.XAxisPosition.BOTTOM // x축 데이터위치 아래로
        textSize = 10f  // 텍스트 크기
        setDrawGridLines(false) //배경 그리드 라인 세팅
        granularity = 1f // x축 데이터 표시 간격
        isGranularityEnabled = true // x축 간격을 제한하는 세분화 기능
        setValueFormatter(object : ValueFormatter() {
            val pattern = "MM/dd"
            private val mFormat = SimpleDateFormat(pattern)
            private val inputFormat = SimpleDateFormat("yyyyMMdd")
            override fun getFormattedValue(value: Float): String {
                val intValue: Int = value.toInt()
                if (intValue < 0 || intValue >=  xLabel.size) return ""
                else return mFormat.format(inputFormat.parse(xLabel[intValue]))
            }
        })
    }
    lineChart.apply { //라인차트 세팅
        axisRight.isEnabled = false // y축 오른쪽 데이터 비활성화
        axisLeft.axisMinimum = 0f
        axisLeft.axisMaximum = 100000f // y축 데이터 최대값 50
        legend.apply {
            textSize = 15f // 글자크기
            verticalAlignment = Legend.LegendVerticalAlignment.TOP // 수직조정 -> 위로
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER // 수평조절 -> 가운데로
            orientation = Legend.LegendOrientation.HORIZONTAL // 범례와 차트 정렬 -> 수평
            setDrawInside(false) // 차트안에 그릴것인지
        }
    }
    lineChart.data = lineData
}
fun setBarChart(barChart: BarChart, result: List<ResponseElement>) {
    val Chart = barChart
    Chart.invalidate()
    Chart.clear()

    var new_decide_value: ArrayList<BarEntry> = arrayListOf()
    var xLabel: ArrayList<String> = arrayListOf()

    var new_decide: Int
    var statedt: String
    var c = 0f

    for (i in 6 downTo 0 ) {
        new_decide = result[i].decideCnt!!.toInt()
        new_decide -= result[i+1].decideCnt!!.toInt()
        new_decide_value.add(BarEntry(c, new_decide.toFloat()))
        c += 1f
        statedt = result[i].stateDt!!
        xLabel.add(statedt)
    }
    var dataSet: BarDataSet = BarDataSet(new_decide_value, "일별 신규 확진자수")
    dataSet.color = Color.RED

    dataSet.apply {
        axisDependency = YAxis.AxisDependency.LEFT
        setDrawValues(true)
    }

    var barDate = BarData()
    barDate.addDataSet(dataSet)
    barDate.barWidth = 0.3f

    val xAxis = barChart.xAxis
    xAxis.apply {
        position = XAxis.XAxisPosition.BOTTOM
        textSize = 10f
        setDrawGridLines(false)
        granularity = 1f
        isGranularityEnabled = true
        setValueFormatter(object : ValueFormatter() {
            val pattern = "MM/dd"
            private val mFormat = SimpleDateFormat(pattern)
            private val inputFormat = SimpleDateFormat("yyyyMMdd")
            override fun getFormattedValue(value: Float): String {
                val intValue: Int = value.toInt()
                if (intValue < 0 || intValue >=  xLabel.size) return ""
                else return mFormat.format(inputFormat.parse(xLabel[intValue]))
            }
        })
    }
    barChart.apply {
        axisRight.isEnabled = false
        axisLeft.axisMinimum = 0f
        axisLeft.axisMaximum = 800f // y축 데이터 최대값 50
        legend.apply {
            textSize = 15f // 글자크기
            verticalAlignment = Legend.LegendVerticalAlignment.TOP // 수직조정 -> 위로
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER // 수평조절 -> 가운데로
            orientation = Legend.LegendOrientation.HORIZONTAL // 범례와 차트 정렬 -> 수평
            setDrawInside(false) // 차트안에 그릴것인지
        }
    }
    barChart.data = barDate
}