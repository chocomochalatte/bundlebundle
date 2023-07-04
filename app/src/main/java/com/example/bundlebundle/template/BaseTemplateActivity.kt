package com.example.bundlebundle.template

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.example.bundlebundle.LoginActivity
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.ActivityBaseBinding
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.dataclass.member.MemberVO
import com.google.android.material.navigation.NavigationView
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

//        if (ApiClient.getJwtToken()!=null) {
//            navigationView.inflateHeaderView(com.example.bundlebundle.R.layout.nav_header_basic)
//            Log.d("test","로그인됨")
//
//            //로그아웃 리스너 설정
//            val headerView = navigationView.getHeaderView(0)
//            val button = headerView.findViewById<Button>(R.id.logout_btn)
//            Log.d("test","button $button")
//            button.setOnClickListener {
//                Log.d("test","로그아웃버튼클릭")
//                ApiClient.setJwtToken(null);
//                //로그아웃 메소드 실행
//                val intent = Intent(this, this@BaseTemplateActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                startActivity(intent)
//                finish()
//            }
//        } else {
//            navigationView.inflateHeaderView(R.layout.nav_header_with_login)
//            Log.d("test","로그인되지 않음")
//
//            // 로그인 리스너 설정
//            val headerView = navigationView.getHeaderView(0)
//            val button = headerView.findViewById<Button>(R.id.btn_oauth_login)
//            Log.d("test","button $button")
//            button.setOnClickListener {
//                Log.d("test","로그인버튼클릭")
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
//            }
//
//        }
//        navigationView.invalidate()
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
                }
            }

            override fun onFailure(call: Call<MemberVO>, t: Throwable) {
                Log.e("TestActivity", "서버 응답이 실패했습니다. 상태 코드: ${t.printStackTrace()}")
            }

        })
    }


    private fun setActionBarAndNavigationDrawer() {
        // Action Bar 설정
        setSupportActionBar(binding.toolbarMain.toolbar)

        // ActionBarDrawerToggle 추가
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbarMain.toolbar,
            com.example.bundlebundle.R.string.navigation_drawer_open,
            com.example.bundlebundle.R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // 툴바 버튼 클릭 시 드로어 열기/닫기
        binding.toolbarMain.toolbar.setNavigationOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        // navigation drawer닫기 버튼
        val closeButton = binding.navView.closeBtn
        closeButton.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            Log.d("jw", "닫기 버튼 클릭했읍니다.")
        }

    }


    /* 상속받은 모든 클래스에서 Override 필요
     * toolbar 밑에 둘 메인 fragment를 무엇으로 할 것인지 아이디로 지정 */
    protected open fun setTopLevelMainFragment(): Set<Int>  {
        return setOf(com.example.bundlebundle.R.id.main_content_fragment_container)
    }

    private fun createAppBarConfiguration(topLevelDestinations: Set<Int>, drawerLayout: DrawerLayout): AppBarConfiguration {
        // AppBarConfiguration 객체 생성하여 반환
        return AppBarConfiguration.Builder(topLevelDestinations)
            .setOpenableLayout(drawerLayout)
            .build()
    }

}
