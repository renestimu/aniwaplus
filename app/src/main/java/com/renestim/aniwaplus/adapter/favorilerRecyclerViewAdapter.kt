package com.renestim.aniwaplus.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.renestim.aniwaplus.R
import com.renestim.aniwaplus.model.dbbolum
import com.renestim.aniwaplus.model.dbseri
import com.renestim.aniwaplus.utils.filterHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.seriler_layout.view.*
import java.lang.Exception

class favorilerRecyclerViewAdapter(context: Context, seriler: ArrayList<dbseri>?):RecyclerView.Adapter<favorilerRecyclerViewAdapter.favorilerlerViewHolder>(){

    var myContext = context
    var mySeriler = seriler



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): favorilerlerViewHolder {
        var inflater = LayoutInflater.from(myContext)
        var view = inflater.inflate(R.layout.seriler_layout, parent, false)

        return favorilerlerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mySeriler!!.size
    }

    override fun onBindViewHolder(holder: favorilerlerViewHolder, position: Int) {
        var oankiseri=mySeriler?.get(position)
        holder.setData(oankiseri,position)
    }



    inner class favorilerlerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var serilerView = itemView as CardView
        var seriname =itemView.seriname
        var seriimg=itemView.seriimg
        var serikatergori=itemView.kategoriname
        var seripuanyil=itemView.seripuanyil
        var seribolumsayisi=itemView.seribolumsayisi

        fun setData(oankiseri:dbseri?,position:Int){
            seriname.text=oankiseri?.name
            serikatergori.text=oankiseri?.kategoritr

            var tam = "0";
            try {
                tam = oankiseri?.tam.toString()
            } catch (ex:Exception) { }
            if (tam.equals("0"))
                tam = "?";
            else {
                val a:Int = tam.toInt();
                val b = oankiseri?.kac;
                if (b != null) {
                    if (b > a)
                        tam = tam + "+"
                };
            }
            seripuanyil.text="Yapım yılı :" + oankiseri?.yil +"   " + "Puan :" + oankiseri?.puan + "   "


            seribolumsayisi.text="Bölüm :" + oankiseri?.kac + "/" + tam

            Picasso.get()
                .load(oankiseri?.image)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(seriimg);


        }

    }


}