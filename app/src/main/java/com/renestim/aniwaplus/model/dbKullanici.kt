package com.renestim.aniwaplus.model

class dbKullanici {


    var kullaniciAdi: String? = null
    var email: String? = null
    var profil_resmi: String? = null
    var seviye: String? = null
    var kullanici_id: String? = null
    var mesaj_token:String? = null

    constructor(kullaniciAdi: String, email: String, profil_resmi: String, seviye: String, kullanici_id: String) {
        this.kullaniciAdi = kullaniciAdi
        this.email = email
        this.profil_resmi = profil_resmi
        this.seviye = seviye
        this.kullanici_id = kullanici_id
    }

    constructor() {}
}