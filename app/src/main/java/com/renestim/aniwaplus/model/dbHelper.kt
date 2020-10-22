package com.renestim.aniwaplus.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class dbHelper(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,
    DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "renestimdb"
        private const val TABLE_SERILER = "seriler"
        private const val TABLE_KATEGORI = "kategoriler"
     //   private const val TABLE_IMAGE = "image"
        private const val TABLE_FAVORI = "favori"
        private const val TABLE_IZLENEN = "izlenen"
        private const val TABLE_KALINAN = "kalinan"
        private const val TABLE_IZLEDIM = "izledim"
        private const val TABLE_INDIRILEN = "indirilen"
        private const val TABLE_FAVORI_ALTERNATIF = "favorialternatif"

    }
    private val CREATE_TABLE_SERILER = "CREATE TABLE IF NOT EXISTS " + TABLE_SERILER+
            " ( id INTEGER PRIMARY KEY,name TEXT, katagori TEXT,katagoritr TEXT,"+
            "yil INTEGER,puan TEXT,tam INTEGER,kac INTEGER,bit TEXT,show TEXT, imgurl TEXT,imgname TEXT)"

     private  val  CREATE_TABLE_KATEGORI = "CREATE TABLE IF NOT EXISTS "+ TABLE_KATEGORI   +
             " ( id INTEGER PRIMARY KEY,katagori TEXT)";

