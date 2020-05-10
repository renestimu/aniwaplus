package com.renestim.aniwaplus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.renestim.aniwaplus.utils.BottomNavHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val activty_no=0
    private  val Tag="MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavView()
    }
    fun setupNavView(){
        BottomNavHelper.setupBottomNavView(bottomNavigationView)
        BottomNavHelper.setupNav(this,bottomNavigationView)
        var menu = bottomNavigationView.menu
        var menuItem=menu.getItem(activty_no)
        menuItem.setChecked(true)
    }
}
