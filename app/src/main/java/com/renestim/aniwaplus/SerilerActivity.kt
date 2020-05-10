package com.renestim.aniwaplus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.renestim.aniwaplus.utils.BottomNavHelper
import kotlinx.android.synthetic.main.activity_main.*

class SerilerActivity : AppCompatActivity() {

    private val activty_no=1
    private  val Tag="SerilerActivity"
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