package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DailyWorkoutPack.DailyWorkout
import com.example.myapplication.DeckCreatePack.DC_UsersDBHelper
import com.example.myapplication.DeckCreatePack.DeckCreate
import com.example.myapplication.DeckCreatePack.KL_UsersDBHelper
import com.example.myapplication.KollectionPack.KL_UserModel
import com.example.myapplication.KollectionPack.Kollection
import com.example.myapplication.SeeAllPack.OnClickListener
import com.example.myapplication.SeeAllPack.SeeAll
import com.example.myapplication.SeeDeckPack.SeeDeck

class MainActivity : AppCompatActivity(), OnClickListener {
    lateinit var usersDBHelper : DC_UsersDBHelper
    lateinit var kl_usersDBHeiper: KL_UsersDBHelper


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        kl_usersDBHeiper = KL_UsersDBHelper(this)
        usersDBHelper = DC_UsersDBHelper(getApplicationContext())
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView2)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = SeeAllAdapter(fillList(), this, fillList1())


    }
    override fun onResume() {
        super.onResume()
        usersDBHelper = DC_UsersDBHelper(getApplicationContext())
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView2)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = SeeAllAdapter(fillList(), this, fillList1())
    }


    override fun OnClicked(tag: String) {
        super.OnClicked(tag)
        val intent = Intent(this, SeeDeck::class.java)
        intent.putExtra("name", tag)
        startActivity(intent)
    }
    private fun fillList(): List<String> {
        val data = mutableListOf<String>()
        var i = 0;
        val cv = mutableListOf<String>()

        var users = usersDBHelper.findDeckName()
        var tv_user: String = ""

        users.forEach {
            cv.add( tv_user  + it.deck_name.toString())
            data.add(cv[i])
            i++
        }
        return data
    }

    private fun fillList1(): List<String> {
        val data = mutableListOf<String>()
        var i = 0;
        val cv = mutableListOf<String>()

        var users = usersDBHelper.findDeckName()
        var tv_user: String = ""
        var l:String
        users.forEach {
            l=it.deck_name.toString()
            data.add("Всего карт: "+usersDBHelper.howManyCard(l).toString())
            i++
        }
        return data
    }



    fun onClickOpenDeckCreate(view: View) {
        val intent = Intent(this, DeckCreate::class.java)
        startActivity(intent)
    }

    fun All(view: View) {
        val intent = Intent(this, SeeAll::class.java)
        intent.putExtra("name", 1)
        startActivity(intent)
    }

    fun AddNewCard(view: View) {

    }

    fun OpenDailyWorkout(view: View) {
        val intent = Intent(this, DailyWorkout::class.java)
        startActivity(intent)
    }

    fun OpenKollectionDialog(view: View) {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.prompt, null)
        var dialogLoginBtn = mDialogView.findViewById<Button>(R.id.dialogLoginBtn)
        var dialogNameEt =  mDialogView.findViewById<TextView>(R.id.dialogNameEt)
        var dialogCancelBtn = mDialogView.findViewById<Button>(R.id.dialogCancelBtn)
        val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle(R.string.make_collection)
        val  mAlertDialog = mBuilder.show()
       dialogLoginBtn.setOnClickListener {
            mAlertDialog.dismiss()
            var tag = dialogNameEt.text.toString()
           if (tag != ""){
           kl_usersDBHeiper.addDeck(KL_UserModel(
                   kollection_name = tag,
                   deck_names = ""
           ))
           val intent = Intent(this, Kollection::class.java)
           intent.putExtra("tag", tag)
           startActivity(intent) }
           else{
               val text = R.string.emty_kollection
               val duration = Toast.LENGTH_SHORT
               val toast = Toast.makeText(applicationContext, text, duration)
               toast.show()
           }

        }
       dialogCancelBtn.setOnClickListener {
            mAlertDialog.dismiss()
        }

    }

    fun AllKollection(view: View) {
        val intent = Intent(this, com.example.myapplication.KollectionPack.AllKollection::class.java)
        startActivity(intent)
    }

}