package com.renestim.aniwaplus.adapter

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.renestim.aniwaplus.R
import com.renestim.aniwaplus.VideoActivity
import com.renestim.aniwaplus.ksoaplib.Ksoap
import com.renestim.aniwaplus.model.dbbolum
import com.renestim.aniwaplus.model.dbbolums
import com.renestim.aniwaplus.utils.TimeAgo
import com.renestim.aniwaplus.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.bolumler_layout.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class bolumlerRecyclerviewAdapter(context: Context, bolumler: ArrayList<dbbolum>?) :
    RecyclerView.Adapter<bolumlerRecyclerviewAdapter.bolumlerViewHolder>() {

     var myContext = context
    var myBolumler = bolumler

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bolumlerViewHolder {
        var inflater = LayoutInflater.from(myContext)
        var view = inflater.inflate(R.layout.bolumler_layout, parent, false)

        return bolumlerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myBolumler!!.size
    }

    override fun onBindViewHolder(holder: bolumlerViewHolder, position: Int) {
        var oankiBolum=myBolumler?.get(position)
        holder.setData(oankiBolum,position)

    }

    inner class bolumlerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var bolumview = itemView as CardView
        var bolumResim=bolumview.imgGorsel
        var baslik=bolumview.tvBaslik
        var begenmeText=bolumview.begenmeText
        var begenmeImg=bolumview.begenmeImg
        var indizleText=bolumview.indizleText
        var yorumYaz=bolumview.yorumYaz
        var begenmemeText=bolumview.begenmemeText
        var begenmemeImg=bolumview.begenmemeImg
        var tarihText=bolumview.tvTarih

        fun setData(oankiBolum:dbbolum?,position:Int){
            baslik.text=oankiBolum?.name
            begenmeText.text="Beğenme:"+oankiBolum?.like
            begenmemeText.text="Beğenmeme:"+oankiBolum?.dislike
            indizleText.text="ind-izle:"+oankiBolum?.ind+"-"+oankiBolum?.izl
            begenmeImg.setTag(bolumview.begenmeImg)
            begenmemeImg.setTag(bolumview.begenmemeImg)


            var format = SimpleDateFormat("dd.MM.yyyy HH:mm:ss",Locale("tr"));
            val time: Long = format.parse(oankiBolum?.tarih).getTime()
            tarihText.text=TimeAgo.getTimeAgo(time)

            Picasso.get().load(oankiBolum?.img).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(bolumResim)

            begenmeImg.setOnClickListener {
                if(begenmeImg.getTag()==R.drawable.ic_like){
                    begenmeImg.setImageResource(R.drawable.ic_like_active);
                    begenmeImg.setTag(R.drawable.ic_like_active);

                    if(begenmemeImg.getTag()==R.drawable.ic_unlike_active){
                        begenmemeText.text="Beğenme:"+(oankiBolum?.dislike)
                    }

                    begenmemeImg.setImageResource(R.drawable.ic_unlike);
                    begenmemeImg.setTag(R.drawable.ic_unlike);

                    begenmeText.text="Beğenme:"+(oankiBolum?.like?.plus(1))
                     setLikeUnlike().execute(oankiBolum?.seri.toString(), oankiBolum?.bolum.toString(),"1")

                }else{
                    begenmeImg.setImageResource(R.drawable.ic_like);
                    begenmeImg.setTag(R.drawable.ic_like)
                    begenmeText.text="Beğenme:"+(oankiBolum?.like)
                    setLikeUnlike().execute(oankiBolum?.seri.toString(), oankiBolum?.bolum.toString(),"1")
                }
            }

            begenmemeImg.setOnClickListener {
                if(begenmemeImg.getTag()==R.drawable.ic_unlike){
                    if(begenmeImg.getTag()==R.drawable.ic_like_active){
                        begenmeText.text="Beğenme:"+(oankiBolum?.like)
                    }


                    begenmeImg.setImageResource(R.drawable.ic_like);
                    begenmeImg.setTag(R.drawable.ic_like);
                    begenmemeImg.setImageResource(R.drawable.ic_unlike_active);
                    begenmemeImg.setTag(R.drawable.ic_unlike_active);
                    begenmemeText.text="Beğenme:"+(oankiBolum?.dislike?.plus(1))
                    setLikeUnlike().execute(oankiBolum?.seri.toString(), oankiBolum?.bolum.toString(),"2")
                }else{
                    begenmemeImg.setImageResource(R.drawable.ic_unlike);
                    begenmemeImg.setTag(R.drawable.ic_unlike)
                    begenmemeText.text="Beğenme:"+(oankiBolum?.dislike)
                    setLikeUnlike().execute(oankiBolum?.seri.toString(), oankiBolum?.bolum.toString(),"2")
                }
            }

            bolumview.setOnClickListener {
                val bol: ArrayList<dbbolums> = oankiBolum?.alternatif as ArrayList<dbbolums>
                //  Toast.makeText(context, movie.getname() + " is selected!", Toast.LENGTH_SHORT).show();
                val listItemsalter: ArrayList<String> =  ArrayList()
                for (i in bol.indices) {
                    if (listItemsalter.indexOf(bol[i].fansub) == -1)
                        listItemsalter.add(bol[i].fansub.toString())
                }
                if (listItemsalter.size > 1) {
                    val items2 = listItemsalter.toTypedArray<CharSequence>()

                    AlertDialog.Builder(myContext)
                        .setSingleChoiceItems(items2,-1 ) { dialogInterface, p ->
                            dialogInterface.dismiss()
                            val sece = listItemsalter[p]
                            alertfansub(sece, bol, oankiBolum)
                        }
                        .show()
                }else{
                    if (listItemsalter.size != 0) {
                        val sece = listItemsalter[0]
                        alertfansub(sece, bol, oankiBolum)
                    } else {
                        alertfansub("Varsayılan", bol, oankiBolum)
                    }
                }
            }

        }

    }

    inner  class setLikeUnlike() : AsyncTask<String, String, String>() {



        override fun onPostExecute(result: String?) {
            if (result != "hata") {

            }
        }

        override fun doInBackground(vararg params: String?): String {
            return Ksoap.setBolumLikeUnlike( Utils.getorgurl(myContext ).toString(),   params[0].toString().toInt(),params[1].toString().toInt(),params[2].toString().toInt(),Utils.getEA(myContext).toString(),Utils.getUid(myContext).toString())
        }
    }

     fun alertfansub(sece:String, bol: ArrayList<dbbolums>, movie:dbbolum){
        val listItems1: ArrayList<String> = ArrayList()
         for (i in 0 until bol.size) {
             if (bol[i].fansub.equals(sece)) {
                 if (!bol[i].fansub.equals("Varsayılan") && bol[i].tip?.toUpperCase().equals("SUNUCU"))
                     listItems1.add(bol[i].fansub.toString())
                 else
                     listItems1.add(bol[i].tip?.toUpperCase().toString())
             }
         }
         val items1 = listItems1.toTypedArray<CharSequence>()
         AlertDialog.Builder(myContext)
             .setSingleChoiceItems(items1,-1 ) { dialogInterface, p ->
                 dialogInterface.dismiss()
                 val secee = listItems1.get(p)
                 var sectim = 0
                 for (i in 0 until bol.size) {
                     if (bol[i].fansub.equals(sece)) {
                         if (bol[i].tip.toString().toUpperCase().equals(secee))
                             sectim=i

                     }
                 }
                 val infoBundle = Bundle()
                 infoBundle.putString("seriid",movie.seri.toString())
                 infoBundle.putString("bolums", movie.bolum.toString());
                 infoBundle.putString("name", movie.name);
                infoBundle.putInt("itemid", movie.id);
                 infoBundle.putString("animeurl", bol.get(sectim).url);
                 infoBundle.putString("kalite", "0");

                 infoBundle.putString("tip", secee);
                 val veriaktarma = Intent()
                 veriaktarma.setClass(myContext, VideoActivity::class.java)
                 veriaktarma.putExtras(infoBundle)
                 myContext.startActivity(veriaktarma)

             }
             .show()
    }
}