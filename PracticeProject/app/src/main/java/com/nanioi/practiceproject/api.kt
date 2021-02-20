package com.nanioi.practiceproject

// xml 파싱방법

//import android.os.Build
//import androidx.annotation.RequiresApi
//import org.w3c.dom.Document
//import org.w3c.dom.Node
//import org.w3c.dom.NodeList
//import org.w3c.dom.Element
//import javax.xml.parsers.DocumentBuilder
//import javax.xml.parsers.DocumentBuilderFactory
//
//@RequiresApi(Build.VERSION_CODES.N)
//fun main() {
//
//    val key: String = "Tdo19TVJxANWay1HQ1dxcwGA5sJ73wYF%2FVfvQaLyA1iBPWkttg74N9jzEUDGlG6J3ItutuWKuzOutjEblPuQIg%3D%3D"
//    var pageNumber : Int = 1
//    var numRows : Int = 10
//    var startDt : String = "20200310"
//    var endDt : String = "20200315"
//    var url: String =
//        "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson?serviceKey=" + key +
//                "&pageNo="+ pageNumber + "&numOfRows="+ numRows+"&startCreateDt=" + startDt + "&endCreateDt=" + endDt
//
//    val xml: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url)
//    xml.documentElement.normalize()
//    println("Root element : " + xml.documentElement.nodeName)
//
//    val list: NodeList = xml.getElementsByTagName("item")
//
//    for (i in 0..list.length - 1) {
//
//        var n: Node = list.item(i)
//
//        if (n.getNodeType() == Node.ELEMENT_NODE) {
//
//            val elem = n as Element
//
//            val map = mutableMapOf<String, String>()
//
//
//            for (j in 0..elem.attributes.length - 1) {
//
//                map.putIfAbsent(elem.attributes.item(j).nodeName, elem.attributes.item(j).nodeValue)
//
//            }
//
//            //list.length-1 (건축물 대장의 경우 디폴트 = 10)만큼 얻고자 하는 태그의 정보를 가져온다
//
//            println("=========${i + 1}=========")
//
//            println("1. 확진자 수 : ${elem.getElementsByTagName("DECIDE_CNT").item(0).textContent}")
//
//            println("2. 검사진행 수 : ${elem.getElementsByTagName("EXAM_CNT").item(0).textContent}")
//
//            println("3. 격리해제 수 : ${elem.getElementsByTagName("CLEAR_CNT").item(0).textContent}")
//
//            println("4. 사망자 수 : ${elem.getElementsByTagName("DEATH_CNT").item(0).textContent}")
//
//            println("5. 누적 환진률 : ${elem.getElementsByTagName("ACC_DEF_RATE").item(0).textContent}")
//
//        }
//
//    }
//}