//  private val CREATE_TABLE_IMAGE = "CREATE TABLE IF NOT EXISTS "+ TABLE_IMAGE+
//          " ( id INTEGER PRIMARY KEY,seri INTEGER, imgurl TEXT,imgname TEXT)";

    private val CREATE_TABLE_FAVORI = "CREATE TABLE IF NOT EXISTS " + TABLE_FAVORI  +
            " ( id INTEGER PRIMARY KEY AUTOINCREMENT,seri INTEGER )";

    private val CREATE_TABLE_IZLENEN = "CREATE TABLE IF NOT EXISTS "+ TABLE_IZLENEN+
            " ( id INTEGER PRIMARY KEY AUTOINCREMENT,seri INTEGER,bolum INTEGER,bolumn TEXT)";

    private val CREATE_TABLE_KALINAN = "CREATE TABLE IF NOT EXISTS "+ TABLE_KALINAN +
            " ( id INTEGER PRIMARY KEY AUTOINCREMENT,seri INTEGER,bolum INTEGER,bolumn TEXT,seek INTEGER)";

    private val CREATE_TABLE_IZLEDIM = "CREATE TABLE IF NOT EXISTS "+ TABLE_IZLEDIM+
            " ( seri INTEGER PRIMARY KEY ,durum INTEGER )";

    private val CREATE_TABLE_INDIRILEN = "CREATE TABLE IF NOT EXISTS "+ TABLE_INDIRILEN +
            " (  id INTEGER PRIMARY KEY,aid TEXT,  name TEXT,url TEXT,image TEXT, session TEXT)";

    private val CREATE_TABLE_FAVORI_ALTERNATIF= "CREATE TABLE IF NOT EXISTS "+ TABLE_FAVORI_ALTERNATIF+
            " (  id INTEGER PRIMARY KEY,href TEXT,  value TEXT,value2 TEXT,image TEXT,alternatif TEXT )";

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_SERILER);
        db?.execSQL(CREATE_TABLE_KATEGORI);
        //db?.execSQL(CREATE_TABLE_IMAGE);
        db?.execSQL(CREATE_TABLE_FAVORI);
        db?.execSQL(CREATE_TABLE_IZLENEN);
        db?.execSQL(CREATE_TABLE_KALINAN);
        db?.execSQL(CREATE_TABLE_IZLEDIM);
        db?.execSQL(CREATE_TABLE_INDIRILEN);
        db?.execSQL(CREATE_TABLE_FAVORI_ALTERNATIF);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_SERILER);
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORI);
      //  db?.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_IZLENEN);
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_KALINAN);
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_KATEGORI);
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_IZLEDIM);
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_INDIRILEN);
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORI_ALTERNATIF);
        onCreate(db);
    }


    fun insertseri(seriler: dbseri) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("id", seriler.id)
        values.put("name", seriler.name)
        values.put("katagori", seriler.kategori)
        values.put("katagoritr", seriler.kategoritr)
        values.put("yil", seriler.yil)
        values.put("puan", seriler.puan)
        values.put("tam", seriler.tam)
        values.put("kac", seriler.kac)
        values.put("bit", seriler.bitti)
        values.put("show", seriler.show)
        values.put("imgurl", seriler.image)
        values.put("imgname", seriler.imageName)
        db.insert(TABLE_SERILER, null, values)
        db.close()
    }
    fun  kontseriler(id:Int):dbseri? {

        val selectQuery = "SELECT  * FROM " + TABLE_SERILER + " WHERE id=" + id;
        val db = this.getReadableDatabase();
        val c = db?.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        var td:dbseri? = null;
        if (c?.moveToFirst()!!) {
            do {
                td =dbseri();
                td.id =c.getInt((c.getColumnIndex("id")));
                td.name= ((c.getString(c.getColumnIndex("name"))));
                td.kategoritr=(c.getString(c.getColumnIndex("katagoritr")));
                td.puan=(c.getString(c.getColumnIndex("puan")));
                td.yil=(c.getInt(c.getColumnIndex("yil")));
                td.kac=(c.getInt(c.getColumnIndex("kac")));
                td.tam=(c.getInt(c.getColumnIndex("tam")));
                td.bitti=(c.getString(c.getColumnIndex("bit")));
                // adding to todo list

            } while (c.moveToNext());
        }
        if (c.getCount() == 0)
            td = null;
        c.close();
        db.close()
        return td;
    }
    fun updateseri( seriler:dbseri):Int {
      val db = this.writableDatabase
      val values = ContentValues()
        // values.put("id", seriler.getid());

      values.put("name", seriler.name)
      values.put("katagori", seriler.kategori)
      values.put("katagoritr", seriler.kategoritr)
      values.put("yil", seriler.yil)
      values.put("puan", seriler.puan)
      values.put("tam", seriler.tam)
      values.put("kac", seriler.kac)
      values.put("bit", seriler.bitti)
      values.put("show", seriler.show)
      values.put("imgurl", seriler.image)
      values.put("imgname", seriler.imageName)
        val i = db.update(TABLE_SERILER, values, " id=?",
                arrayOf((seriler.id).toString()));
        db.close();
        return i;
    }
    fun getAllseri():ArrayList<dbseri>{
        val userList = ArrayList<dbseri>()
        val db = this.writableDatabase
        val selectQuery = "SELECT  * FROM " + TABLE_SERILER +" ORDER BY " + TABLE_SERILER + ".name ASC";
        val c = db?.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        var td:dbseri? = null;
        if (c?.moveToFirst()!!) {
            do {
                td =dbseri();
                td.id =c.getInt((c.getColumnIndex("id")));
                td.name= ((c.getString(c.getColumnIndex("name"))));
                td.kategoritr=(c.getString(c.getColumnIndex("katagoritr")));
                td.puan=(c.getString(c.getColumnIndex("puan")));
                td.yil=(c.getInt(c.getColumnIndex("yil")));
                td.kac=(c.getInt(c.getColumnIndex("kac")));
                td.tam=(c.getInt(c.getColumnIndex("tam")));
                td.bitti=(c.getString(c.getColumnIndex("bit")));
                td.image=(c.getString(c.getColumnIndex("imgurl")));
                td.imageName=(c.getString(c.getColumnIndex("imgname")));
                // adding to todo list
                userList.add(td)
            } while (c.moveToNext());
        }
        if (c.getCount() == 0)
            td = null;
        c.close();
        db.close()

        return userList
    }



    fun insertKategori(seriler: dbkategori) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("id", seriler.id)
        values.put("katagori", seriler.kategori)

        db.insert(TABLE_KATEGORI, null, values)
        db.close()
    }
    fun  kontKategori(id:Int):dbkategori? {

        val selectQuery = "SELECT  * FROM " + TABLE_KATEGORI + " WHERE id=" + id;
        val db = this.getReadableDatabase();
        val c = db?.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        var td:dbkategori? = null;
        if (c?.moveToFirst()!!) {
            do {
                td =dbkategori();
                td.id =c.getInt((c.getColumnIndex("id")));

                td.kategori=(c.getString(c.getColumnIndex("katagori")));

                // adding to todo list

            } while (c.moveToNext());
        }
        if (c.getCount() == 0)
            td = null;
        c.close();
        db.close()
        return td;
    }
    fun updateKategori( seriler:dbkategori):Int {
        val db = this.writableDatabase
        val values = ContentValues()
        // values.put("id", seriler.getid());


        values.put("katagori", seriler.kategori)

        val i = db.update(TABLE_KATEGORI, values, " id=?",
            arrayOf((seriler.id).toString()));
        db.close();
        return i;
    }


    fun insertFavori(seriler: dbfavori) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put("seri", seriler.seri)

        db.insert(TABLE_FAVORI, null, values)
        db.close()
    }
    fun  kontFavori(id:Int):dbfavori? {

        val selectQuery = "SELECT  * FROM " + TABLE_FAVORI + " WHERE seri=" + id ;
        val db = this.getReadableDatabase();
        val c = db?.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        var td:dbfavori? = null;
        if (c?.moveToFirst()!!) {
            do {
                td = dbfavori();
                td.id =c.getInt((c.getColumnIndex("id")));

                td.seri=(c.getInt(c.getColumnIndex("seri")));

                // adding to todo list

            } while (c.moveToNext());
        }
        if (c.getCount() == 0)
            td = null;
        c.close();
        db.close()
        return td;
    }
    fun getAllseriFavori():ArrayList<dbseri>{
        val userList = ArrayList<dbseri>()
        val db = this.writableDatabase
        val selectQuery = "SELECT  " + TABLE_SERILER + ".* FROM " + TABLE_FAVORI + " INNER JOIN " + TABLE_SERILER + " ON " + TABLE_FAVORI + ".seri=" + TABLE_SERILER + ".id ORDER BY " + TABLE_SERILER + ".name ASC";
        val c = db?.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        var td:dbseri? = null;
        if (c?.moveToFirst()!!) {
            do {
                td =dbseri();
                td.id =c.getInt((c.getColumnIndex("id")));
                td.name= ((c.getString(c.getColumnIndex("name"))));
                td.kategoritr=(c.getString(c.getColumnIndex("katagoritr")));
                td.puan=(c.getString(c.getColumnIndex("puan")));
                td.yil=(c.getInt(c.getColumnIndex("yil")));
                td.kac=(c.getInt(c.getColumnIndex("kac")));
                td.tam=(c.getInt(c.getColumnIndex("tam")));
                td.bitti=(c.getString(c.getColumnIndex("bit")));
                td.image=(c.getString(c.getColumnIndex("imgurl")));
                td.imageName=(c.getString(c.getColumnIndex("imgname")));
                // adding to todo list
                userList.add(td)
            } while (c.moveToNext());
        }
        if (c.getCount() == 0)
            td = null;
        c.close();
        db.close()

        return userList
    }


    fun deletefavori(id:Int) {
        val db = this.writableDatabase;
        db.delete(TABLE_FAVORI, "seri = "+id, null);
        db.close();
    }

}