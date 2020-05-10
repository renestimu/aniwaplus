package com.renestim.aniwaplus.utils

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.renestim.aniwaplus.*


class BottomNavHelper {
    companion object{
        fun setupBottomNavView(bottomNavViewEx: BottomNavigationViewEx){
            bottomNavViewEx.enableAnimation(false)
            bottomNavViewEx.enableItemShiftingMode(false)
            bottomNavViewEx.enableShiftingMode(false)
            bottomNavViewEx.setTextVisibility(false)
        }
        fun setupNav(context: Context, bottomNavViewEx: BottomNavigationViewEx){
            bottomNavViewEx.onNavigationItemSelectedListener= object : BottomNavigationView.OnNavigationItemSelectedListener{
                override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                    when(p0.itemId){
                        R.id.ic_lastadd ->{
                            val intent=Intent(context,MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            context.startActivity(intent)
                            return  true
                        }
                        R.id.ic_seriler ->{
                            val intent=Intent(context,SerilerActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            context.startActivity(intent)
                            return  true
                        }
                        R.id.ic_category ->{
                            val intent=Intent(context,CategoryActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            context.startActivity(intent)
                            return  true
                        }
                        R.id.ic_favoriler ->{
                            val intent=Intent(context,FavorilerActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            context.startActivity(intent)
                            return  true
                        }
                        R.id.ic_menu ->{
                            val intent=Intent(context,MainActivity::class.java)
                            context.startActivity(intent)
                            return  true
                        }
                    }
                    return  false;
                }

            }
        }
    }
}