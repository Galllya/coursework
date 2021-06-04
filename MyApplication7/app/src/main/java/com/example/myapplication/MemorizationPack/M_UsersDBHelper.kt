package com.example.myapplication.DeckCreatePack

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.MemorizationPack.M_UserModel
import java.util.*

class MC_UsersDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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


    fun addMemo(user: M_UserModel) {
        val db = writableDatabase

        val values = ContentValues()

        values.put(M_DBContact.UserEntry.COLUMN_DECK_NAME, user.deck_name)
        values.put(M_DBContact.UserEntry.COLUMN_NO_STUDY, user.no_study)
        values.put(M_DBContact.UserEntry.COLUMN_IN_PROCESS, user.in_process)
        values.put(M_DBContact.UserEntry.COLUMN_WAS_STUDY, user.was_study)
        values.put(M_DBContact.UserEntry.COLUMN_KNOW_IT, user.know_it)
        values.put(M_DBContact.UserEntry.COLUMN_SHOULD_GO, user.should_go)

        val newRowId = db.insert(M_DBContact.UserEntry.TABLE_NAME, null, values)
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteAll() {
        val db = writableDatabase
        db.delete(M_DBContact.UserEntry.TABLE_NAME, null, null)
    }

    fun deleteNeded(tag: String) {
        val db = writableDatabase
        val selection = M_DBContact.UserEntry.COLUMN_DECK_NAME + " LIKE ?"
        val selectionArgs = arrayOf(tag)
        db.delete(M_DBContact.UserEntry.TABLE_NAME, selection, selectionArgs)

    }

    fun UpdateShouldGo(tag: String, N1: Int) {
        val db = writableDatabase
        val selection =  M_DBContact.UserEntry.COLUMN_DECK_NAME + " LIKE ?"
        val selectionArgs = arrayOf(tag)
        val values = ContentValues()
        values.put(M_DBContact.UserEntry.COLUMN_SHOULD_GO, N1.toString())
        db.update(M_DBContact.UserEntry.TABLE_NAME,values, selection, selectionArgs)
    }

    fun FindShouldGo(tag: String): String {
        val db = writableDatabase
        var cursor: Cursor
        cursor = db.rawQuery("Select * From memo WHERE deck_name =  ?;", arrayOf(tag))
        var a:String
        cursor.moveToFirst()
       a =  cursor.getString(cursor.getColumnIndex(M_DBContact.UserEntry.COLUMN_SHOULD_GO))
        return a
    }

    fun FindAll(tag: String): ArrayList<M_UserModel> {
        val users = ArrayList<M_UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        cursor = db.rawQuery("Select * From memo WHERE deck_name =  ?;", arrayOf(tag))

        var no_study: String
        var in_process: String
        var was_study : String
        var know_it : String
        var shouid_go: String
        cursor.moveToFirst()
        no_study =  cursor.getString(cursor.getColumnIndex(M_DBContact.UserEntry.COLUMN_NO_STUDY))
        in_process=  cursor.getString(cursor.getColumnIndex(M_DBContact.UserEntry.COLUMN_IN_PROCESS))
        was_study = cursor.getString(cursor.getColumnIndex(M_DBContact.UserEntry.COLUMN_WAS_STUDY))
        know_it = cursor.getString(cursor.getColumnIndex(M_DBContact.UserEntry.COLUMN_KNOW_IT))
        shouid_go = cursor.getString(cursor.getColumnIndex(M_DBContact.UserEntry.COLUMN_SHOULD_GO))

        users.add(M_UserModel(tag, no_study, in_process, was_study, know_it, shouid_go))
        return users
    }

    /*fun HaveWeThisMemo(tag: String): String {
        val db = writableDatabase
        var cursor: Cursor
        cursor = db.rawQuery("Select * From memo WHERE deck_name =  ?;", arrayOf(tag))
        var a:Int
        cursor.moveToFirst()
        a =  cursor.getCount()
        return a.toString()
    }*/


    fun undo(): Boolean {
        val db = writableDatabase
        db.delete(M_DBContact.UserEntry.TABLE_NAME, null, null)

        return true
    }



    companion object {
        val DATABASE_VERSION = 1
        public val DATABASE_NAME = "Memo.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + M_DBContact.UserEntry.TABLE_NAME + " (" +
                        M_DBContact.UserEntry.COLUMN_DECK_NAME + " TEXT PRIMARY KEY," +
                        M_DBContact.UserEntry.COLUMN_NO_STUDY + " TEXT," +
                        M_DBContact.UserEntry.COLUMN_IN_PROCESS + " TEXT," +
                        M_DBContact.UserEntry.COLUMN_WAS_STUDY + " TEXT," +
                        M_DBContact.UserEntry.COLUMN_KNOW_IT + " TEXT," +
                        M_DBContact.UserEntry.COLUMN_SHOULD_GO + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DC_DBContract.UserEntry.TABLE_NAME
    }

}