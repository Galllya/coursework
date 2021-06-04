package com.example.myapplication.ShowDeck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DailyWorkoutPack.DW_UserModel
import com.example.myapplication.DailyWorkoutPack.DW_UsersDBHelper
import com.example.myapplication.DeckCreatePack.DC_UserModel
import com.example.myapplication.DeckCreatePack.DC_UsersDBHelper
import com.example.myapplication.DeckCreatePack.MC_UsersDBHelper
import com.example.myapplication.R
import com.example.myapplication.ShowDeckAdapter
import java.text.SimpleDateFormat
import java.util.*

class Show_Deck : AppCompatActivity(), ClickListener {
    lateinit var usersDBHelper : DC_UsersDBHelper
    lateinit var dw_usersDBHelper : DW_UsersDBHelper
    lateinit var etFirst: EditText
    lateinit var etSecond: EditText
    lateinit var text: TextView
    lateinit var m_usersDBHelper : MC_UsersDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show__deck)
        usersDBHelper = DC_UsersDBHelper(getApplicationContext())
        m_usersDBHelper = MC_UsersDBHelper(getApplicationContext())
        dw_usersDBHelper = DW_UsersDBHelper(getApplicationContext())
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView2)
        recyclerView.layoutManager = LinearLayoutManager(this)
        var a = intent.getStringExtra("tag")
        recyclerView.adapter = ShowDeckAdapter(fillList(a.toString()),this, fillList1(a.toString()), findID(a.toString()))
        etFirst = findViewById(R.id.editTextFirst)
        etSecond = findViewById(R.id.editTextSecond)
    }

    override fun OnClickedDelete(tag: String) {
        super.OnClickedDelete(tag)
        usersDBHelper.deleteNided(tag)
        dw_usersDBHelper.deleteWithDeck(tag)
        var a = intent.getStringExtra("tag")
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView2)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ShowDeckAdapter(fillList(a.toString()),this, fillList1(a.toString()), findID(a.toString()))
        var f = 0
        m_usersDBHelper.UpdateShouldGo(a.toString(), f)

    }

    override fun OnClickedChenge(tag: String) {
        super.OnClickedChenge(tag)
        if ((etFirst.getText().toString() != "")&&(etSecond.getText().toString() != "")) {
            var a = intent.getStringExtra("tag")
            var f = 0
            m_usersDBHelper.UpdateShouldGo(a.toString(), f)
            //при изменинии попадание в ежедневную тренировку НЕ изменяется
            usersDBHelper.ChanceDeckEliment(tag, etFirst.getText().toString(), etSecond.getText().toString())
            etFirst.setText("")
            etSecond.setText("")
            val recyclerView: RecyclerView = findViewById(R.id.recyclerView2)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = ShowDeckAdapter(fillList(a.toString()), this, fillList1(a.toString()), findID(a.toString()))
        }
        else {
            val text = R.string.alart_write_top
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }
    }

    private fun fillList(a: String): List<String> {
        val data = mutableListOf<String>()
        var i = 0;
        val cv = mutableListOf<String>()

        var users = usersDBHelper.showDeckItem(a)
        var tv_user: String = ""

        users.forEach {
            cv.add( tv_user  + it.first_site.toString())
            data.add(cv[i])
            i++
        }
        return data
    }

    private fun fillList1(a: String): List<String> {
        val data = mutableListOf<String>()
        var i = 0;
        val cv = mutableListOf<String>()

        var users = usersDBHelper.showDeckItem(a)
        var tv_user: String = ""

        users.forEach {
            cv.add( tv_user   + it.second_site.toString())
            data.add(cv[i])
            i++
        }
        return data
    }

    private fun findID(a: String): List<String> {
        val data = mutableListOf<String>()
        var i = 0;
        val cv = mutableListOf<String>()

        var users = usersDBHelper.showDeckItem(a)
        var tv_user: String = ""

        users.forEach {
            cv.add( tv_user   + it.id.toString())
            data.add(cv[i])
            i++
        }
        return data

    }

    fun AddNewCard(view: View){

        if ((etFirst.getText().toString() != "")&&(etSecond.getText().toString() != "")) {
            var a = intent.getStringExtra("tag")
            var f = 0
            m_usersDBHelper.UpdateShouldGo(a.toString(), f)
            var first_site_tx = etFirst.getText().toString()
            var second_site_tx = etSecond.getText().toString()
            var id = usersDBHelper.maxID().toInt() + 1
            var result = usersDBHelper.addCard(
                    DC_UserModel(
                            id = id.toString(),
                            first_site = first_site_tx,
                            second_site = second_site_tx,
                            deck_name = a.toString()
                    )
            )
            val currentDate = Date()

            val sdf = SimpleDateFormat("dd/MM/yyyy")

            val formattedDate: String = sdf.format(currentDate)
            dw_usersDBHelper.addCard(
                    DW_UserModel(
                            id = id.toString(),
                            box = "1",
                            first_day = formattedDate,
                            last_day = formattedDate
                    )
            )
            etFirst.setText("")
            etSecond.setText("")
            val recyclerView: RecyclerView = findViewById(R.id.recyclerView2)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = ShowDeckAdapter(fillList(a.toString()), this, fillList1(a.toString()), findID(a.toString()))
        }
        else{
            val text = R.string.alert_no_free_card
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }



    }
}