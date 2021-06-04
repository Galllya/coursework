package com.example.myapplication.KollectionPack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DeckCreatePack.KL_UsersDBHelper
import com.example.myapplication.R
import com.example.myapplication.SeeAllAdapter
import com.example.myapplication.SeeAllPack.OnClickListener

class AllKollection : AppCompatActivity(), OnClickListener {
    lateinit var kl_usersDBHeiper: KL_UsersDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_kollection)
        kl_usersDBHeiper = KL_UsersDBHelper(getApplicationContext())

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView2)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SeeAllAdapter(fillList(), this, fillList1())
    }

    override fun OnClicked(tag: String) {
        super.OnClicked(tag)
        val intent = Intent(this, Kollection::class.java)
        intent.putExtra("tag", tag)
        startActivity(intent)
    }

    private fun fillList(): List<String> {
        val data = mutableListOf<String>()
        var i = 0;
        val cv = mutableListOf<String>()

        var users = kl_usersDBHeiper.showAll()
        var tv_user: String = ""

        users.forEach {
            cv.add( tv_user  + it.kollection_name.toString())
            data.add(cv[i])
            i++
        }
        return data
    }

    private fun fillList1(): List<String> {
        val data = mutableListOf<String>()
        var i = 0;
        val cv = mutableListOf<String>()

        var users = kl_usersDBHeiper.showAll()
        var tv_user: String = ""
        var l:String
        users.forEach {
            data.add("0")
            i++
        }
        return data
    }
}