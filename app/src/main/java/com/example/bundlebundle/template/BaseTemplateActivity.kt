package com.example.bundlebundle.template

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.example.bundlebundle.CartActivity
import com.example.bundlebundle.LoginActivity
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.ActivityBaseBinding
import com.example.bundlebundle.product.list.ProductPageActivity
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.dataclass.member.MemberVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


abstract class BaseTemplateActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    protected lateinit var binding: ActivityBaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateNavViewLayout()
        setActionBarAndNavigationDrawer()

        // 메인 fragment 넣기
        val topLevelDestinations = setTopLevelMainFragment()
        appBarConfiguration = createAppBarConfiguration(topLevelDestinations, binding.drawerLayout)

        val textView = binding.navView.currentUserName
        var myName : String? = "기본이름입니다";
        //API 요청 시작
        val apiService = ApiClient.apiService
        val call: Call<MemberVO> = apiService.getmember()
        call.enqueue(object : Callback<MemberVO> {
            override fun onResponse(call: Call<MemberVO>, response: Response<MemberVO>) {
                if (response.isSuccessful) {
                    val memberInfo = response.body()
                    memberInfo?.let { info ->
                        // 서버 응답 처리
                        Log.i("TestActivity", "멤버 정보 받아오기 성공 $memberInfo")
                        myName= memberInfo.username;
                        Log.i("TestActivity", "myName $myName")
                        textView.text = myName+"님";
                    } ?: run {
                        // 응답이 null인 경우 처리
                        Log.e("TestActivity", "서버 응답이 null입니다.")
                    }
                } else {
                    // 응답이 실패한 경우 처리
                    Log.e("TestActivity", "서버 응답이 실패했습니다. 상태 코드: ${response.code()}")
                    showAlert("ERROR : ${response.body()}", "서버 응답이 실패했습니다. 메인 화면으로 돌아갑니다.", DialogInterface.OnClickListener { dialog, _ -> moveToMain() })
                }
            }

            override fun onFailure(call: Call<MemberVO>, t: Throwable) {
                Log.e("TestActivity", "서버 응답이 실패했습니다. 상태 코드: ${t.printStackTrace()}")
                showAlert("ERROR : ${t.message}", "서버 응답이 실패했습니다. 메인 화면으로 돌아갑니다.", DialogInterface.OnClickListener { dialog, _ -> moveToMain() })
            }
        })
    }

    fun updateNavViewLayout() {
        val navView: ViewGroup = findViewById(R.id.nav_view)
        val navLayout: Int = when (ApiClient.getJwtToken()) {
            null -> R.layout.nav_header_before_login
            else -> R.layout.nav_header_basic
        }
        val inflater = layoutInflater
        inflater.inflate(navLayout, navView, true)
    }

    private fun setActionBarAndNavigationDrawer() {
        // Action Bar 설정
        setSupportActionBar(binding.toolbarMain.toolbar)

        val drawerLayout_home = binding.drawerLayout

        val menuCloseButton = binding.navView.closeBtn
        menuCloseButton.setOnClickListener {
            drawerLayout_home.closeDrawer(GravityCompat.START)
        }

        val cartShortcutBtn = binding.toolbarMain.cartImage
        cartShortcutBtn.setOnClickListener {
            when (isLogedIn()) {
                true -> {
                    val newIntent = Intent(this, CartActivity::class.java)
                    newIntent.putExtra("tab", "personal")
                    startActivity(newIntent)
                }
                else -> {
                    val posListener = DialogInterface.OnClickListener { dialog, _ -> goToLogin()}
                    showAlert("로그인이 필요합니다.", "로그인 페이지로 이동합니다.", posListener)
                }
            }
        }

        // 메뉴 버튼 클릭 리스너 설정
        val menuButton = binding.toolbarMain.menuBtn
        menuButton.setOnClickListener {
            // 드로워 토글
            if (drawerLayout_home.isDrawerOpen(GravityCompat.START)) {
                drawerLayout_home.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout_home.openDrawer(GravityCompat.START)
            }
        }

//        val logoutBtn = binding.navView.logoutBtn
//        logoutBtn.setOnClickListener {
//            Log.d("fgkjnbdjkv", "fdjbfskdjnbfvkj")
//            true
//        }

        val loginBtn = binding.navView.btnOauthLogin
        loginBtn.setOnClickListener {
            Log.d("test","로그인 버튼 클릭")
            goToLogin()
        }

    }

    private fun showAlert(title: String, message: String, positiveListener: DialogInterface.OnClickListener) {
        val negativeListener = DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }

        AlertDialog.Builder(this, R.style.MyAlertDialogTheme).run {
            setTitle(title)
            setMessage(message)
            setPositiveButton("확인", positiveListener)
            setNegativeButton("취소", negativeListener)
            create()
        }.show()
    }

    private fun moveToMain() {
        val intent = Intent(this, ProductPageActivity::class.java)
        startActivity(intent)
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun isLogedIn(): Boolean {
        return (ApiClient.getJwtToken() != null)
    }

    protected open fun setTopLevelMainFragment(): Set<Int>  {
        return setOf(com.example.bundlebundle.R.id.main_content_fragment_container)
    }

    private fun createAppBarConfiguration(topLevelDestinations: Set<Int>, drawerLayout: DrawerLayout): AppBarConfiguration {
        return AppBarConfiguration.Builder(topLevelDestinations)
            .setOpenableLayout(drawerLayout)
            .build()
    }

}
