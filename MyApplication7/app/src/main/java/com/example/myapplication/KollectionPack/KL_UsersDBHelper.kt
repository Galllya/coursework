package com.example.myapplication.DeckCreatePack

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.DailyWorkoutPack.DW_DBContact
import com.example.myapplication.KollectionPack.KL_DBContect.UserEntry.Companion.COLUMN_DECK_NAME
import com.example.myapplication.KollectionPack.KL_DBContect.UserEntry.Companion.COLUMN_KOLLECTION_NAME
import com.example.myapplication.KollectionPack.KL_DBContect.UserEntry.Companion.TABLE_NAME
import com.example.myapplication.KollectionPack.KL_UserModel
import java.util.*

class KL_UsersDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun addDeck(user: KL_UserModel): Boolean {
        val db = writableDatabase

        val values = ContentValues()

        values.put(COLUMN_KOLLECTION_NAME, user.kollection_name)
        values.put(COLUMN_DECK_NAME, user.deck_names)

        val newRowId = db.insert(TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun undo() {
        val db = writableDatabase
        db.delete(TABLE_NAME, null, null)
    }

    fun deleteNided(name: String) {
        val db = writableDatabase
        val selection = COLUMN_KOLLECTION_NAME + " LIKE ?"
        val selectionArgs = arrayOf(name)
        db.delete(TABLE_NAME, selection, selectionArgs)
    }


    fun AddDeck(tag: String, name: String){
        val db = writableDatabase
        val selection = COLUMN_KOLLECTION_NAME + " LIKE ?"
        val selectionArgs = arrayOf(tag)
        val values = ContentValues()
        values.put(COLUMN_DECK_NAME, name)
        db.update(TABLE_NAME,values, selection, selectionArgs)
    }

    fun FindDeck(tag: String): String {
        val db = writableDatabase
        var cursor: Cursor
        cursor = db.rawQuery("Select * From kollection WHERE kollection_name =  ?;", arrayOf(tag))
        var a:String
        cursor.moveToFirst()
        a =  cursor.getString(cursor.getColumnIndex(COLUMN_DECK_NAME))
        return a
    }

    fun showAll(): ArrayList<KL_UserModel> {
        val users = ArrayList<KL_UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(KL_UsersDBHelper.SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var kollection: String
        var deck_name: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {

                kollection = cursor.getString(cursor.getColumnIndex(COLUMN_KOLLECTION_NAME))
                deck_name = cursor.getString(cursor.getColumnIndex(COLUMN_DECK_NAME))
                users.add(KL_UserModel(kollection, deck_name))
                cursor.moveToNext()
            }
        }
        return users
    }



    companion object {
        val DATABASE_VERSION = 1
        public val DATABASE_NAME = "Kollection.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_KOLLECTION_NAME + " TEXT PRIMARY KEY," +
                    COLUMN_DECK_NAME + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME
    }

}