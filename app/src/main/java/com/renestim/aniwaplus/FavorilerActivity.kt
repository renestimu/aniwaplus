package com.renestim.aniwaplus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.renestim.aniwaplus.utils.BottomNavHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class FavorilerActivity : AppCompatActivity() {

    private val activty_no=3
    private  val Tag="FavorilerActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavView()
    }
    fun setupNavView(){
        BottomNavHelper.setupBottomNavView(bottomNavigationView,drawer_layout,nav_view,this)
        BottomNavHelper.setupNav(this,bottomNavigationView)
        var menu = bottomNavigationView.menu
        var menuItem=menu.getItem(activty_no)
        menuItem.setChecked(true)
    }
}
