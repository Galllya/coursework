package com.example.myapplication.DailyWorkoutPack

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.DeckCreatePack.DC_UsersDBHelper
import com.example.myapplication.R
import java.text.SimpleDateFormat
import java.util.*

class DailyWorkout : AppCompatActivity() {
    lateinit var progress: TextView
    lateinit var dw_usersDBHelper : DW_UsersDBHelper
    lateinit var usersDBHelper : DC_UsersDBHelper
    lateinit var big: Button
    lateinit var b10: Button
    lateinit var b11: Button

    var id : ArrayList<String> = arrayListOf()
    var first: ArrayList<String> = arrayListOf()
    var second: ArrayList<String> = arrayListOf()
    var day_today = ""
    var side = 0
    var number = 0
    var have_we_words = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_workout)
        dw_usersDBHelper = DW_UsersDBHelper(getApplicationContext())
        usersDBHelper = DC_UsersDBHelper(getApplicationContext())
        big = findViewById(R.id.button7)
        b10 = findViewById(R.id.button10)
        b11 = findViewById(R.id.button11)


        progress = findViewById(R.id.progress)

        val f = SimpleDateFormat("dd/MM/yyyy")

        val currentDate = Date()

        val sdf = SimpleDateFormat("dd/MM/yyyy")

        progress.setText("")
        val formattedDate: String = sdf.format(currentDate)
        var user = dw_usersDBHelper.showDeckItem()
        user.forEach{
            var d = it.first_day
            val date = SimpleDateFormat("dd/MM/yyyy").parse(d)
            var day_today: String = f.format(date)
            val c = Calendar.getInstance()
            c.time = f.parse(day_today)
            var box = it.box
            if (box == "1") box = "1"
            if (box == "2") box = "2"
            if (box == "3") box = "7"
            if (box == "4") box = "30"

            c.add(Calendar.DATE, box.toInt())
            day_today = f.format(c.time)
            if (day_today == formattedDate) {
                id.add(it.id)
            }
        }

        id.forEach{
            first.add(usersDBHelper.FindFirstSide(it))
            second.add(usersDBHelper.FindSecondSide(it))
        }

            if (id.size == 0){
                 big.setText(R.string.no_word)
                have_we_words =false
                b10.setVisibility(View.GONE);
                b11.setVisibility(View.GONE);


            }else{
                big.setText(first.get(0))
            }


    }

    fun ChangeSide(view: View) {
        if (have_we_words == true) {
            if (side == 0) {
                big.setText(second.get(number))
                side = 1
            } else {
                big.setText(first.get(number))
                side = 0

            }
        }
    }

    fun Ret(view: View) {

    }
    fun True(view: View) {
        if (have_we_words == true) {
            side = 0
            var box = dw_usersDBHelper.FindBox(id.get(number))
            var box_int = 0
            if (box == "4") {
            } else {
                box_int = box.toInt() + 1
                dw_usersDBHelper.changeBox(id.get(number), box_int.toString())
            }

            val currentDate = Date()

            val sdf = SimpleDateFormat("dd/MM/yyyy")

            val formattedDate: String = sdf.format(currentDate)

            dw_usersDBHelper.ChangeFirstDay(id.get(number), formattedDate)
            number++
            if (id.size == number) {
                big.setText(R.string.good)
                have_we_words = false
            } else {
                big.setText(first.get(number))
            }

        }
    }
    fun False(view: View) {
        if (have_we_words == true) {
            side = 0
            var box = dw_usersDBHelper.FindBox(id.get(number))
            var box_int = 0
            if (box == "1") {
            } else {
                box_int = box.toInt() - 1
                dw_usersDBHelper.changeBox(id.get(number), box_int.toString())
            }

            val currentDate = Date()

            val sdf = SimpleDateFormat("dd/MM/yyyy")

            val formattedDate: String = sdf.format(currentDate)

            dw_usersDBHelper.ChangeFirstDay(id.get(number), formattedDate)
            number++
            if (id.size == number) {
                big.setText(R.string.good)
                have_we_words = false
            } else {
                big.setText(first.get(number))
            }

        }
    }
}