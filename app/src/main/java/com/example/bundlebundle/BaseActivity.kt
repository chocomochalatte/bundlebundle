package com.example.bundlebundle

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bundlebundle.databinding.ActivityBaseBinding
import com.google.android.material.navigation.NavigationView


abstract class BaseActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Action Bar 설정
        setSupportActionBar(binding.toolbarMain.toolbar)

        // navigation drawer 구성요소 초기화
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_basic)

        // 메인 fragment 넣기
        val topLevelDestinations = setTopLevelMainFragment()
        appBarConfiguration = createAppBarConfiguration(topLevelDestinations, binding.drawerLayout)

        // navigation controller 설정
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // navigation drawer닫기 버튼
        val navHeaderView = navView.getHeaderView(0)
        val closeButton = navHeaderView.findViewById<ImageButton>(R.id.close_btn)

        closeButton?.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    /* 상속받은 모든 클래스에서 Override 필요
     * toolbar 밑에 둘 메인 fragment를 무엇으로 할 것인지 아이디로 지정 */
    protected abstract fun setTopLevelMainFragment(): Set<Int>

    private fun createAppBarConfiguration(topLevelDestinations: Set<Int>, drawerLayout: DrawerLayout): AppBarConfiguration {
        // AppBarConfiguration 객체 생성하여 반환
        return AppBarConfiguration.Builder(topLevelDestinations)
            .setOpenableLayout(drawerLayout)
            .build()
    }

    // 메뉴를 액션 바에 인플레이트하여 추가
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.base, menu)
        return true
    }

    // 상위 네비게이션 버튼을 눌렀을 때 호출됨 -> 현재의 AppBarConfiguration과 함께 네비게이션을 처리하고, 처리 여부를 반환
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_basic)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
