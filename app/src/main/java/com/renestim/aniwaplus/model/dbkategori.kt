package com.renestim.aniwaplus.model

class dbkategori {
    var id = 0
    var kategori: String? = null

    constructor() {}
    constructor(id:Int,kategori:String){
        this.id=id
        this.kategori=kategori
    }

}