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
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.w3c.dom.Text


abstract class BaseTemplateActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    protected lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBarAndNavigationDrawer()

        // 메인 fragment 넣기
        val topLevelDestinations = setTopLevelMainFragment()
        appBarConfiguration = createAppBarConfiguration(topLevelDestinations, binding.drawerLayout)

        val textView = binding.navView.getHeaderView(0).findViewById<TextView>(R.id.current_user_name)
        val apiService = ApiClient.apiService

        //API 요청 시작


        textView.text = "노이노"+"님";
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
