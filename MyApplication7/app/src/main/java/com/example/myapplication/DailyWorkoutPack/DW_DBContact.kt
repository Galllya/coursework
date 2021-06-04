package com.example.myapplication.DailyWorkoutPack

import android.provider.BaseColumns

object DW_DBContact {


    class UserEntry : BaseColumns {
        companion object {

            val TABLE_NAME = "day"
            val COLUMN__ID = "id"
            val COLUMN_BOX = "box"
            val COLUMN_FIRST_DAY = "first_day"
            val COLUMN_LAST_DAY = "last_day"
        }
    }
}