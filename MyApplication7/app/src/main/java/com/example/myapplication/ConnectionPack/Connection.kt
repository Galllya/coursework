package com.example.myapplication.ConnectionPack

import android.os.Build.VERSION_CODES.N
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import com.example.myapplication.DeckCreatePack.DC_UsersDBHelper
import com.example.myapplication.R

class Connection : AppCompatActivity() {

    var first: ArrayList<String> = arrayListOf("cat")
    var second: ArrayList<String> = arrayListOf("cat")

    var usedRandom: ArrayList<Int> = arrayListOf()
    var usedRandomPositionFerst: ArrayList<Int> = arrayListOf()
    var usedRandomPositionSecond: ArrayList<Int> = arrayListOf()

    lateinit var usersDBHelper : DC_UsersDBHelper


    lateinit var bt_First_1: Button
    lateinit var bt_First_2: Button
    lateinit var bt_First_3: Button
    lateinit var bt_First_4: Button
    lateinit var bt_First_5: Button
    lateinit var bt_First_6: Button

    lateinit var bt_Second_1: Button
    lateinit var bt_Second_2: Button
    lateinit var bt_Second_3: Button
    lateinit var bt_Second_4: Button
    lateinit var bt_Second_5: Button
    lateinit var bt_Second_6: Button

    var N = 0

    var S1 = ""
    var S2 = ""

    var change = true

    lateinit var bt: Button

