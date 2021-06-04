package com.example.myapplication.KollectionPack


import android.provider.BaseColumns

object KL_DBContect {


    class UserEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "kollection"
            val COLUMN_KOLLECTION_NAME = "kollection_name"
            val COLUMN_DECK_NAME = "deck_names"
        }
    }
}