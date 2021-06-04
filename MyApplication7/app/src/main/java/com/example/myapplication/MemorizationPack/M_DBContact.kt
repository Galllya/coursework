package com.example.myapplication.DeckCreatePack

import android.provider.BaseColumns

object M_DBContact {


    class UserEntry : BaseColumns {
        companion object {

            val TABLE_NAME = "memo"
            val COLUMN_DECK_NAME = "deck_name"
            val COLUMN_NO_STUDY = "no_study"
            val COLUMN_IN_PROCESS = "in_process"
            val COLUMN_WAS_STUDY = "was_study"
            val COLUMN_KNOW_IT = "know_it"
            val COLUMN_SHOULD_GO = "should_go"
        }
    }
}