package com.renestim.aniwaplus.ksoaplib

import com.renestim.aniwaplus.utils.Utils
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapPrimitive
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

class Ksoap {
    companion object {
        fun getSonAnime(orgurl: String, id: Int): String {
            val Namespace = Utils.nameSpace
            val URL: String = orgurl + Utils.nameAsmx
            val method_name = "getAnimeson"

            val soap_action = Utils.nameSpace+method_name

            val request = SoapObject(Namespace, method_name)
            request.addProperty("lastid", id)
            val envvlope = SoapSerializationEnvelope(
                SoapEnvelope.VER11
            )
            envvlope.dotNet = true
            envvlope.setOutputSoapObject(request)
            return try {
                val hts = HttpTransportSE(URL, 60000)
                hts.call(soap_action, envvlope)
                val response = envvlope.response as SoapPrimitive
                response.toString()
            } catch (e: Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
                "hata"
            }


        }
        fun getSeri(orgurl: String):String {
            val Namespace =Utils.nameSpace
            val URL: String = orgurl + Utils.nameAsmx
            val method_name = "getSeries"

            val soap_action = Utils.nameSpace+method_name

            val request = SoapObject(Namespace, method_name)

            val envvlope = SoapSerializationEnvelope(
                SoapEnvelope.VER11
            )
            envvlope.dotNet = true
            envvlope.setOutputSoapObject(request)
            return try {
                val hts = HttpTransportSE(URL, 60000)
                hts.call(soap_action, envvlope)
                val response = envvlope.response as SoapPrimitive
                response.toString()
            } catch (e: Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
                "hata"
            }
        }

        fun getKatergori(orgurl: String):String{
            val Namespace =Utils.nameSpace
            val URL: String = orgurl + Utils.nameAsmx
            val method_name = "getKategories"

            val soap_action = Utils.nameSpace+method_name

            val request = SoapObject(Namespace, method_name)

            val envvlope = SoapSerializationEnvelope(
                SoapEnvelope.VER11
            )
            envvlope.dotNet = true
            envvlope.setOutputSoapObject(request)
            return try {
                val hts = HttpTransportSE(URL, 60000)
                hts.call(soap_action, envvlope)
                val response = envvlope.response as SoapPrimitive
                response.toString()
            } catch (e: Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
                "hata"
            }
        }


        fun getLogin(orgurl: String, email: String, sifre: String): String {



            val Namespace =Utils.nameSpace
            val URL: String = orgurl + Utils.nameAsmx
            val method_name = "userLogin"
            val soap_action = Utils.nameSpace+method_name
            val request = SoapObject(Namespace, method_name)

            request.addProperty("email", email)
            request.addProperty("sifre", sifre)

            val envvlope = SoapSerializationEnvelope(
                SoapEnvelope.VER11
            )
            envvlope.dotNet = true
            envvlope.setOutputSoapObject(request)
            return try {
                val hts = HttpTransportSE(URL, 60000)
                hts.call(soap_action, envvlope)
                val response = envvlope.response as SoapPrimitive
                response.toString()
            } catch (e: Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
                "hata"
            }


        }

        fun getFavori(orgurl: String, email: String, uid: String): String {



            val Namespace =Utils.nameSpace
            val URL: String = orgurl + Utils.nameAsmx
            val method_name = "getFavoriler"
            val soap_action = Utils.nameSpace+method_name
            val request = SoapObject(Namespace, method_name)

            request.addProperty("email", email)
            request.addProperty("uid", uid)

            val envvlope = SoapSerializationEnvelope(
                SoapEnvelope.VER11
            )
            envvlope.dotNet = true
            envvlope.setOutputSoapObject(request)
            return try {
                val hts = HttpTransportSE(URL, 60000)
                hts.call(soap_action, envvlope)
                val response = envvlope.response as SoapPrimitive
                response.toString()
            } catch (e: Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
                "hata"
            }


        }
        fun setBolumLikeUnlike(
            orgurl: String,
            seri: Int,
            bolum: Int,
            type: Int,
            email: String,
            uid: String
        ): String {



            val Namespace =Utils.nameSpace
            val URL: String = orgurl + Utils.nameAsmx
            val method_name = "setLikeUnlike"
            val soap_action = Utils.nameSpace+method_name
            val request = SoapObject(Namespace, method_name)
            request.addProperty("seri", seri)
            request.addProperty("bolum", bolum)
            request.addProperty("email", email)
            request.addProperty("type", type)
            request.addProperty("uid", uid)
            val envvlope = SoapSerializationEnvelope(
                SoapEnvelope.VER11
            )
            envvlope.dotNet = true
            envvlope.setOutputSoapObject(request)
            return try {
                val hts = HttpTransportSE(URL, 60000)
                hts.call(soap_action, envvlope)
                val response = envvlope.response as SoapPrimitive
                response.toString()
            } catch (e: Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
                "hata"
            }


        }
        fun setFirebaseId(orgurl: String, email: String, sifre: String, uids: String): String {



            val Namespace =Utils.nameSpace
            val URL: String = orgurl + Utils.nameAsmx
            val method_name = "setUser"
            val soap_action = Utils.nameSpace+method_name
            val request = SoapObject(Namespace, method_name)

            request.addProperty("email", email)
            request.addProperty("sifre", sifre)
            request.addProperty("uids", uids)

            val envvlope = SoapSerializationEnvelope(
                SoapEnvelope.VER11
            )
            envvlope.dotNet = true
            envvlope.setOutputSoapObject(request)
            return try {
                val hts = HttpTransportSE(URL, 60000)
                hts.call(soap_action, envvlope)
                val response = envvlope.response as SoapPrimitive
                response.toString()
            } catch (e: Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
                "hata"
            }


        }

        fun setRegister(orgurl: String, email: String, sifre: String): String {



            val Namespace =Utils.nameSpace
            val URL: String = orgurl + Utils.nameAsmx
            val method_name = "setUserAktivation"
            val soap_action = Utils.nameSpace+method_name
            val request = SoapObject(Namespace, method_name)

            request.addProperty("email", email)
            request.addProperty("sifre", sifre)

            val envvlope = SoapSerializationEnvelope(
                SoapEnvelope.VER11
            )
            envvlope.dotNet = true
            envvlope.setOutputSoapObject(request)
            return try {
                val hts = HttpTransportSE(URL, 60000)
                hts.call(soap_action, envvlope)
                val response = envvlope.response as SoapPrimitive
                response.toString()
            } catch (e: Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
                "hata"
            }


        }

        fun serviskulkont(orgurl: String, email: String?, uid: String?): String? {


            val Namespace =Utils.nameSpace
            val URL: String = orgurl + Utils.nameAsmx
            val method_name = "userKontrol"
            val soap_action = Utils.nameSpace+method_name
            val request = SoapObject(Namespace, method_name)


            request.addProperty("email", email)

            request.addProperty("uids", uid)
            val envvlope = SoapSerializationEnvelope(
                SoapEnvelope.VER11
            )
            envvlope.dotNet = true
            envvlope.setOutputSoapObject(request)
            return try {
                val hts = HttpTransportSE(URL, 60000)
                hts.call(soap_action, envvlope)
                val response = envvlope.response as SoapPrimitive
                response.toString()
            } catch (e: java.lang.Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
                "hata"
            }
        }

        fun servisgetImage(orgurl: String): String? {
            val Namespace =Utils.nameSpace
            val URL: String = orgurl + Utils.nameAsmx
            val method_name = "getImage"
            val soap_action = Utils.nameSpace+method_name
            val request = SoapObject(Namespace, method_name)

            val envvlope = SoapSerializationEnvelope(
                SoapEnvelope.VER11
            )
            envvlope.dotNet = true
            envvlope.setOutputSoapObject(request)
            return try {
                val hts = HttpTransportSE(URL, 60000)
                hts.call(soap_action, envvlope)
                val response = envvlope.response as SoapPrimitive
                response.toString()
            } catch (e: java.lang.Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
                "hata"
            }
        }
    }
}