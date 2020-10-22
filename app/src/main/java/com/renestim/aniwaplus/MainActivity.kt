package com.renestim.aniwaplus

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.renestim.aniwaplus.adapter.bolumlerRecyclerviewAdapter
import com.renestim.aniwaplus.ksoaplib.Ksoap
import com.renestim.aniwaplus.model.dbbolum
import com.renestim.aniwaplus.model.dbbolums
import com.renestim.aniwaplus.utils.BottomNavHelper
import com.renestim.aniwaplus.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private val activty_no = 0
    private val Tag = "MainActivity"
    lateinit var context: Context
    private var bolumList: ArrayList<dbbolum>? = null
    var myAdapter:bolumlerRecyclerviewAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        bolumList = ArrayList<dbbolum>()



        setupNavView()

        getSonAnimeTask().execute();
    }

    fun setupNavView() {
        BottomNavHelper.setupBottomNavView(bottomNavigationView, drawer_layout, nav_view, this)
        BottomNavHelper.setupNav(this, bottomNavigationView)
        var menu = bottomNavigationView.menu
        var menuItem = menu.getItem(activty_no)
        menuItem.setChecked(true)
    }


    internal inner class getSonAnimeTask() : AsyncTask<String, String, String>() {

        override fun onPreExecute() {

        }

        override fun onPostExecute(result: String?) {
            if (result != "hata") {
                var jsonarray: JSONArray? = null
                try {
                    jsonarray = JSONArray(result)
                    for (i in 0..jsonarray.length()-1) {
                        val jsonobject = jsonarray.getJSONObject(i)
                        val ekle = dbbolum()
                        ekle.id = jsonobject.getInt("id");
                        ekle.seri = jsonobject.getInt("seri");
                        ekle.bolum = jsonobject.getInt("bolum");
                        ekle.name = jsonobject.getString("name");
                        ekle.tarih = jsonobject.getString("tarih");
                        ekle.img = jsonobject.getString("img");
                        ekle.ind = jsonobject.getInt("ind");
                        ekle.izl = jsonobject.getInt("izl");
                        ekle.like = jsonobject.getInt("like");
                        ekle.dislike = jsonobject.getInt("dislike");
                        val alternatif: ArrayList<dbbolums> = ArrayList<dbbolums>()
                        val ja: JSONArray = jsonobject.getJSONArray("alternatif");
                        for (j in 0..ja.length()-1) {
                            val jsonobject2 = ja.getJSONObject(j)
                            val ekle1 = dbbolums()
                            ekle1.url = jsonobject2.getString("url");
                            ekle1.tip = jsonobject2.getString("urltip");
                            ekle1.fansub = jsonobject2.getString("fansub");
                            alternatif.add(ekle1)
                        }
                        ekle.alternatif = alternatif

                        bolumList?.add(ekle)


                    }
                    myAdapter= bolumlerRecyclerviewAdapter(context = context, bolumler = bolumList)
                    sonEklenen.adapter=myAdapter
                    sonEklenen.layoutManager=LinearLayoutManager(this@MainActivity,RecyclerView.VERTICAL,false)

                } catch (ex: Exception) {
                    Log.v( "Hata",ex.toString())
                    Toast.makeText(
                        context, "Veriler sunucudan çekilemedi.",
                        Toast.LENGTH_SHORT
                    ).show();
                }
            }
        }

        override fun doInBackground(vararg params: String?): String {
            return Ksoap.getSonAnime(orgurl = Utils.getorgurl(context = context).toString(), id = 0)
        }
    }


}
