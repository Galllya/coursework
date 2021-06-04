package com.example.myapplication.MemorizationPack

import AllDialogFragment
import MemorizationDialogFragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.DeckCreatePack.DC_UserModel
import com.example.myapplication.DeckCreatePack.DC_UsersDBHelper
import com.example.myapplication.DeckCreatePack.MC_UsersDBHelper
import com.example.myapplication.R
import com.example.myapplication.SeeDeckPack.SeeDeck
import com.example.myapplication.ShowDeck.Show_Deck
import java.util.*
import kotlin.collections.ArrayList
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random.Default.nextInt

class Memorization : AppCompatActivity() {
    lateinit var big: Button
    lateinit var progress: TextView
    lateinit var m_usersDBHelper : MC_UsersDBHelper
    lateinit var usersDBHelper : DC_UsersDBHelper
    lateinit var noStudy: TextView
    lateinit var inProces: TextView
    lateinit var wasStudy: TextView
    var first: ArrayList<String> = arrayListOf("cat")
    var second: ArrayList<String> = arrayListOf("cat")
    var N:Int = 0
    var part = 0
    var notShowed : ArrayList<Boolean> = arrayListOf()
    var inProcess: ArrayList<Boolean> = arrayListOf()
    var studied: ArrayList<Boolean> = arrayListOf()
    var knowIt: ArrayList<Int> = arrayListOf()
    var write: String = ""
    var allDone: Boolean = false
    var what_side  = "first_site"
    var side = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memorization)
        usersDBHelper = DC_UsersDBHelper(getApplicationContext())
        m_usersDBHelper = MC_UsersDBHelper(getApplicationContext())
        big = findViewById(R.id.button7)
        noStudy = findViewById(R.id.noStudy)
        inProces = findViewById(R.id.inProcess)
        wasStudy = findViewById(R.id.wasStudy)
        var a = intent.getStringExtra("tag")

        noStudy.text = ""
        inProces.text = ""
        wasStudy.text = ""

        if(m_usersDBHelper.FindShouldGo(a.toString())=="0") {

            first = FindFirst()
            second = FindSecond()
            N = first.size
            noStudy.text = (N - 1).toString() + " / " + N.toString()
            inProces.text = "1 / " + N.toString()
            wasStudy.text = "0 / " + N.toString()

            for (i in 0..N - 1) {
                notShowed.add(i, true)
                inProcess.add(i, false)
                studied.add(i, false)
                knowIt.add(i, 0)
            }

            val rnds = (0..N - 1).random()

            part = rnds
            notShowed.removeAt(rnds)
            notShowed.add(rnds, false)
            inProcess.add(rnds, true)
            big.setText(first.get(rnds))
        }
        else{
            var users = m_usersDBHelper.FindAll(a.toString())
            var find: ArrayList<String> = arrayListOf()
            users.forEach{
                find.add(it.in_process)
            }
            notShowed.clear()
            inProcess.clear()
            studied.clear()
            knowIt.clear()
            first = FindFirst()
            second = FindSecond()
            N = first.size
            for(char in users.first().no_study){
                if (char == 'f'){
                    notShowed.add( false)
                }
                if (char == 't'){
                    notShowed.add( true)
                }
            }
            for(char in users.first().in_process){
                if (char == 'f'){
                    inProcess.add( false)
                }
                if (char == 't'){
                    inProcess.add( true)
                }
            }
            for(char in users.first().was_study){
                if (char == 'f'){
                    studied.add( false)
                }
                if (char == 't'){
                    studied.add( true)
                }
            }
            for(char in users.first().know_it){
                if (char == '0'){
                    knowIt.add( 0)
                }
                if (char == '1'){
                    knowIt.add( 1)
                }
                if (char == '2'){
                    knowIt.add( 2)
                }
                if (char == '3'){
                    knowIt.add( 3)
                }

            }

            var rnds = 0
            for (i in 0..N-1) {
                if (inProcess.get(i) == true){
                    rnds = i
                }
            }

            part = rnds

            big.setText(first.get(rnds))

            Statistic()

        }




    }
    override fun onStop() {
        super.onStop()
        var a = intent.getStringExtra("tag")
        var no_study  = ""
        var in_process = ""
        var was_study = ""
        var know_it = ""
        var shouid_go = m_usersDBHelper.FindShouldGo(a.toString())
        for (i in 0..N-1){
            no_study = no_study + "+" + notShowed.get(i).toString()
            in_process = in_process + "+" + inProcess.get(i).toString()
            was_study = was_study + "+" + studied.get(i).toString()
            know_it = know_it + "+" + knowIt.get(i).toString()
        }


        m_usersDBHelper.deleteNeded(a.toString())
        m_usersDBHelper.addMemo(
                M_UserModel(
                        deck_name = a.toString(),
                        no_study = no_study,
                        in_process = in_process,
                        was_study = was_study,
                        know_it = know_it,
                        should_go = shouid_go
                )
        )


    }


    fun GoBeck(){
        var a = intent.getStringExtra("tag")
        val intent = Intent(this, SeeDeck::class.java)
        intent.putExtra("name", a.toString())
        startActivity(intent)
        this.finish();
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


    fun Knowed(view: View) {

        what_side = "first_site"
        var rnd = 0
        var number  = knowIt.get(part) + 1
        knowIt.removeAt(part)
        knowIt.add(part,  number)

        if (knowIt.get(part)==3){
            inProcess.removeAt(part)
            inProcess.add(part, false)
            studied.removeAt(part)
            studied.add(part, true)
        }

        var k = 0

        if (studied.all {it==true }){
            val myDialogFragment = AllDialogFragment()
            val manager = supportFragmentManager
            myDialogFragment.show(manager, "myDialog")
            allDone = true
            var a = intent.getStringExtra("tag")
            var f = 0
            m_usersDBHelper.UpdateShouldGo(a.toString(), f)
        }
        else{
            if ((inProcess.count{it==true}==10)){
                WriteText()
            }
            else {
                if (notShowed.all { it== false}){
                    WriteText()

                }
                else {
                    rnd = (0..N - 1).random()
                    while ((notShowed.get(rnd) == false)) {
                        rnd = (0..N - 1).random()
                    }

                    part = rnd
                    notShowed.removeAt(rnd)
                    notShowed.add(rnd, false)
                    inProcess.removeAt(rnd)
                    inProcess.add(rnd, true)

                    write = first[rnd]
                    big.setText(write)
                }
            }
            var a = intent.getStringExtra("tag")
            m_usersDBHelper.UpdateShouldGo(a.toString(), 1)
        }

        Statistic()


    }

    fun Statistic(){
        noStudy.text = notShowed.count { it == true }.toString() + " / " + N.toString()
        inProces.text = inProcess.count { it == true }.toString() + " / " + N.toString()
        wasStudy.text = studied.count { it == true }.toString() +" / " + N.toString()
    }

    fun WriteText(){
        var rnd = (0..N - 1).random()
        while ((inProcess.get(rnd) == false)) {
            rnd = (0..N - 1).random()
        }
        part = rnd

        write = first[rnd]
        big.setText(write)
    }
    fun ShowAnswer(view: View) {
        if(what_side  == "first_site"){
            big.setText(second.get(part))
            what_side  = "second_side"
        } else{
            big.setText(first.get(part))
            what_side  = "first_site"
        }
    }
    fun DontKnowed(view: View) {
        what_side = "first_site"
        if (allDone == false) {
            var number = 0
            knowIt.removeAt(part)
            knowIt.add(part, number)

            if (knowIt.get(part) == 3) {
                inProcess.removeAt(part)
                inProcess.add(part, false)
                studied.removeAt(part)
                studied.add(part, true)
            }
            if ((inProcess.count { it == true } == 10)) {
                WriteText()
            } else {
                if (notShowed.all { it == false }) {
                    WriteText()

                } else {
                    var rnd = (0..N - 1).random()
                    while ((notShowed.get(rnd) == false)) {
                        rnd = (0..N - 1).random()
                    }

                    part = rnd
                    notShowed.removeAt(rnd)
                    notShowed.add(rnd, false)
                    inProcess.removeAt(rnd)
                    inProcess.add(rnd, true)

                    write = first[rnd]
                    big.setText(write)
                }
            }
        }
        var a = intent.getStringExtra("tag")
        m_usersDBHelper.UpdateShouldGo(a.toString(), 1)
        Statistic()
    }

    fun Change(view: View) {
        val myDialogFragment = MemorizationDialogFragment()
        val manager = supportFragmentManager
        myDialogFragment.show(manager, "myDialog")

    }
    fun YesChance(){
        if(side ==true){
            side=false
            first.clear()
            first = FindSecond()
            second.clear()
            second = FindFirst()}
        else{
            side=true
            first.clear()
            first = FindFirst()
            second.clear()
            second = FindSecond()
        }


        N = first.size


        for (i in 0..N-1){
            notShowed.removeAt(i)
            notShowed.add(i, true)
            inProcess.removeAt(i)
            inProcess.add(i, false)
            studied.removeAt(i)
            studied.add(i, false)
            knowIt.removeAt(i)
            knowIt.add(i, 0)
        }

        val rnds = (0..N-1).random()

        part = rnds
        notShowed.removeAt(rnds)
        notShowed.add(rnds, false)
        inProcess.add(rnds, true)
        big.setText(first.get(rnds))
        Statistic()
        allDone=false
    }
}