package com.example.myapplication.CardsPack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.myapplication.DeckCreatePack.DC_UsersDBHelper
import com.example.myapplication.R

class Cards : AppCompatActivity() {
    lateinit var usersDBHelper : DC_UsersDBHelper
    lateinit var big: Button
    var side = true
    var part = 0
    var what_side  = "first_site"
    var first: Array<String?> = arrayOf()
    var second: Array<String?> = arrayOf()
    lateinit var progress: TextView
    var write = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)
        big = findViewById(R.id.button7)
        usersDBHelper = DC_UsersDBHelper(getApplicationContext())
        progress = findViewById(R.id.progress)
        first = FindFirst()
        big.setText(first[0])
        write = (part+1).toString() + " / " + first.size.toString()
        progress.setText(write)



    }

    fun FindFirst(): Array<String?>{
        var a = intent.getStringExtra("tag")
        var users = usersDBHelper.showDeckItem(a.toString())
        var lenghtDeck = users.size
        var first: Array<String?> = arrayOfNulls(lenghtDeck)
        var i = 0
        users.forEach{
            first[i] = it.first_site
            i++
        }
        return first
    }
    fun FindSecond(): Array<String?>{
        var a = intent.getStringExtra("tag")
        var users = usersDBHelper.showDeckItem(a.toString())
        var lenghtDeck = users.size
        var second: Array<String?> = arrayOfNulls(lenghtDeck)
        var i = 0
        users.forEach{
            second[i] = it.second_site
            i++
        }
        return second
    }
    fun ChangeSite(view: View) {
        first = FindFirst()
        second = FindSecond()

        if(what_side  == "first_site"){
            big.setText(second[part])
            what_side  = "second_side"
        } else{
            big.setText(first[part])
            what_side  = "first_site"
        }
    }

    fun Last(view: View) {

        if (side == true) { second = FindFirst()
            what_side  = "first_site"

        }
        else{
            second = FindSecond()
            what_side  = "second_side"
        }
        if (part == 0) {part = second.size-1}
        else{
        part--
        }
        write = (part+1).toString() + " / " + first.size.toString()
        progress.setText(write)
        big.setText(second[part])


    }

    fun Next(view: View) {
        if (side == true) { first = FindFirst()
            what_side  = "first_site"


        }
        else{
            first = FindSecond()
            what_side  = "second_side"
        }


        if (part == first.size-1) {part = 0}
        else{
            part++
        }
        write = (part+1).toString() + " / " + first.size.toString()
        progress.setText(write)
        big.setText(first[part])


    }

    fun ChangeSideOnAllTime(view: View) {

        if (side == true) {
            side = false
            second = FindSecond()
            big.setText(second[part])
            what_side  = "second_side"
        }
        else{
            side = true
            first = FindFirst()
            big.setText(first[part])
            what_side  = "first_site"
        }


    }
}