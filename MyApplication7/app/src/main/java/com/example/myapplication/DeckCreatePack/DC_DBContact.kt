package com.example.myapplication.DeckCreatePack

import android.provider.BaseColumns

object DC_DBContract {


    class UserEntry : BaseColumns {
        companion object {

            val TABLE_NAME = "users"
            val COLUMN__ID = "id"
            val COLUMN_FIRST_SITE = "first_site"
            val COLUMN_SECOND_SITE = "second_site"
            val COLUMN_DECK_NAME = "deck_name"
        }
    }
}