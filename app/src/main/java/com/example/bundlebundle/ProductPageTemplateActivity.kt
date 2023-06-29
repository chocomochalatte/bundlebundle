package com.example.bundlebundle

import android.os.Bundle
import com.example.bundlebundle.template.BaseTemplateActivity

// BaseActivity를 상속받아 만드는 클래스 예시!
class ProductPageTemplateActivity : BaseTemplateActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Activity의 top level에서 보여줄 fragment 설정 -> 이렇게 해야 app bar에 뒤로가기 버튼이 아니라 메뉴 버튼으로 보여짐
    override fun setTopLevelMainFragment(): Set<Int> {
        return setOf(R.id.product_list)
    }
}