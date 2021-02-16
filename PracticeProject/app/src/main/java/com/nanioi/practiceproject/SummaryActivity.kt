package com.nanioi.practiceproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.util.Linkify
import kotlinx.android.synthetic.main.activity_summary.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class SummaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        domestic_btn.setOnClickListener {
            startActivity(Intent(this, DomesticStatusActivity::class.java))
        }
        overseas_btn.setOnClickListener {
            startActivity(Intent(this, OverseasStatusActivity::class.java))
        }
        precaution_btn.setOnClickListener {
            startActivity(Intent(this, PrecautionActivity::class.java))
        }

        //특정 문자열에 링크연결
        val transform = Linkify.TransformFilter(object : Linkify.TransformFilter, (Matcher, String) -> String {
            override fun transformUrl(p0: Matcher?, p1: String?): String {
                return ""
            }
            override fun invoke(p1: Matcher, p2: String): String {
                return ""
            }
        })

        val pattern1 = Pattern.compile("더보기")
        val pattern2 = Pattern.compile("선별 진료소")
        val pattern3 = Pattern.compile("관할 보건소")
        val pattern4 = Pattern.compile("보도자료")
        val pattern5 = Pattern.compile("최신뉴스")
        val pattern6 = Pattern.compile("SNU 팩트체크")
        val pattern7 = Pattern.compile("대응수칙")
        val pattern8 = Pattern.compile("전자출입명부 안내")
        val pattern9 = Pattern.compile("네이버 QR체크인 안내")
        val pattern10 = Pattern.compile("중앙재난안전대책본부")
        val pattern11 = Pattern.compile("중앙사고수습본부")
        val pattern12 = Pattern.compile("중앙방역대책본부")
        val pattern13 = Pattern.compile("Johns Hopkins CSSE")

        Linkify.addLinks(justice, pattern1, "https://terms.naver.com/entry.nhn?docId=5912275&cid=43667&categoryId=43667", null, transform)
        Linkify.addLinks(check, pattern1, "https://terms.naver.com/entry.nhn?docId=5916219&cid=66630&categoryId=66630#TABLE_OF_CONTENT3", null, transform)
        Linkify.addLinks(treatment, pattern1,
                "https://terms.naver.com/entry.nhn?docId=5916219&cid=66630&categoryId=66630#TABLE_OF_CONTENT4",
                null,
                transform
        )
        Linkify.addLinks(emergency_number, pattern2,
                "https://search.naver.com/search.naver?where=nexearch&sm=tab_etc&query=%EC%BD%94%EB%A1%9C%EB%82%9819%20%EC%84%A0%EB%B3%84%EC%A7%84%EB%A3%8C%EC%86%8C",
                null,
                transform
        )
        Linkify.addLinks(emergency_number, pattern3,
                "https://search.naver.com/search.naver?where=nexearch&sm=tab_etc&query=%EB%B3%B4%EA%B1%B4%EC%86%8C",
                null,
                transform
        )
        Linkify.addLinks(information, pattern4,
                "http://ncov.mohw.go.kr/tcmBoardList.do?brdId=&brdGubun=&dataGubun=&ncvContSeq=&contSeq=&board_id=&gubun=",
                null,
                transform
        )
        Linkify.addLinks(information, pattern5,
                "https://news.naver.com/main/hotissue/sectionList.nhn?mid=hot&sid1=102&gid=1086319",
                null,
                transform
        )
        Linkify.addLinks(information, pattern6,
                "https://news.naver.com/main/factcheck/main.nhn?section=%C4%DA%B7%CE%B3%AA+%B9%D9%C0%CC%B7%AF%BD%BA",
                null,
                transform
        )
        Linkify.addLinks(information, pattern7,
                "http://ncov.mohw.go.kr/duBoardList.do?brdId=2&brdGubun=21&dataGubun=&ncvContSeq=&contSeq=&board_id=&gubun=",
                null,
                transform
        )
        Linkify.addLinks(information, pattern8,
                "https://blog.naver.com/mohw2016/221995921830",
                null,
                transform
        )
        Linkify.addLinks(information, pattern9,
                "https://blog.naver.com/naver_diary/221995124479",
                null,
                transform
        )
        Linkify.addLinks(source, pattern10,
                "http://ncov.mohw.go.kr/",
                null,
                transform
        )
        Linkify.addLinks(source, pattern11,
                "http://www.mohw.go.kr/react/index.jsp",
                null,
                transform
        )
        Linkify.addLinks(source, pattern12,
                "http://www.cdc.go.kr/index.es?sid=a2",
                null,
                transform
        )
        Linkify.addLinks(source, pattern13,
                "https://www.arcgis.com/apps/opsdashboard/index.html#/bda7594740fd40299423467b48e9ecf6",
                null,
                transform
        )
    }
}