package com.renestim.aniwaplus.model

class dbbolum {
    var id = 0
    var name: String? = null
    var seri = 0
    var bolum = 0
    var alternatif: List<dbbolums?>? = null
    var tarih: String? = null
    var img: String? = null
    var ind: Int? = 0
    var izl: Int? = 0
    var like: Int? = 0
    var dislike: Int? = 0

    constructor() {}
    constructor(id: Int,name: String?,seri: Int,bolum: Int, alternatif: List<dbbolums?>?,  tarih: String?, img: String?, ind: Int?, izl: Int?,like: Int?,dislike: Int?  ) {
        this.id = id
        this.name = name
        this.seri = seri
        this.bolum = bolum
        this.alternatif = alternatif
        this.tarih = tarih
        this.img = img
        this.ind = ind
        this.izl = izl
        this.like = like
        this.dislike = dislike
    }


}