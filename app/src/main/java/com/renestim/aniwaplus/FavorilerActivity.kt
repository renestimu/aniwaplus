package com.renestim.aniwaplus

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.renestim.aniwaplus.adapter.favorilerRecyclerViewAdapter

import com.renestim.aniwaplus.model.dbHelper
import com.renestim.aniwaplus.model.dbseri
import com.renestim.aniwaplus.utils.BottomNavHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_favoriler.*

import kotlinx.android.synthetic.main.content_main.bottomNavigationView


class FavorilerActivity : AppCompatActivity() {

    private val activty_no=3
    private  val Tag="FavorilerActivity"

    private var seriList: ArrayList<dbseri>? = null
    var myAdapter: favorilerRecyclerViewAdapter?=null
    lateinit var context: Context

    val db by lazy { dbHelper(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoriler)
        setupNavView()
        context = this


        setupNavView()

        seriList=db.getAllseriFavori()


        myAdapter= favorilerRecyclerViewAdapter(context = context, seriler = seriList )
        favorilergetir.adapter=myAdapter
        favorilergetir.layoutManager=
            LinearLayoutManager(context, RecyclerView.VERTICAL,false)

    }
    fun setupNavView(){
        BottomNavHelper.setupBottomNavView(bottomNavigationView,drawer_layout,nav_view,this)
        BottomNavHelper.setupNav(this,bottomNavigationView)
        var menu = bottomNavigationView.menu
        var menuItem=menu.getItem(activty_no)
        menuItem.setChecked(true)
    }
}
