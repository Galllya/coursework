package com.example.myapplication.SeeAllPack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DeckCreatePack.DC_UsersDBHelper
import com.example.myapplication.R
import com.example.myapplication.SeeAllAdapter
import com.example.myapplication.SeeDeckPack.SeeDeck

class SeeAll : AppCompatActivity(), OnClickListener {
    lateinit var usersDBHelper : DC_UsersDBHelper
    lateinit var text: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all)
        text = findViewById(R.id.textView5)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView2)
        usersDBHelper = DC_UsersDBHelper(getApplicationContext())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SeeAllAdapter(fillList(), this, fillList1())


    }


        override fun OnClicked(tag: String) {
            super.OnClicked(tag)
            val intent = Intent(this, SeeDeck::class.java)
            intent.putExtra("name", tag)
            startActivity(intent)
        }

    override fun onResume() {
        super.onResume()
        text = findViewById(R.id.textView5)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView2)
        usersDBHelper = DC_UsersDBHelper(getApplicationContext())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SeeAllAdapter(fillList(), this, fillList1())
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
}