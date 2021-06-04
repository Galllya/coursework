package com.example.myapplication.DeckCreatePack

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.DailyWorkoutPack.DW_UserModel
import com.example.myapplication.DailyWorkoutPack.DW_UsersDBHelper
import com.example.myapplication.R
import com.example.myapplication.SeeDeckPack.SeeDeck
import java.text.SimpleDateFormat
import java.util.*

class DeckCreate : AppCompatActivity() {
    lateinit var dc_usersDBHelper : DC_UsersDBHelper
    lateinit var dw_usersDBHelper : DW_UsersDBHelper
    lateinit var m_usersDBHeiper: MC_UsersDBHelper
    lateinit var kl_usersDBHeiper: KL_UsersDBHelper


    private lateinit var deck_name: EditText
    private lateinit var first_site: EditText
    private lateinit var second_site: EditText
    private lateinit var b12: Button
    var id:Int = 0
    var firsSiteList = mutableListOf<String>()
    var secondSiteList = mutableListOf<String>()
    var shouldDoIt = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck_create2)
        dc_usersDBHelper = DC_UsersDBHelper(this)
        dw_usersDBHelper = DW_UsersDBHelper(this)
        m_usersDBHeiper =  MC_UsersDBHelper(getApplicationContext())
        kl_usersDBHeiper = KL_UsersDBHelper(getApplicationContext())

        deck_name = findViewById(R.id.et_deck_name)
        first_site = findViewById(R.id.ed_first_site)
        second_site = findViewById(R.id.ed_second_side)

        if (dc_usersDBHelper.maxID() == "null") {id = 0}
        else {id = (dc_usersDBHelper.maxID()).toInt()+1}

    }



    fun AddCard(view: View) {
        if ((first_site.getText().toString() != "")&&(second_site.getText().toString() != "")) {
            firsSiteList.add(first_site.text.toString())
            secondSiteList.add(second_site.text.toString())

            first_site.setText("")
            second_site.setText("")
            shouldDoIt = 1
        }
        else{
            val text = R.string.alert_no_free_card
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }
    }



    fun Undo(view: View) {
        var id_text = id
        val result = dc_usersDBHelper.undo(id_text.toString())
        id = 0
        dw_usersDBHelper.undo()
        m_usersDBHeiper.deleteAll()
        kl_usersDBHeiper.undo()
    }



    fun MakeMagic(view: View) {

        if (deck_name.text.toString() == "") {
            val text = "Вы не ввели название свой колоды!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()

        } else {
            if(shouldDoIt != 0) {
                var a: Array<String?> = arrayOfNulls(firsSiteList.size)
                var b: Array<String?> = arrayOfNulls(firsSiteList.size)
                var i = 0
                firsSiteList.forEach {
                    a[i] = it
                    i++
                }
                i = 0
                secondSiteList.forEach {
                    b[i] = it
                    i++
                }


                var deck_name = deck_name.text.toString()


                (0..firsSiteList.size - 1).forEach {
                    var first_site_tx = a[it].toString()
                    var second_site_tx = b[it].toString()
                    var id_tx = id.toString()
                    var result = dc_usersDBHelper.addCard(
                            DC_UserModel(
                                    id = id_tx,
                                    first_site = first_site_tx,
                                    second_site = second_site_tx,
                                    deck_name = deck_name
                            )
                    )

                    val currentDate = Date()

                    val sdf = SimpleDateFormat("dd/MM/yyyy")

                    val formattedDate: String = sdf.format(currentDate)

                    dw_usersDBHelper.addCard(
                            DW_UserModel(
                                    id = id_tx,
                                    box = "1",
                                    first_day = formattedDate,
                                    last_day = formattedDate
                            )
                    )


                    id++
                }


                val intent = Intent(this, SeeDeck::class.java)
                intent.putExtra("name", deck_name)
                startActivity(intent)
                this.finish()
            }
            else{
                val text = R.string.alert_free_deck
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }


        }
    }

    /*fun b12(view: View) {
        m_usersDBHelper.undo()
    }*/

}