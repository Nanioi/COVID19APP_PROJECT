package com.nanioi.practiceproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.util.Linkify
import kotlinx.android.synthetic.main.activity_precaution.accumulate_btn
import kotlinx.android.synthetic.main.activity_precaution.new_btn
import kotlinx.android.synthetic.main.activity_precaution.source
import kotlinx.android.synthetic.main.activity_precaution.summary_btn
import java.util.regex.Matcher
import java.util.regex.Pattern

class PrecautionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_precaution)

        summary_btn.setOnClickListener {
            startActivity(Intent(this,SummaryActivity::class.java))
        }
        accumulate_btn.setOnClickListener {
            startActivity(Intent(this,AccumulateActivity::class.java))
        }
        new_btn.setOnClickListener {
            startActivity(Intent(this,NewActivity::class.java))
        }

        val transform = Linkify.TransformFilter(object : Linkify.TransformFilter, (Matcher, String) -> String {
            override fun transformUrl(p0: Matcher?, p1: String?): String {
                return ""
            }
            override fun invoke(p1: Matcher, p2: String): String {
                return ""
            }
        })
        val pattern1 = Pattern.compile("중앙재난안전대책본부")
        val pattern2 = Pattern.compile("중앙사고수습본부")
        val pattern3 = Pattern.compile("중앙방역대책본부")
        val pattern4 = Pattern.compile("Johns Hopkins CSSE")

        Linkify.addLinks(source, pattern1,
                "http://ncov.mohw.go.kr/",
                null,
                transform
        )
        Linkify.addLinks(source, pattern2,
                "http://www.mohw.go.kr/react/index.jsp",
                null,
                transform
        )
        Linkify.addLinks(source, pattern3,
                "http://www.cdc.go.kr/index.es?sid=a2",
                null,
                transform
        )
        Linkify.addLinks(source, pattern4,
                "https://www.arcgis.com/apps/opsdashboard/index.html#/bda7594740fd40299423467b48e9ecf6",
                null,
                transform
        )

    }
}