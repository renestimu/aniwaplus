package com.renestim.aniwaplus

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.renestim.aniwaplus.ksoaplib.Ksoap
import com.renestim.aniwaplus.model.dbKullanici
import com.renestim.aniwaplus.utils.Utils
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class LoginActivity : AppCompatActivity() {


    private val Tag = "loginActivity"
    lateinit var context: Context
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context = this
        auth = Firebase.auth
        btngirisyap.setOnClickListener {

            if (editemail.text!!.isNotEmpty() && editsifre.text!!.isNotEmpty()) {
                getLoginTask().execute(
                    editemail?.text.toString().trim(),
                    editsifre?.text.toString().trim()
                )
            } else {
                Toast.makeText(this, "Email veya Şifre boş olamaz", Toast.LENGTH_LONG)
                    .show()
            }


        }
        btnkayitol.setOnClickListener {
            val veriaktarma = Intent()
            veriaktarma.setClass(context, RegisterActivity::class.java)
            startActivity(veriaktarma)
        }

    }


    internal inner class getLoginTask() : AsyncTask<String, Int, String>() {

        override fun onPreExecute() {

        }

        override fun onPostExecute(result: String?) {
            Log.v(Tag, result)
            if (result != "hata") {
                try {

                    if (!result.equals("yok")) {
                        val jo = JSONObject(result)
                        val son: String = jo.getString("durum")
                        if (!son.equals("banned") && !son.equals("banned3")
                            && !son.equals("yanlis") && !son.equals("yok")
                        ) {
                            var reks = jo.getString("reklam")
                            if (reks.equals("acik")) {
                                reks = "acik"
                                if (Utils.getreklamgun(context) == 0L) {
                                    Utils.setreklamsay(context, 5)
                                } else {
                                    if (Utils.getreklamsay(context) === 0) {
                                        //  Calendar c = Calendar.getInstance();
                                        val c: Calendar = Calendar.getInstance()
                                        val current: Date = c.getTime()
                                        val bubu: String =
                                            SimpleDateFormat("yyyyMMddHHmmss").format(current)
                                        val cevir = bubu.toLong()
                                        if (cevir < Utils.getreklamgun(context)) {
                                            reks = "kapali"
                                        }
                                    }
                                }
                            }

                            Utils.setreklams(context, reks);
                            Utils.setea(context, jo.getString("email"));
                            Utils.setusername(context, jo.getString("username"));
                            Utils.setuserimage(context, jo.getString("image"));
                            Utils.setWid(context, jo.getInt("wid"));


                            Toast.makeText(
                                context,
                                "Hoşgeldiniz.",
                                Toast.LENGTH_LONG
                            ).show();


                                firebaseLogin()


                            val intent = Intent()
                            intent.putExtra("kayit", "email")
                            setResult(12, intent)


                        }else if(son.equals("yanlis")){

                            Toast.makeText(
                                context,
                                "Email ve Şifreniz yanlıştır.",
                                Toast.LENGTH_LONG
                            ).show();
                        }else if(son.equals("banned")){

                            Toast.makeText(
                                context,
                                "Hesabınız banlanmıştır.",
                                Toast.LENGTH_LONG
                            ).show();
                        }else if(son.equals("yok")){

                            Toast.makeText(
                                context,
                                "Böyle bir hesap bulunamadı.",
                                Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                } catch (ex: Exception) {
                    Log.e(Tag, ex.localizedMessage)
                }
            }
        }

        override fun doInBackground(vararg params: String?): String {
            return Ksoap.getLogin(
                orgurl = Utils.getorgurl(context = context).toString(),
                email = params[0].toString(),
                sifre = params[1].toString()
            )
        }
    }

    private fun firebaseLogin() {
        auth.signInWithEmailAndPassword(
            editemail?.text.toString().trim(),
            editsifre?.text.toString().trim()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Tag, "signInWithEmail:success")
                    val user = auth.currentUser
                    Utils.setUid(context, user?.uid.toString())
                    sendUserId(
                        editemail?.text.toString(),
                        editsifre?.text.toString(),
                        user?.uid.toString()
                    )
                    kaydetDatabaseUser()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Tag, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                    firebaseCreateUser();
                }


            }
    }



    private fun firebaseCreateUser() {
        auth.createUserWithEmailAndPassword(
            editemail?.text.toString().trim(),
            editsifre?.text.toString().trim()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Tag, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Utils.setUid(context, user?.uid.toString())
                    sendUserId(
                        editemail?.text.toString(),
                        editsifre?.text.toString(),
                        user?.uid.toString()
                    )
                    kaydetDatabaseUser()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Tag, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }

                // ...
            }
    }
    private fun sendUserId(email: String, sifre: String, uid: String) {

        setFirebase().execute(email, sifre, uid)
    }

    internal inner class setFirebase() : AsyncTask<String, Int, String>() {

        override fun onPreExecute() {

        }

        override fun onPostExecute(result: String?) {
            if (result != "hata") {
                try {

                    Toast.makeText(context, result, Toast.LENGTH_LONG).show();


                } catch (ex: Exception) {
                    Log.e(Tag, ex.localizedMessage)
                }
            }
        }

        override fun doInBackground(vararg params: String?): String {
            return Ksoap.setFirebaseId(
                orgurl = Utils.getorgurl(context = context).toString(),
                params[0].toString(),
                params[1].toString(),
                params[2].toString()
            )
        }
    }

    private fun kaydetDatabaseUser(){

        var veritabaninaEklenecekKullanici= dbKullanici()
        veritabaninaEklenecekKullanici.email= editemail.text.toString()
        veritabaninaEklenecekKullanici.kullanici_id=auth.currentUser?.uid
        veritabaninaEklenecekKullanici.profil_resmi=    Utils.getuserimage(context);
        veritabaninaEklenecekKullanici.kullaniciAdi= Utils.getusername(context);
        veritabaninaEklenecekKullanici.seviye="1"

        auth.currentUser?.uid?.let {
            FirebaseDatabase.getInstance().reference
                .child("kullanici")
                .child(it)
                .setValue(veritabaninaEklenecekKullanici).addOnCompleteListener { task->

                    if(task.isSuccessful){
                        Toast.makeText(
                            this,
                            "Üye kaydedildi:" + FirebaseAuth.getInstance().currentUser?.uid,
                            Toast.LENGTH_SHORT
                        ).show()


                    }

                }
        }
        val intent = Intent()
        intent.putExtra("kayit", "email")

        setResult(12, intent);
        this.finish();
    }

}