package com.example.myapplication.DailyWorkoutPack


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.DailyWorkoutPack.DW_DBContact.UserEntry.Companion.COLUMN_BOX
import com.example.myapplication.DailyWorkoutPack.DW_DBContact.UserEntry.Companion.COLUMN_FIRST_DAY
import com.example.myapplication.DailyWorkoutPack.DW_DBContact.UserEntry.Companion.COLUMN_LAST_DAY
import com.example.myapplication.DailyWorkoutPack.DW_DBContact.UserEntry.Companion.COLUMN__ID
import com.example.myapplication.DailyWorkoutPack.DW_DBContact.UserEntry.Companion.TABLE_NAME
import com.example.myapplication.DeckCreatePack.DC_DBContract
import com.example.myapplication.DeckCreatePack.DC_UserModel
import com.example.myapplication.DeckCreatePack.DC_UsersDBHelper
import com.example.myapplication.DeckCreatePack.M_DBContact
import java.util.*

class DW_UsersDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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
    fun addCard(user: DW_UserModel) {
        val db = writableDatabase

        val values = ContentValues()

        values.put(COLUMN__ID, user.id)
        values.put(COLUMN_BOX, user.box)
        values.put(COLUMN_FIRST_DAY, user.first_day)
        values.put(COLUMN_LAST_DAY, user.last_day)

        db.insert(TABLE_NAME, null, values)

    }

    @Throws(SQLiteConstraintException::class)
    fun undo() {
        val db = writableDatabase
        db.delete(TABLE_NAME, null, null)
    }

    fun deleteWithDeck(id: String) {
        val db = writableDatabase
        val selection = COLUMN__ID + " LIKE ?"
        val selectionArgs = arrayOf(id)
        db.delete(TABLE_NAME, selection, selectionArgs)
    }

    fun changeBox(id: String, box: String){
        val db = writableDatabase
        val selection = COLUMN__ID + " LIKE ?"
        val selectionArgs = arrayOf(id)
        val values = ContentValues()
        values.put(COLUMN_BOX,box)
        db.update(TABLE_NAME,values, selection, selectionArgs)
    }

    fun FindBox(id: String): String{
        val db = writableDatabase
        var cursor: Cursor
        cursor = db.rawQuery("Select * From day WHERE id =  ?;", arrayOf(id))
        var a:String
        cursor.moveToFirst()
        a =  cursor.getString(cursor.getColumnIndex(COLUMN_BOX))
        return a
    }

    fun ChangeFirstDay(id: String, day: String){
        val db = writableDatabase
        val selection = COLUMN__ID + " LIKE ?"
        val selectionArgs = arrayOf(id)
        val values = ContentValues()
        values.put(COLUMN_FIRST_DAY,day)
        db.update(TABLE_NAME,values, selection, selectionArgs)
    }


    fun FindShouldDay(id: String): String {
        val db = writableDatabase
        var cursor: Cursor
        cursor = db.rawQuery("Select * From day WHERE id =  ?;", arrayOf(id))
        var a:String
        cursor.moveToFirst()
        a =  cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_DAY))
        return a
    }



    fun showDeckItem(): ArrayList<DW_UserModel> {
        val users = ArrayList<DW_UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.query(
                    "day", null,
                    null, null,
                    null, null, null
            )
        } catch (e: SQLiteException) {
            db.execSQL(DW_UsersDBHelper.SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var id: String
        var box: String
        var first_day: String
        var last_day: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {

                id = cursor.getString(cursor.getColumnIndex(COLUMN__ID))
                box = cursor.getString(cursor.getColumnIndex(COLUMN_BOX) )
                first_day = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_DAY))
                last_day = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_DAY))
                users.add(DW_UserModel(id, box, first_day, last_day))
                cursor.moveToNext()
            }
        }
        return users
    }


    companion object {
        val DATABASE_VERSION = 1
        public val DATABASE_NAME = "Day.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " +TABLE_NAME + " (" +
                        COLUMN__ID + " TEXT PRIMARY KEY," +
                        COLUMN_BOX + " TEXT," +
                        COLUMN_FIRST_DAY + " TEXT," +
                        COLUMN_LAST_DAY + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME
    }

}