    var number = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)

        var a = intent.getStringExtra("tag")

        usersDBHelper = DC_UsersDBHelper(getApplicationContext())

        bt_First_1 = findViewById(R.id.bt_First_1)
        bt_First_2 = findViewById(R.id.bt_First_2)
        bt_First_3 = findViewById(R.id.bt_First_3)
        bt_First_4 = findViewById(R.id.bt_First_4)
        bt_First_5 = findViewById(R.id.bt_First_5)
        bt_First_6 = findViewById(R.id.bt_First_6)

        bt_Second_1 = findViewById(R.id.bt_Second_1)
        bt_Second_2 = findViewById(R.id.bt_Second_2)
        bt_Second_3 = findViewById(R.id.bt_Second_3)
        bt_Second_4 = findViewById(R.id.bt_Second_4)
        bt_Second_5 = findViewById(R.id.bt_Second_5)
        bt_Second_6 = findViewById(R.id.bt_Second_6)

        N = usersDBHelper.howManyCard(a.toString())

        first = FindFirst()
        second = FindSecond()



        Start()
    }


    fun Start(){
        usedRandom.clear()
        usedRandomPositionFerst.clear()
        usedRandomPositionSecond.clear()
        var m = 0
        for (i in 0..6) {
            m = Random()
            var k = RandomPositionFirst()
            when (k) {
                1 -> bt_First_1.setText(first[m])
                2 -> bt_First_2.setText(first[m])
                3 -> bt_First_3.setText(first[m])
                4-> bt_First_4.setText(first[m])
                5-> bt_First_5.setText(first[m])
                6-> bt_First_6.setText(first[m])
            }
            var z = RandomPositionSecond()
            when (z) {
                1 -> bt_Second_1.setText(second[m])
                2 -> bt_Second_2.setText(second[m])
                3 -> bt_Second_3.setText(second[m])
                4-> bt_Second_4.setText(second[m])
                5-> bt_Second_5.setText(second[m])
                6-> bt_Second_6.setText(second[m])
            }

        }

         number = 0
        change = true



        MakeVision(bt_First_1)
        MakeVision(bt_First_2)
        MakeVision(bt_First_3)
        MakeVision(bt_First_4)
        MakeVision(bt_First_5)
        MakeVision(bt_First_6)

        MakeVision(bt_Second_1)
        MakeVision(bt_Second_2)
        MakeVision(bt_Second_3)
        MakeVision(bt_Second_4)
        MakeVision(bt_Second_5)
        MakeVision(bt_Second_6)


    }


    fun MakeVision(button: Button){
        button?.setClickable(true)
        button?.setVisibility(View.VISIBLE)
        button?.setBackgroundResource(R.drawable.boarder)
    }

    fun Random(): Int{
        var rnd = 0
        while (usedRandom.contains(rnd) == true){
            rnd = (0..N - 1).random()
        }
        usedRandom.add(rnd)
        return  rnd
    }

    fun RandomPositionFirst(): Int{
        var rnd = 0
        while (usedRandomPositionFerst.contains(rnd) == true){
            rnd = (1..6).random()
        }
        usedRandomPositionFerst.add(rnd)
        return  rnd
    }
    fun RandomPositionSecond(): Int{
        var rnd = 0
        while (usedRandomPositionSecond.contains(rnd) == true){
            rnd = (1..6).random()
        }
        usedRandomPositionSecond.add(rnd)
        return  rnd
    }



    fun FindFirst(): ArrayList<String>{
        var a = intent.getStringExtra("tag")
        var users = usersDBHelper.showDeckItem(a.toString())
        var lenghtDeck = users.size
        var first: ArrayList<String> = arrayListOf()
        users.forEach{
            first.add(it.first_site)
        }
        return first
    }
    fun FindSecond(): ArrayList<String>{
        var a = intent.getStringExtra("tag")
        var users = usersDBHelper.showDeckItem(a.toString())
        var lenghtDeck = users.size
        var second: ArrayList<String> = arrayListOf()
        users.forEach{
            second.add(it.second_site)
        }
        return second
    }

    fun bt_First_1_OnClick(view: View) {
        if (change == true) {
            S1 = bt_First_1.text.toString()
            markButtonDisable(bt_First_1)
            change = false
            bt = bt_First_1
        }

    }
    fun bt_First_2_OnClick(view: View) {

        if (change == true){
        S1 = bt_First_2.text.toString()
            change = false
            markButtonDisable(bt_First_2)
            bt = bt_First_2
        }

    }
    fun bt_First_3_OnClick(view: View) {
        if (change == true) {
            S1 = bt_First_3.text.toString()
            markButtonDisable(bt_First_3)
            change = false
            bt = bt_First_3

        }
    }
    fun bt_First_4_OnClick(view: View) {
        if (change == true) {
            S1 = bt_First_4.text.toString()
            markButtonDisable(bt_First_4)
            change = false
            bt = bt_First_4

        }

    }
    fun bt_Second_5_OnClick(view: View) {
        S2 = bt_Second_5.text.toString()

        var r = isTrue()
        if (r) {
            True(bt_Second_5)
            True(bt)}
        else{
            False(bt)
        }
        if (number == 6){
            Start()
        }
    }
    fun bt_First_6_OnClick(view: View) {
        if (change == true) {
            S1 = bt_First_6.text.toString()
            markButtonDisable(bt_First_6)
            change = false
            bt = bt_First_6

        }

    }
    fun bt_Second_1_OnClick(view: View) {
        S2 = bt_Second_1.text.toString()

        var r = isTrue()
        if (r) {
            True(bt_Second_1)
            True(bt)}
        else{
            False(bt)
        }
        if (number == 6){
            Start()
        }


    }
    fun bt_First_5_OnClick(view: View) {
        if (change == true) {
            S1 = bt_First_5.text.toString()
            markButtonDisable(bt_First_5)
            change = false
            bt = bt_First_5

        }

    }
    fun bt_Second_4_OnClick(view: View) {
        S2 = bt_Second_4.text.toString()

        var r = isTrue()
        if (r) {
            True(bt_Second_4)
            True(bt)}
        else{
            False(bt)
        }
        if (number == 6){
            Start()
        }

    }
    fun bt_Second_6_OnClick(view: View) {
        S2 = bt_Second_6.text.toString()

        var r = isTrue()
        if (r) {
            True(bt_Second_6)
            True(bt)}
        else{
            False(bt)
        }
        if (number == 6){
            Start()
        }


    }
    fun bt_Second_2_OnClick(view: View) {
        S2 = bt_Second_2.text.toString()
        var r = isTrue()
        if (r) {
            True(bt_Second_2)
            True(bt)}
        else{
            False(bt)
        }
        if (number == 6){
            Start()
        }


    }
    fun bt_Second_3_OnClick(view: View) {
        S2 = bt_Second_3.text.toString()

        var r = isTrue()
        if (r) {
            True(bt_Second_3)
            True(bt)}
        else{
            False(bt)
        }
        if (number == 6){
            Start()
        }

    }

    fun markButtonDisable(button: Button) {
        button?.setClickable(false)
        button?.setBackgroundResource(R.drawable.dark_green)
        //button?.setVisibility(View.GONE);
    }

    fun True(button: Button) {
        button?.setVisibility(View.GONE);
    }

    fun False(button: Button) {
        button?.setClickable(true)
        button?.setBackgroundResource(R.drawable.boarder)
    }


    fun isTrue(): Boolean{
        var k = first.indexOf(S1)
        var r = second.indexOf(S2)
        S1 = ""
        S2 = ""
        change = true

        if(k == r){

            number++

            return true

        }
        else{
            return false
        }
    }

}

