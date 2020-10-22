package com.renestim.aniwaplus


import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.renestim.aniwaplus.ksoaplib.Ksoap
import com.renestim.aniwaplus.model.*
import com.renestim.aniwaplus.utils.Utils
import kotlinx.android.synthetic.main.activity_acilis.*
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AcilisActivity : AppCompatActivity() {

    var seriList: ArrayList<dbseri>? = null
    private val Tag = "AcilisActivity"
    lateinit var context: Context
    var loginc = "yok"
    var loginuids = "yok"
    var orgurl = "http://www.renessoft.org/"
    var alturl = "http://www.renestim.net/"
    var izinkontrol = false
    var ilkmi = false


    val db by lazy { dbHelper(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acilis)
        context = this


        val asagidanGelenButon = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        val yukaridanGelenBalon = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val ortadanGelenBalon = AnimationUtils.loadAnimation(this, R.anim.middle_animation)

        realLogo.animation = yukaridanGelenBalon
        appLogo.animation = ortadanGelenBalon
        yuklemeBari.animation = asagidanGelenButon
        yuklemealani.animation = asagidanGelenButon

        loginc = Utils.getea(this).toString();
        loginuids = Utils.getUid(this).toString();


        seriList = ArrayList<dbseri>()

       mainTask().execute()
        //getFavori().execute()
    }

    internal inner class getSeriTask() : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            yuklemealani.text = "Seriler indiriliyor..."
        }

        override fun onPostExecute(result: String?) {
            if (result != "hata") {
                try {
                    setSeriTask().execute(result);


                } catch (ex: Exception) {
                    Log.e(Tag, ex.localizedMessage)
                }
            }
        }

        override fun doInBackground(vararg params: String?): String {
            return Ksoap.getSeri(orgurl = Utils.getorgurl(context = context).toString())
        }
    }

    internal inner class setSeriTask() : AsyncTask<String, Int, String>() {

        override fun onPreExecute() {
            yuklemealani.text = "Seriler Yükleniyor..."
        }

        override fun onPostExecute(result: String?) {
            yuklemealani.text = "Seriler Yüklendi."
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            getKatergoriTask().execute();
        }

        override fun onProgressUpdate(vararg values: Int?) {
            values[0]?.let { yuklemeBari.setProgress(it) }
            super.onProgressUpdate(*values)
        }

        override fun doInBackground(vararg params: String?): String {
            try {
                val jsonarray = JSONArray(params[0])
                for (i in 0..jsonarray.length() - 1) {
                    val jsonobject = jsonarray.getJSONObject(i)
                    val ekle = dbseri()
                    ekle.id = jsonobject.getInt("id")
                    ekle.name = jsonobject.getString("name")
                    ekle.kategori = jsonobject.getString("kategori")
                    ekle.kategoritr = jsonobject.getString("kategoritr")
                    ekle.yil = jsonobject.getInt("yil")
                    ekle.tam = jsonobject.getInt("tam")
                    ekle.kac = jsonobject.getInt("kac")
                    ekle.puan = jsonobject.getString("puan")
                    ekle.bitti = jsonobject.getString("bitti")
                    ekle.show = jsonobject.getString("show")
                    ekle.imageName = jsonobject.getString("imgname")
                    ekle.image = jsonobject.getString("img")

                    var ks = db.kontseriler(ekle.id)
                    if (ks == null) {
                        db.close()
                        db.insertseri(ekle)
                    } else {
                        db.close()
                        db.updateseri(ekle)
                    }
                    val percentage = (i.toFloat() / jsonarray.length().toFloat() * 100).toInt()
                    publishProgress(percentage)
                }

            } catch (ex: Exception) {
                Log.e(Tag, ex.localizedMessage)
            }
            return "";
        }
    }


    internal inner class getKatergoriTask() : AsyncTask<String, Int, String>() {

        override fun onPreExecute() {
            yuklemealani.text = "Kategoriler yükleniyor..."
            yuklemeBari.setProgress(0)
        }

        override fun onPostExecute(result: String?) {
            if (result != "hata") {
                try {
                    val jsonarray = JSONArray(result)
                    for (i in 0..jsonarray.length() - 1) {
                        val jsonobject = jsonarray.getJSONObject(i)
                        val ekle = dbkategori()
                        ekle.id = jsonobject.getInt("id")

                        ekle.kategori = jsonobject.getString("kategori")


                        val ks = db.kontKategori(ekle.id)
                        if (ks == null) {
                            db.close()
                            db.insertKategori(ekle)
                        } else {
                            db.close()
                            db.updateKategori(ekle)
                        }
                        val percentage = (i.toFloat() / jsonarray.length().toFloat() * 100).toInt()
                        yuklemeBari.setProgress(percentage)
                    }
                    yuklemeBari.setProgress(100);
                    if (loginc.equals("yok")) {
                        Utils.setfirst(context, false);
                        loginac();

                    } else {
                        Utils.setfirst(context, false);
                        gonderMain();
                    }
                } catch (ex: Exception) {
                    Log.e(Tag, ex.localizedMessage)
                }
            }
        }

        override fun onProgressUpdate(vararg values: Int?) {

            super.onProgressUpdate(*values)
        }

        override fun doInBackground(vararg params: String?): String {
            return Ksoap.getKatergori(orgurl = Utils.getorgurl(context = context).toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 12) {
            if (!Utils.getea(this).equals("yok")) {

                getFavori().execute()
            } else {
                loginac()
            }
        }
    }

    internal inner class getFavori() : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            yuklemealani.text = "Favoriler indiriliyor..."
        }

        override fun onPostExecute(result: String?) {
            if (result != "hata") {
                try {
                    // setSeriTask().execute(result);
                    setFavoriTask().execute(result)

                } catch (ex: Exception) {
                    Log.e(Tag, ex.localizedMessage)
                }
            }
        }

        override fun doInBackground(vararg params: String?): String {
            return Ksoap.getFavori(
                orgurl = Utils.getorgurl(context = context).toString(),
                Utils.getEA(context).toString(), Utils.getUid(context).toString()
            )
        }
    }


    internal inner class setFavoriTask() : AsyncTask<String, Int, String>() {

        override fun onPreExecute() {
            yuklemealani.text = "Favoriler yükleniyor..."
            yuklemeBari.setProgress(0)
        }

        override fun onPostExecute(result: String?) {
            yuklemealani.text="Favoriler Yüklendi...";

            yuklemeBari.setProgress(100);

            gonderMain();
        }

        override fun onProgressUpdate(vararg values: Int?) {

            super.onProgressUpdate(*values)
        }

        override fun doInBackground(vararg params: String?): String {

            try {
                val jsonarray = JSONArray(params[0])
                for (i in 0 until jsonarray.length()) {
                    val sid = jsonarray.getInt(i)
                    val bol: dbfavori? = db.kontFavori(sid)
                    if (bol == null) {
                        db.close()
                        db.insertFavori(
                            dbfavori(
                                1, sid
                            )
                        )
                        db.close()
                    }
                    val percentage = (i.toFloat() / jsonarray.length().toFloat() * 100).toInt()
                    yuklemeBari.setProgress(percentage)
                }


                yuklemeBari.setProgress(100);
                gonderMain();

            } catch (ex: Exception) {
                Log.e(Tag, ex.localizedMessage)
            }
            return ""
        }
    }


    internal inner class mainTask() : AsyncTask<String, Int, String>() {

        override fun onPreExecute() {

        }

        override fun onPostExecute(result: String?) {
            if (orgurl.equals(alturl))
                Toast.makeText(
                    context, "İkinci sunucudasınız",
                    Toast.LENGTH_LONG
                ).show();
            else
                Toast.makeText(
                    context, "Birinci sunucudasınız",
                    Toast.LENGTH_LONG
                ).show();

            if (Utils.getfirst(context)) {
                getSeriTask().execute()
            } else if (loginc != "yok") {
                kullaniciKontrol().execute(loginc, loginuids)

            } else {
                loginac()
            }

        }

        override fun doInBackground(vararg params: String?): String {
            var title = ""

            try {

                val doc: Document = Jsoup.connect(orgurl + "control.txt").timeout(3000).get()
                val el = doc.select("*").first().text().toString()

                title = orgurl
            } catch (ex: Exception) {
                try {
                    Log.v("gelen url2", alturl)
                    val doc1 = Jsoup.connect(alturl + "control.txt").timeout(3000).get()
                    val el1 = doc1.select("*").first().text().toString()
                    //Log.v("gelen url1", el1);
                    orgurl = alturl
                    title = orgurl
                } catch (e1: Exception) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace()
                    //Log.v("gelen url2", "hata");
                    title = orgurl
                }
            }



            return title
        }
    }


    internal inner class kullaniciKontrol() : AsyncTask<String, String, String>() {
        var sonuc = ""
        override fun doInBackground(vararg p0: String?): String {
            sonuc =
                Ksoap.serviskulkont(
                    Utils.getorgurl(context = context).toString(),
                    p0[0].toString(),
                    p0[1].toString()
                ).toString();
            return "";
        }

        override fun onPostExecute(result: String?) {
            if (!sonuc.trim().equals("hata") && !sonuc.trim().equals("yok")) {
                try {
                    val jo = JSONObject(sonuc)
                    val son: String = jo.getString("durum")
                    if (!son.equals("banned") && !son.equals("banned3")
                        && !son.equals("yalnis") && !son.equals("sifreyok")
                    ) {
                        var login = Utils.getea(context);
                        if (login.equals("yok")) {
                            Utils.setea(context, jo.getString("email"));
                        }
                        var reks = jo.getString("reklam");

                        if(reks.equals("acik")) {
                            reks = "acik";
                            if (Utils.getreklamgun(context).toInt() == 0) {
                                Utils.setreklamsay(context, 5);

                            } else {
                                if (Utils.getreklamsay(context) == 0) {
                                    //  Calendar c = Calendar.getInstance();
                                    var c = Calendar.getInstance();

                                    var current = c.getTime();
                                    var bubu =  SimpleDateFormat("yyyyMMddHHmmss").format(current);
                                    var cevir = (bubu).toLong();
                                    if(cevir<Utils.getreklamgun(context) )
                                    {
                                        reks="kapali";
                                    }else
                                        Utils.setreklamsay(context, 5);

                                }
                            }
                        }

                        Utils.setreklams(context, reks);
                        Utils.setusername(context, jo.getString("username"));
                        Utils.setuserimage(context, jo.getString("image"));
                        Utils.setWid(context, jo.getInt("wid"));


                    }

                } catch (ex: Exception) {

                }
                kaydetDatabaseUser(Utils.getUid(context).toString())
                gonderMain();
            }

        }
    }

    private fun kaydetDatabaseUser(uid:String){
        var referans = FirebaseDatabase.getInstance().reference

        referans .child("kullanici").child(uid).child("kullaniciAdi").setValue(Utils.getEA(context))
        referans .child("kullanici").child(uid).child("profil_resmi").setValue(Utils.getuserimage(context))


    }
    fun gonderMain() {
        val myIntent = Intent(this, MainActivity::class.java)
        startActivity(myIntent)
        finish()
    }

    fun loginac() {
        val veriaktarma = Intent()
        veriaktarma.setClass(context, LoginActivity::class.java)
        val pairs = arrayOf(
            Pair<View, String>(realLogo, "logo_image")
        )

        val options: ActivityOptions =
            ActivityOptions.makeSceneTransitionAnimation(
                this@AcilisActivity,
                realLogo,
                "logo_image"
            )
        startActivityForResult(veriaktarma, 12, options.toBundle())
    }


}


