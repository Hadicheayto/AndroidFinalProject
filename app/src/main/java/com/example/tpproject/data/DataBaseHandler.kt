package com.example.tpproject.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
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
val COL_SUPPLIER = "supplier"
val COL_PHONENUMBER= "phonenumber"

val TABLE_USER = "user"
val COL_FNAME = "fname"
val COL_LNAME = "lname"
val COL_EMAIL = "email"
val COL_PASSWORD = "password"


class DataBaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,8)
{
    override fun onCreate(p0: SQLiteDatabase?) {

//        val CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
//                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//                COL_USERID + " INTEGER," +
//                COL_Price + " LONG," +
//                COL_Location + " VARCHAR(256)," +
//                COL_TITLE + " VARCHAR(256)," +
//                COL_DESCRIPTION + " VARCHAR(256)," +
//                COL_IMAGEURL + " VARCHAR(256)," +
//                COL_Active + " INTEGER," +
//                COL_SUPPLIER + " VARCHAR(256)," +
//                COL_PHONENUMBER + " INTEGER," +
//                COL_DATE + " VARCHAR(256))";
//
//        val CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " (" +
//                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//                COL_FNAME + " VARCHAR(256)," +
//                COL_LNAME + "  VARCHAR(256)," +
//                COL_EMAIL + "  VARCHAR(256)," +
//                COL_PASSWORD + "  VARCHAR(256),"+
//                COL_Active + " INTEGER)";

        p0?.execSQL("PRAGMA foreign_keys=ON;")

        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_USERID INTEGER," +
                "$COL_Price LONG," +
                "$COL_Location VARCHAR(256)," +
                "$COL_TITLE VARCHAR(256)," +
                "$COL_DESCRIPTION VARCHAR(256)," +
                "$COL_IMAGEURL VARCHAR(256)," +
                "$COL_Active INTEGER," +
                "$COL_SUPPLIER VARCHAR(256)," +
                "$COL_PHONENUMBER INTEGER," +
                "$COL_DATE VARCHAR(256)," +
                "FOREIGN KEY($COL_USERID) REFERENCES $TABLE_USER($COL_ID))";

        val CREATE_TABLE_USER = "CREATE TABLE $TABLE_USER (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_FNAME VARCHAR(256)," +
                "$COL_LNAME VARCHAR(256)," +
                "$COL_EMAIL VARCHAR(256)," +
                "$COL_PASSWORD VARCHAR(256)," +
                "$COL_Active INTEGER," +
                "CONSTRAINT fk_user_active FOREIGN KEY($COL_Active) REFERENCES $TABLE_NAME($COL_USERID))";

        p0?.execSQL(CREATE_TABLE)
        p0?.execSQL(CREATE_TABLE_USER)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
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
            put("supplier", property.supplier)
            put("phonenumber",property.phonenumber)
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
                val price = cursor.getLong(cursor.getColumnIndex("price"))
                val location = cursor.getString(cursor.getColumnIndexOrThrow("location"))
                val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                val description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                val imageurl = cursor.getString(cursor.getColumnIndexOrThrow("imageurl"))
                val date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                val supplier = cursor.getString(cursor.getColumnIndexOrThrow("supplier"))
                val phonenumber = cursor.getInt(cursor.getColumnIndexOrThrow("phonenumber"))
                val active = cursor.getInt(cursor.getColumnIndexOrThrow("active"))// Convert to boolean if needed


                properties.add(Property(id, user_id,date,title,description,location,price,imageurl,supplier,phonenumber,active))
            }
            close()
        }

        return properties
    }

    fun insertUser(user: User): Long {

        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_FNAME, user.fname)
            put(COL_LNAME, user.lname)
            put(COL_EMAIL, user.email)
            put(COL_PASSWORD, user.password)
            put(COL_Active, user.active)
        }
        return db.insert(TABLE_USER, null, values)
    }

    fun getUserById(userId: Long): User? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USER WHERE $COL_ID = ?", arrayOf(userId.toString()))

        return if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID))
            val fname = cursor.getString(cursor.getColumnIndexOrThrow(COL_FNAME))
            val lname = cursor.getString(cursor.getColumnIndexOrThrow(COL_LNAME))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD))
            val active = cursor.getInt(cursor.getColumnIndexOrThrow(COL_Active))

            User(id,fname,lname,email,password,active)
        } else {
            null
        }
    }

    fun editUser(user: User): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_FNAME, user.fname)
            put(COL_LNAME, user.lname)
            put(COL_EMAIL, user.email)
            put(COL_PASSWORD, user.password)
            put(COL_Active, user.active)
        }

        return db.update(
            TABLE_USER,
            values,
            "$COL_ID = ?",
            arrayOf(user.id.toString())
        )
    }

    fun getPropertiesByUserId(userId: Long): List<Property> {
        val properties = mutableListOf<Property>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL_USERID = ?", arrayOf(userId.toString()))

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COL_ID))
                val user_id = getInt(getColumnIndexOrThrow(COL_USERID))
                val price = getLong(getColumnIndexOrThrow(COL_Price))
                val location = getString(getColumnIndexOrThrow(COL_Location))
                val title = getString(getColumnIndexOrThrow(COL_TITLE))
                val description = getString(getColumnIndexOrThrow(COL_DESCRIPTION))
                val imageurl = getString(getColumnIndexOrThrow(COL_IMAGEURL))
                val date = getString(getColumnIndexOrThrow(COL_DATE))
                val supplier = getString(getColumnIndexOrThrow(COL_SUPPLIER))
                val phonenumber = getInt(getColumnIndexOrThrow(COL_PHONENUMBER))
                val active = getInt(getColumnIndexOrThrow(COL_Active))

                properties.add(Property(id, user_id, date, title, description, location, price, imageurl, supplier, phonenumber, active))
            }
            close()
        }

        return properties
    }

    fun getUserByEmailAndPassword(email: String, password: String): User? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USER WHERE $COL_EMAIL = ? AND $COL_PASSWORD = ?",
            arrayOf(email, password)
        )

        return if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID))
            val fname = cursor.getString(cursor.getColumnIndexOrThrow(COL_FNAME))
            val lname = cursor.getString(cursor.getColumnIndexOrThrow(COL_LNAME))
            val userEmail = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL))
            val userPassword = cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD))
            val active = cursor.getInt(cursor.getColumnIndexOrThrow(COL_Active))

            User(id, fname, lname, userEmail, userPassword, active)
        } else {
            null
        }
    }

}
