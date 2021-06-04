package com.example.myapplication.ChoicePack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.myapplication.DeckCreatePack.DC_UsersDBHelper
import com.example.myapplication.R

class Choice : AppCompatActivity() {

    lateinit var bt_1: Button
    lateinit var bt_2: Button
    lateinit var bt_3: Button
    lateinit var bt_4: Button

    lateinit var bt_7: Button

    lateinit var usersDBHelper : DC_UsersDBHelper

    var N = 0

    var first: ArrayList<String> = arrayListOf("cat")
    var second: ArrayList<String> = arrayListOf("cat")

    var S1 = ""
    var S2 = ""

    var usedRandom: ArrayList<Int> = arrayListOf()
    var usedRandomButton: ArrayList<Int> = arrayListOf()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice)

        usersDBHelper = DC_UsersDBHelper(getApplicationContext())

        var a = intent.getStringExtra("tag")


        N = usersDBHelper.howManyCard(a.toString())


        bt_1 = findViewById(R.id.bt_1)
        bt_2 = findViewById(R.id.bt_2)
        bt_3 = findViewById(R.id.bt_3)
        bt_4 = findViewById(R.id.bt_4)
        bt_7 = findViewById(R.id.button7)

        first = FindFirst()
        second = FindSecond()



        Start()



    }

    fun Start(){
        usedRandom.clear()
        usedRandomButton.clear()
        var rnd = (0..N - 1).random()

        usedRandom.add(rnd)

        S1 = first.get(rnd)
        bt_7.setText(S1)

        var m = rnd
        for (i in 0..3) {
            var k = RandomButton()
            when (k)
            {
                0 -> bt_1.setText(second[m])
                1 -> bt_2.setText(second[m])
                2 -> bt_3.setText(second[m])
                3->  bt_4.setText(second[m])
            }
            m = Random()
        }
        MakeVision(bt_1)
        MakeVision(bt_2)
        MakeVision(bt_3)
        MakeVision(bt_4)


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

    fun RandomButton(): Int{
        var rnd = (0..3).random()
        while (usedRandomButton.contains(rnd) == true){
            rnd = (0..3).random()
        }
        usedRandomButton.add(rnd)
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

    fun bt_4(view: View) {
        S2 = bt_4.text.toString()
        Find(bt_4)
    }
    fun bt_3(view: View) {
        S2 = bt_3.text.toString()
        Find(bt_3)
    }
    fun bt_2(view: View) {
        S2 = bt_2.text.toString()
        Find(bt_2)
    }
    fun bt_1(view: View) {
        S2 = bt_1.text.toString()
        Find(bt_1)
    }

 fun Find(button: Button){
     if (first.indexOf(S1)==second.indexOf(S2)){
         Start()
     }
     else{
         button?.setBackgroundResource(R.drawable.dark_pink)

     }
 }


}