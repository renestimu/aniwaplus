package com.renestim.aniwaplus

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.renestim.aniwaplus.ksoaplib.Ksoap
import com.renestim.aniwaplus.utils.Utils
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.btnkayitol
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private val Tag = "RegisterActivity"
    var email:String=""
    var sifre:String=""
    var sifrete:String=""
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        context = this
        btnkayitol.setOnClickListener {

            if(etsifre.text.toString().trim().length>=6) {
                if (etemail.text!!.isNotEmpty() && etsifre.text!!.isNotEmpty() && etsifretekrar.text!!.isNotEmpty()) {
                    email = etemail.text.toString().trim().toLowerCase()
                    sifre = etsifre.text.toString().trim()
                    sifrete = etsifretekrar.text.toString().trim()



                    if (sifre.equals(sifrete)) {
                        Toast.makeText(this, "basarili", Toast.LENGTH_LONG)
                            .show()
                        setRegisterTask().execute(email,sifre)
                    } else {
                        Toast.makeText(
                            this,
                            "Şifre veya Şifre Tekrar aynı değil",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }


                } else {
                    Toast.makeText(this, "Email veya Şifre boş olamaz", Toast.LENGTH_LONG)
                        .show()
                }
            }else{
                Toast.makeText(this, "Şifre 6 karakterden uzun olmalı", Toast.LENGTH_LONG)
                    .show()
            }

        }

    }

    internal inner class setRegisterTask() : AsyncTask<String, Int, String>() {

        override fun onPreExecute() {

        }

        override fun onPostExecute(result: String?) {
            if (result != "hata") {
                try {

                    if (!result.equals("Gönderilmedi") && !result.equals("iletisim")) {



                        Toast.makeText(
                           context,
                            "Mail Adresinize Başarı aktivasyon maili gönderilmiştir. Lütfen gelen kutunuzu yada spam maillerinizi kontrol ediniz",
                            Toast.LENGTH_LONG).show();
                        finish();


                    }else if (result.equals("iletisim"))
                        Toast.makeText(
                            context,
                            "Mail Adresinizi yalnis girdiniz. Sistemdeki Mail adresiniz değildir.Eğer kayıtlı değilseniz ve böyle bir hata alıyorsanız info@renestim.net adresine mail atınız..",
                            Toast.LENGTH_LONG).show();
                    else if (result.equals("Gönderilmedi")) {

                        Toast.makeText(
                            context,
                            "Mail Adresinize mail gönderilemedi.Lütfen kullandığınız mail adresinizi gönderin",
                            Toast.LENGTH_LONG).show();
                    }

                    else if (result.equals("yok"))
                        Toast.makeText(
                            context,
                            "Sistemde bir hata oluştu.",
                            Toast.LENGTH_LONG).show();

                } catch (ex: Exception) {
                    Log.e(Tag, ex.localizedMessage)
                }
            }
        }

        override fun doInBackground(vararg params: String?): String {
            return Ksoap.setRegister(
                orgurl = Utils.getorgurl(context = context).toString(),
                params[0].toString(),
                params[1].toString()
            )
        }
    }

}