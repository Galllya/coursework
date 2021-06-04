package com.example.myapplication.KollectionPack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater.*
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DeckCreatePack.DC_UsersDBHelper
import com.example.myapplication.DeckCreatePack.KL_UsersDBHelper
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.SeeAllAdapter
import com.example.myapplication.SeeAllPack.OnClickListener
import com.example.myapplication.SeeDeckPack.SeeDeck

class Kollection : AppCompatActivity(),  OnClickListener {
    lateinit var deck_name: TextView
    lateinit var usersDBHelper : DC_UsersDBHelper
    lateinit var kl_usersDBHeiper: KL_UsersDBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usersDBHelper = DC_UsersDBHelper(getApplicationContext())
        kl_usersDBHeiper = KL_UsersDBHelper(getApplicationContext())

        setContentView(R.layout.activity_kollection)
        var a = intent.getStringExtra("tag")
        deck_name = findViewById(R.id.textView5)
        deck_name.text = a.toString()

       /* var kok: String = kl_usersDBHeiper.FindDeck(a.toString())
        val loop = kok.split("+").toTypedArray()

        deck_name.text = loop.get(0)*/

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView2)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SeeAllAdapter(fillList(), this, fillList1())

    }


    private fun fillList(): List<String> {
        var a = intent.getStringExtra("tag")
        var kok: String = kl_usersDBHeiper.FindDeck(a.toString())
        var loop = kok.split("+").toTypedArray()
        val data = mutableListOf<String>()
        var i = 0;
        loop.forEach {

            if (i!=0) data.add(loop[i])
            i++
        }
        return data
    }

    private fun fillList1(): List<String> {
        var a = intent.getStringExtra("tag")
        var kok: String = kl_usersDBHeiper.FindDeck(a.toString())
        var loop = kok.split("+").toTypedArray()
        val data = mutableListOf<String>()
        var i = 0;
        var l:String
        loop.forEach {
            l=loop[i]
            if (i!=0) data.add("Всего карт: "+usersDBHelper.howManyCard(l).toString())
            i++
        }
        return data
    }

    override fun OnClicked(tag: String) {
        super.OnClicked(tag)
        val intent = Intent(this, SeeDeck::class.java)
        intent.putExtra("name", tag)
        startActivity(intent)
    }

    fun PutDeck(view: View) {
        val mDialogView = from(this).inflate(R.layout.prompt, null)
        var dialogLoginBtn = mDialogView.findViewById<Button>(R.id.dialogLoginBtn)
        var dialogNameEt =  mDialogView.findViewById<TextView>(R.id.dialogNameEt)
        var dialogCancelBtn = mDialogView.findViewById<Button>(R.id.dialogCancelBtn)
        val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle(R.string.add_coloda)
        val  mAlertDialog = mBuilder.show()
        dialogLoginBtn.setOnClickListener {
            mAlertDialog.dismiss()
            var tag = dialogNameEt.text.toString()
            var a = intent.getStringExtra("tag")

            var mn = false
            var k = 0
            var user = usersDBHelper.showAll()
            user.forEach {
                if (tag == it.deck_name){
                    mn = true
                }
                }

                if (mn == false){
                    val text = R.string.cant_find_this_deck
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()
                }
            else{
                    var kok: String = kl_usersDBHeiper.FindDeck(a.toString())
                    //deck_name.text = ("+" in kok).toString()
                    if ((tag in kok) == false){
                    var k = kok + "+" + tag
                    kl_usersDBHeiper.AddDeck(a.toString(), k)
                        val recyclerView: RecyclerView = findViewById(R.id.recyclerView2)
                        recyclerView.layoutManager = LinearLayoutManager(this)
                        recyclerView.adapter = SeeAllAdapter(fillList(), this, fillList1())}

                    else{
                        val text = R.string.beee
                        val duration = Toast.LENGTH_SHORT
                        val toast = Toast.makeText(applicationContext, text, duration)
                        toast.show()
                    }
                }
        }

        dialogCancelBtn.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    fun Delete(view: View) {
        var a = intent.getStringExtra("tag")
        kl_usersDBHeiper.deleteNided(a.toString())
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }
}