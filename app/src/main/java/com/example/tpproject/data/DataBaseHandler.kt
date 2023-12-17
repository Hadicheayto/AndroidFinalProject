package com.example.tpproject.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


val DATABASE_NAME = "MyDB"
val TABLE_NAME = "property"
val COL_ID = "id"
val COL_USERID = "user_id"
val COL_Price = "price"
val COL_Location = "location"
val COL_TITLE = "title"
val COL_DESCRIPTION = "description"
val COL_IMAGEURL = "imageurl"
val COL_DATE = "date"
val COL_Active = "active"

class DataBaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,1)
{
    override fun onCreate(p0: SQLiteDatabase?) {

        val CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_USERID + " INTEGER," +
                COL_Price + " DOUBLE," +
                COL_Location + " VARCHAR(256)," +
                COL_TITLE + " VARCHAR(256)," +
                COL_DESCRIPTION + " VARCHAR(256)," +
                COL_IMAGEURL + " VARCHAR(256)," +
                COL_Active + " INTEGER," +
                COL_DATE + " VARCHAR(256))";



        p0?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE " + TABLE_NAME)
        onCreate(p0)
    }

    fun insertProperties(property: Property): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", property.title)
            put("description", property.description)
            put("imageurl", property.image)
            put("date", property.date)
            put("user_id", property.user_id)
            put("price", property.price)
            put("location", property.location)

            put("active", property.active)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    @SuppressLint("Range")
    fun getAllProperties(): List<Property> {
        val properties = mutableListOf<Property>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

        with(cursor) {
            while (moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
                val user_id = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"))
                val price = cursor.getDouble(cursor.getColumnIndex("price"))
                val location = cursor.getString(cursor.getColumnIndexOrThrow("location"))
                val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                val description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                val imageurl = cursor.getString(cursor.getColumnIndexOrThrow("imageurl"))
                val date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                val active = cursor.getInt(cursor.getColumnIndexOrThrow("active"))// Convert to boolean if needed


                properties.add(Property(id, user_id,date,title,description,location,price,imageurl,active))
            }
            close()
        }

        return properties
    }



}