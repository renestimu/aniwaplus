package com.renestim.aniwaplus.utils

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.renestim.aniwaplus.*


class BottomNavHelper {
    companion object{
        lateinit var drawerLayout:DrawerLayout
        lateinit var navview:NavigationView

        fun setupBottomNavView(bottomNavViewEx: BottomNavigationViewEx,dl:DrawerLayout,nv:NavigationView,context:Context){
            bottomNavViewEx.enableAnimation(false)
            bottomNavViewEx.enableItemShiftingMode(false)
            bottomNavViewEx.enableShiftingMode(false)
            bottomNavViewEx.setTextVisibility(false)
            drawerLayout=dl
            navview=nv
            navview.setNavigationItemSelectedListener{
                when(it.itemId){
                    R.id.nav_profile -> {
                        Toast.makeText(context, "Profile clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.nav_messages -> {
                        Toast.makeText(context, "Messages clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.nav_friends -> {
                        Toast.makeText(context, "Friends clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.nav_update -> {
                        Toast.makeText(context, "Update clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.nav_logout -> {
                        Toast.makeText(context, "Sign out clicked", Toast.LENGTH_SHORT).show()
                    }
                }
                drawerLayout.closeDrawer(Gravity.RIGHT)
                return@setNavigationItemSelectedListener true;

            }
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
                            drawerLayout.openDrawer(Gravity.RIGHT)
                            return  true
                        }
                    }
                    return  false;
                }

            }
        }


    }
}