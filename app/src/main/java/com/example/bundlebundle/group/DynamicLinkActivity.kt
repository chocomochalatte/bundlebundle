package com.example.bundlebundle.group

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bundlebundle.databinding.ActivityUpbuttonTemplateBinding
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase

class DynamicLinkActivity : AppCompatActivity() {

    val TAG = "DynamicLinkActivity"

    companion object{
        val SCHEME_GROUP_ID = "groupId"
        val SCHEME_GROUP_OWNER_NICKNAME = "groupOwnerNickname"
    }

    lateinit var binding: ActivityUpbuttonTemplateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpbuttonTemplateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val fragment = GroupJoinFragment()
        putFragment(fragment)

        handleDynamicLinks()
    }

    fun handleDynamicLinks(){
        Log.d("ming", "lfkngvldkn")
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                var deeplink: Uri? = null
                if(pendingDynamicLinkData != null) {
                    deeplink = pendingDynamicLinkData.link
                }
                if (deeplink != null) {
                    Log.d(TAG, "query=" + deeplink.query.toString())
                    Log.d(TAG, "lastpath" + deeplink.lastPathSegment)
                    Log.d(TAG, "path" + deeplink.path.toString())
                }

//                if(deeplink != null) {
////                    val keyValues = mapOf(
////                        "key1" to "value1",
////                        "key2" to "value2"
////                    )
////
////                    val queryString = keyValues.map { (key, value) -> "$key=$value" }.joinToString("&")
////                    val dynamicLink = "https://example.com?${queryString}"
//                    when(segment){
//                        SCHEME_PHEED -> {
//                            //공유 타입이 pheed으로 들어왔을 때 처리
//                            val code: String = deeplink.getQueryParameter(PARAM_ID)!!
//                            Toast.makeText(this, "SCHEME_PHEED 타입 id : $code 데이터 보여주기", Toast.LENGTH_LONG).show()
//                            val intent = Intent(this@SchemeActivity, MainActivity::class.java)
//                            startActivity(intent)
//                        }
//                        SCHEME_COMMENT -> {
//                            //공유 타입이 comment로 들어왔을 때 처리
//                            val code: String = deeplink.getQueryParameter(PARAM_ID)!!
//                            Toast.makeText(this, "SCHEME_COMMENT 타입 id : $code 데이터 보여주기", Toast.LENGTH_LONG).show()
//                            val intent = Intent(this@SchemeActivity, MainActivity::class.java)
//                            startActivity(intent)
//                        }
//                        SCHEME_MAIN -> {
//                            //공유 타입이 main으로 들어왔을 때 처리
//                            Toast.makeText(this, "메인 이동", Toast.LENGTH_LONG).show()
//                            val intent = Intent(this@SchemeActivity, MainActivity::class.java)
//                            startActivity(intent)
//                        }
//                    }
//                }
//                else {
//                    Log.d(TAG, "getDynamicLink: no link found")
//                }
            }
            .addOnFailureListener(this) { e -> Log.e(TAG, "getDynamicLink:onFailure", e) }
    }


    protected fun putFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerUpbutton.id, fragment)
            .commit()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarTransparent.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // 뒤로가기 버튼 클릭 시 수행할 동작
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
