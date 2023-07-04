package com.example.bundlebundle.template

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.ActivityBaseBinding
import com.example.bundlebundle.product.list.MenuTabAdapter
import com.example.bundlebundle.product.list.ProductGridFragment
import com.example.bundlebundle.retrofit.ApiClient
import com.example.bundlebundle.retrofit.dataclass.member.MemberVO
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.w3c.dom.Text
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

        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        if (ApiClient.getJwtToken()!=null) {
            navigationView.inflateHeaderView(R.layout.nav_header_basic)
            Log.d("test","로그인됨")
        } else {
            navigationView.inflateHeaderView(R.layout.nav_header_with_login)
            Log.d("test","로그인되지 않음")
        }

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBarAndNavigationDrawer()

        // 메인 fragment 넣기
        val topLevelDestinations = setTopLevelMainFragment()
        appBarConfiguration = createAppBarConfiguration(topLevelDestinations, binding.drawerLayout)

        val textView = binding.navView.getHeaderView(0).findViewById<TextView>(R.id.current_user_name)
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

        // navigation drawer 구성요소 초기화
        val navView: NavigationView = binding.navView

        // ActionBarDrawerToggle 추가
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // navigation drawer닫기 버튼
        val navHeaderView = navView.getHeaderView(0)
        val closeButton = navHeaderView.findViewById<ImageButton>(R.id.close_btn)
        closeButton?.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

    }

    /* 상속받은 모든 클래스에서 Override 필요
     * toolbar 밑에 둘 메인 fragment를 무엇으로 할 것인지 아이디로 지정 */
    protected open fun setTopLevelMainFragment(): Set<Int>  {
        return setOf(R.id.main_content_fragment_container)
    }

    private fun createAppBarConfiguration(topLevelDestinations: Set<Int>, drawerLayout: DrawerLayout): AppBarConfiguration {
        // AppBarConfiguration 객체 생성하여 반환
        return AppBarConfiguration.Builder(topLevelDestinations)
            .setOpenableLayout(drawerLayout)
            .build()
    }

}
