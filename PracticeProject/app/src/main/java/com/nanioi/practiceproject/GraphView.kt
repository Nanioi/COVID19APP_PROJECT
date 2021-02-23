package com.nanioi.practiceproject

import android.graphics.Color
import android.util.Log
import androidx.core.graphics.red
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

//
//import android.content.Context
//import android.graphics.Paint
//import android.view.View
//import com.github.mikephil.charting.charts.LineChart
//import com.github.mikephil.charting.components.Legend
//import com.github.mikephil.charting.components.XAxis
//import com.github.mikephil.charting.data.LineData
//
//public class GraphView : View{
//    var BAR : Boolean = true
//    var LINE : Boolean = false
//
//    private var paint: Paint? = null
//    private var values: Array<Float> = arrayOf()
//    private var horlabels: Array<String> = arrayOf()
//    private var verlabels: Array<String> = arrayOf()
//    private var title: String? = null
//    private var type = false
//
//    fun GraphView(context: Context?, values: Array<Float>?, title: String?, horlabels: Array<String?>?, verlabels: Array<String?>?, type: Boolean) {
//        var values = values
//        var title = title
//        super.context
//        if (values == null) values = Array<Float>.get(0) else this.values = values
//        if (title == null) title = "" else this.title = title
//        if (horlabels == null) this.horlabels = arrayOfNulls(0) else this.horlabels = horlabels
//        if (verlabels == null) this.verlabels = arrayOfNulls(0) else this.verlabels = verlabels
//        this.type = type
//        paint = Paint()
//    }
//
//}
//private fun setChart(lineChart : LineChart, result : List<ResponseElement>){
//
//    val xAxis = lineChart.xAxis
//
//    xAxis.apply{
//        position = XAxis.XAxisPosition.BOTTOM // x축 데이터위치 아래로
//        textSize = 10f  // 텍스트 크기
//        setDrawGridLines(false) //배경 그리드 라인 세팅
//        granularity =1f // x축 데이터 표시 간격
//        axisMinimum =2f // x축 데이터의 최소 표시값
//        isGranularityEnabled= true // x축 간격을 제한하는 세분화 기능
//    }
//    lineChart.apply { //라인차트 세팅
//        axisRight.isEnabled = false // y축 오른쪽 데이터 비활성화
//        axisLeft.axisMaximum = 120000f // y축 데이터 최대값 50
//        legend.apply{
//            textSize = 15f // 글자크기
//            verticalAlignment = Legend.LegendVerticalAlignment.TOP // 수직조정 -> 위로
//            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER // 수평조절 -> 가운데로
//            orientation= Legend.LegendOrientation.HORIZONTAL // 범례와 차트 정렬 -> 수평
//            setDrawInside(false) // 차트안에 그릴것인지
//        }
//    }
//    val lineData : LineData = LineData()
//    lineChart.data = lineData
//    feedMultiple(result)
//}
//private fun feedMultiple(result : List<ResponseElement>){
//
//}

//private fun setChart(lineChart : LineChart, result : List<ResponseElement>){
//
//    val xAxis = lineChart.xAxis
//
//    xAxis.apply{
//        position = XAxis.XAxisPosition.BOTTOM // x축 데이터위치 아래로
//        textSize = 10f  // 텍스트 크기
//        setDrawGridLines(false) //배경 그리드 라인 세팅
//        granularity =1f // x축 데이터 표시 간격
//        axisMinimum =2f // x축 데이터의 최소 표시값
//        isGranularityEnabled= true // x축 간격을 제한하는 세분화 기능
//    }
//    lineChart.apply { //라인차트 세팅
//        axisRight.isEnabled = false // y축 오른쪽 데이터 비활성화
//        axisLeft.axisMaximum = 120000f // y축 데이터 최대값 50
//        legend.apply{
//            textSize = 15f // 글자크기
//            verticalAlignment = Legend.LegendVerticalAlignment.TOP // 수직조정 -> 위로
//            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER // 수평조절 -> 가운데로
//            orientation= Legend.LegendOrientation.HORIZONTAL // 범례와 차트 정렬 -> 수평
//            setDrawInside(false) // 차트안에 그릴것인지
//        }
//    }
//    val data = result as LineData
//
//    Log.d ("dataaaaa" , "result : "+data)
//    data?.let {
//        var set : ILineDataSet? = data.getDataSetByIndex(0)
//
//        if(set==null){
//            set = createSet()
//            data.addDataSet(set)
//        }
//        //data.addEntry(Entry(set.,f),0)
//    }
//}
//private fun createSet(): LineDataSet {
//    val set = LineDataSet(null,"누적확진자 수")
//    set.apply {
//        axisDependency = YAxis.AxisDependency.LEFT //  y값을 왼쪽으로
//        color = getColor(color.red)
//        setCircleColor(getColor(color.red)) // 데이터 원색
//        valueTextSize = 10f
//        lineWidth = 2f
//        circleRadius=3f // 원크기
//        fillAlpha = 0 // 라인 색 투명도
//        fillColor = getColor(color.red) // 라인색
//        highLightColor = Color.BLACK
//        setDrawValues(true)
//    }
//    return set
//}