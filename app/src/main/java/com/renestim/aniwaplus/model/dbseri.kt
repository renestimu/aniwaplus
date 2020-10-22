package com.renestim.aniwaplus.model

class dbseri {

     var id = 0
     var name: String? = null
     var kategori: String? = null
     var kategoritr: String? = null
     var puan: String? = null
     var yil = 0
     var tam = 0
     var kac = 0
     var bitti: String? = null
     var show: String? = null
     var image: String? = null
     var imageName: String? = null
    constructor() {}
    constructor(id:Int,name:String,kategori:String,kategoritr: String,puan:String,yil:Int,tam:Int,kac:Int,bitti:String,show:String,image:String,imageName: String){
        this.id = id
        this.name = name
        this.kategori = kategori
        this.kategoritr = kategoritr
        this.yil = yil
        this.puan = puan
        this.tam = tam
        this.kac = kac
        this.bitti =bitti
        this.show=show
        this.image=image
        this.imageName=imageName

    }

}