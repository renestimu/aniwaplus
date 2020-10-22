package com.renestim.aniwaplus

import android.content.Context
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.renestim.aniwaplus.adapter.serilerRecyclerViewAdapter
import com.renestim.aniwaplus.model.dbHelper
import com.renestim.aniwaplus.model.dbseri
import com.renestim.aniwaplus.utils.BottomNavHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.bottomNavigationView
import kotlinx.android.synthetic.main.content_seriler.*


class SerilerActivity : AppCompatActivity() {

    private val activty_no=1
    lateinit var context: Context
    private  val Tag="SerilerActivity"

    private var seriList: ArrayList<dbseri>? = null
    var myAdapter: serilerRecyclerViewAdapter?=null
    val db by lazy { dbHelper(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seriler)
        context = this
        seriList = ArrayList<dbseri>()

        setupNavView()

        seriList=db.getAllseri()



      //  var seriList2 = seriList?.sortedWith(compareBy({it.name}))?.toList<dbseri>() as ArrayList<dbseri>
        myAdapter= serilerRecyclerViewAdapter(context = context, seriler = seriList )
        seriekle.adapter=myAdapter
        seriekle.layoutManager=
            LinearLayoutManager(context, RecyclerView.VERTICAL,false)



        searchViewSeriler.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return  false

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                myAdapter!!.filter.filter(newText)
                return false
            }

        }
        )


       // getSeriTask().execute();
    }
    fun setupNavView(){
        BottomNavHelper.setupBottomNavView(bottomNavigationView,drawer_layout,nav_view,this)
        BottomNavHelper.setupNav(this,bottomNavigationView)
        var menu = bottomNavigationView.menu
        var menuItem=menu.getItem(activty_no)
        menuItem.setChecked(true)
    }


}
