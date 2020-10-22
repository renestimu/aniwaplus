package com.renestim.aniwaplus.utils

import android.widget.Filter
import com.renestim.aniwaplus.adapter.serilerRecyclerViewAdapter
import com.renestim.aniwaplus.model.dbseri
import java.util.*
import kotlin.collections.ArrayList

class filterHelper(seriler: ArrayList<dbseri>?, adapter: serilerRecyclerViewAdapter) : Filter() {

    var suankiListe = seriler
    var suankiAdapter = adapter


    override fun performFiltering(p0: CharSequence?): FilterResults {
        val sonuc = FilterResults()
        if (p0 != null && p0.length > 0) {
            val aranilan=p0.toString().toLowerCase(Locale.ROOT)

            val eslesen=ArrayList<dbseri>()

            for(seri in suankiListe!!){
                val ad=seri.name?.toLowerCase(Locale.ROOT)
                if(ad?.contains(aranilan)!!){
                    eslesen.add(seri)
                }
            }
            sonuc.values=eslesen
            sonuc.count=eslesen.size


        }else{
            sonuc.values=suankiListe
            sonuc.count= suankiListe?.size!!
        }

        return sonuc

    }

    override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
      suankiAdapter.setFilter(p1?.values as ArrayList<dbseri>)
        suankiAdapter.notifyDataSetChanged()
    }
}