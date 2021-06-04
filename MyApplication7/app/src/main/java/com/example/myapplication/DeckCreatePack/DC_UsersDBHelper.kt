package com.example.myapplication.DeckCreatePack

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.DailyWorkoutPack.DW_DBContact
import java.util.*

class DC_UsersDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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
    fun addCard(user: DC_UserModel): Boolean {
        val db = writableDatabase

        val values = ContentValues()

        values.put(DC_DBContract.UserEntry.COLUMN__ID, user.id)
        values.put(DC_DBContract.UserEntry.COLUMN_FIRST_SITE, user.first_site)
        values.put(DC_DBContract.UserEntry.COLUMN_SECOND_SITE, user.second_site)
        values.put(DC_DBContract.UserEntry.COLUMN_DECK_NAME, user.deck_name)

        val newRowId = db.insert(DC_DBContract.UserEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteLast(id: String): Boolean {
        val db = writableDatabase
        val selection = DC_DBContract.UserEntry.COLUMN__ID + " LIKE ?"
        val selectionArgs = arrayOf(id)
        db.delete(DC_DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }



    @Throws(SQLiteConstraintException::class)
    fun deleteNided(id: String) {
        val db = writableDatabase
        val selection = DC_DBContract.UserEntry.COLUMN__ID + " LIKE ?"
        val selectionArgs = arrayOf(id)
        db.delete(DC_DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)
    }


    @Throws(SQLiteConstraintException::class)
    fun undo(id: String): Boolean {
        val db = writableDatabase
        db.delete(DC_DBContract.UserEntry.TABLE_NAME, null, null)

        return true
    }


    fun maxID(): String {
        val db = writableDatabase


        val c = db.query(
                DC_DBContract.UserEntry.TABLE_NAME,
                arrayOf("MAX(" +  DC_DBContract.UserEntry.COLUMN__ID.toString() + ")"),
                null,
                null,
                null,
                null,
                null
        )

        val buffer =
                StringBuffer("")

        if (c.moveToFirst()) {
            val max_id = c.getString(0)
            buffer.append(max_id)
        }
        c.close()
        db.close()
        return buffer.toString()
    }

    fun numberOf(): String {
        val db = writableDatabase


        val c = db.query(
                DC_DBContract.UserEntry.TABLE_NAME,
                arrayOf("MAX(" +  DC_DBContract.UserEntry.COLUMN__ID.toString() + ")"),
                null,
                null,
                null,
                null,
                null
        )
        val buffer =
                StringBuffer("")

        if (c.moveToFirst()) {
            val max_id = c.getString(0)
            buffer.append(max_id)
        }
        c.close()
        db.close()
        return buffer.toString()
    }


    fun findDeckName(): ArrayList<DC_UserModel> {
        val users = ArrayList<DC_UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("Select * From users GROUP BY  deck_name ;" , null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var id: String
        var first_site: String
        var second_site: String
        var deck_name: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {


                id = cursor.getString(cursor.getColumnIndex(DC_DBContract.UserEntry.COLUMN__ID))
                first_site = cursor.getString(cursor.getColumnIndex(DC_DBContract.UserEntry.COLUMN_FIRST_SITE))
                second_site = cursor.getString(cursor.getColumnIndex(DC_DBContract.UserEntry.COLUMN_SECOND_SITE))
                deck_name = cursor.getString(cursor.getColumnIndex(DC_DBContract.UserEntry.COLUMN_DECK_NAME))
                users.add(DC_UserModel(id, first_site, second_site, deck_name))
                cursor.moveToNext()
            }
        }
        return users
    }

    @Throws(SQLiteConstraintException::class)
    fun DeleteThisDeck(tag: String) {
        val db = writableDatabase
        val selection = DC_DBContract.UserEntry.COLUMN_DECK_NAME + " LIKE ?"
        val selectionArgs = arrayOf(tag)
        db.delete(DC_DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)
    }
    @Throws(SQLiteConstraintException::class)
    fun ChanceDeckEliment(tag: String, N1: String, N2: String) {
        val db = writableDatabase

        val selection = DC_DBContract.UserEntry.COLUMN__ID + " LIKE ?"
        val selectionArgs = arrayOf(tag)
        val values = ContentValues()
        values.put(DC_DBContract.UserEntry.COLUMN_FIRST_SITE, N1)
        values.put(DC_DBContract.UserEntry.COLUMN_SECOND_SITE, N2)
        db.update(DC_DBContract.UserEntry.TABLE_NAME,values, selection, selectionArgs)


    }


    fun howManyCard(tag: String): Int {
        val users = ArrayList<DC_UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        var number = 0
        try {
            cursor = db.query(
                    "users", arrayOf("deck_name"),
                    "deck_name = ?", arrayOf(tag),
                    null, null, null
            )
            while (cursor.moveToNext()) {
                number += 1
            }
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
        }
        return number
    }

    fun FindFirstSide(id: String): String {
        val db = writableDatabase
        var cursor: Cursor
        cursor = db.rawQuery("Select * From users WHERE id =  ?;", arrayOf(id))
        var a:String
        cursor.moveToFirst()
        a =  cursor.getString(cursor.getColumnIndex(DC_DBContract.UserEntry.COLUMN_FIRST_SITE))
        return a
    }

    fun FindSecondSide(id: String): String {
        val db = writableDatabase
        var cursor: Cursor
        cursor = db.rawQuery("Select * From users WHERE id =  ?;", arrayOf(id))
        var a:String
        cursor.moveToFirst()
        a =  cursor.getString(cursor.getColumnIndex(DC_DBContract.UserEntry.COLUMN_SECOND_SITE))
        return a
    }


    fun showDeckItem(tag: String): ArrayList<DC_UserModel> {
        val users = ArrayList<DC_UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.query(
                    "users", arrayOf(DC_DBContract.UserEntry.COLUMN__ID, DC_DBContract.UserEntry.COLUMN_FIRST_SITE,DC_DBContract.UserEntry.COLUMN_SECOND_SITE, DC_DBContract.UserEntry.COLUMN_DECK_NAME ),
                    "deck_name = ?", arrayOf(tag),
                    null, null, null
            )
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var id: String
        var first_site: String
        var second_site: String
        var deck_name: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {

                id = cursor.getString(cursor.getColumnIndex(DC_DBContract.UserEntry.COLUMN__ID))
                first_site = cursor.getString(cursor.getColumnIndex(DC_DBContract.UserEntry.COLUMN_FIRST_SITE))
                second_site = cursor.getString(cursor.getColumnIndex(DC_DBContract.UserEntry.COLUMN_SECOND_SITE))
                deck_name = cursor.getString(cursor.getColumnIndex(DC_DBContract.UserEntry.COLUMN_DECK_NAME))
                users.add(DC_UserModel(id, first_site, second_site, deck_name))
                cursor.moveToNext()
            }
        }
        return users
    }


    fun showAll(): ArrayList<DC_UserModel> {
        val users = ArrayList<DC_UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DC_DBContract.UserEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var id: String
        var first_site: String
        var second_site: String
        var deck_name: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {

                id = cursor.getString(cursor.getColumnIndex(DC_DBContract.UserEntry.COLUMN__ID))
                first_site = cursor.getString(cursor.getColumnIndex(DC_DBContract.UserEntry.COLUMN_FIRST_SITE))
                second_site = cursor.getString(cursor.getColumnIndex(DC_DBContract.UserEntry.COLUMN_SECOND_SITE))
                deck_name = cursor.getString(cursor.getColumnIndex(DC_DBContract.UserEntry.COLUMN_DECK_NAME))
                users.add(DC_UserModel(id, first_site, second_site, deck_name))
                cursor.moveToNext()
            }
        }
        return users
    }




    companion object {
        val DATABASE_VERSION = 1
        public val DATABASE_NAME = "Deck.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DC_DBContract.UserEntry.TABLE_NAME + " (" +
                        DC_DBContract.UserEntry.COLUMN__ID + " TEXT PRIMARY KEY," +
                        DC_DBContract.UserEntry.COLUMN_FIRST_SITE + " TEXT," +
                        DC_DBContract.UserEntry.COLUMN_SECOND_SITE + " TEXT," +
                        DC_DBContract.UserEntry.COLUMN_DECK_NAME + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DC_DBContract.UserEntry.TABLE_NAME
    }

}