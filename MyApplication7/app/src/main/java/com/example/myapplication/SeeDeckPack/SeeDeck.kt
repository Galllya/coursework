package com.example.myapplication.SeeDeckPack

import MyDialogFragment
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.CardsPack.Cards
import com.example.myapplication.ChoicePack.Choice
import com.example.myapplication.ConnectionPack.Connection
import com.example.myapplication.DailyWorkoutPack.DW_UsersDBHelper
import com.example.myapplication.DeckCreatePack.DC_UsersDBHelper
import com.example.myapplication.DeckCreatePack.MC_UsersDBHelper
import com.example.myapplication.MainActivity
import com.example.myapplication.MemorizationPack.M_UserModel
import com.example.myapplication.MemorizationPack.Memorization
import com.example.myapplication.R
import com.example.myapplication.ShowDeck.Show_Deck


class SeeDeck : AppCompatActivity() {

    lateinit var usersDBHelper : DC_UsersDBHelper
    lateinit var dw_usersDBHelper : DW_UsersDBHelper



    private lateinit var header_name: TextView
    private lateinit var number_card: TextView
    lateinit var m_usersDBHelper : MC_UsersDBHelper
    private lateinit var b4: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_deck)
        m_usersDBHelper = MC_UsersDBHelper(this)

        usersDBHelper = DC_UsersDBHelper(getApplicationContext())
        dw_usersDBHelper = DW_UsersDBHelper(getApplicationContext())


        header_name = findViewById(R.id.textView5)
        b4 = findViewById(R.id.button4)
        var a = intent.getStringExtra("name")
        number_card = findViewById(R.id.textView7)
        header_name.text = a
        number_card.text =  number_card.text.toString() +" " +  usersDBHelper.howManyCard(a.toString()).toString()

         m_usersDBHelper.addMemo(
                       M_UserModel(
                               deck_name = a.toString(),
                               no_study = "",
                               in_process = "",
                               was_study = "",
                               know_it = "",
                               should_go = "0"
                       )
               )



    }

    override fun onResume() {
        super.onResume()
        usersDBHelper = DC_UsersDBHelper(getApplicationContext())

        header_name = findViewById(R.id.textView5)

        var a = intent.getStringExtra("name")
        number_card = findViewById(R.id.textView7)
        header_name.text = a
        number_card.text = "Карт:"
        number_card.text =  number_card.text.toString() +" " +  usersDBHelper.howManyCard(a.toString()).toString()
    }



    fun Open(view: View) {
        val intent = Intent(this, Show_Deck::class.java)
        intent.putExtra("tag", header_name.text.toString())
        startActivity(intent)
    }

    fun ShowDialig(view: View) {
        val myDialogFragment = MyDialogFragment()
        val manager = supportFragmentManager
        myDialogFragment.show(manager, "myDialog")
    }


     fun DeleteThisDeck(){
         m_usersDBHelper.deleteNeded(header_name.text.toString())
         var a = intent.getStringExtra("name")

         var user = usersDBHelper.showDeckItem(a.toString())

         user.forEach{
             dw_usersDBHelper.deleteWithDeck(it.id)
         }
         usersDBHelper.DeleteThisDeck(header_name.text.toString())

         val intent = Intent(this, MainActivity::class.java)
         startActivity(intent)

    }

    fun OpenCards(view: View) {
        val intent = Intent(this, Cards::class.java)
        intent.putExtra("tag",header_name.text.toString())
        startActivity(intent)
    }

    fun MemorizationOpen(view: View) {
        val intent = Intent(this, Memorization::class.java)
        intent.putExtra("tag",header_name.text.toString())
        startActivity(intent)
    }

    fun ConnectionOpen(view: View) {
        var N = usersDBHelper.howManyCard(header_name.text.toString())
        if (N>=7) {
            val intent = Intent(this, Connection::class.java)
            intent.putExtra("tag", header_name.text.toString())
            startActivity(intent)
        }else{
            val text = R.string.little_card
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }

    }

    fun ChooseOpen(view: View) {
        var N = usersDBHelper.howManyCard(header_name.text.toString())
        if (N>4) {
        val intent = Intent(this, Choice::class.java)
        intent.putExtra("tag", header_name.text.toString())
        startActivity(intent)}
        else{
            val text = R.string.little_card
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }
    }


    fun bt_First_1_OnClick(view: View) {}
    fun bt_First_2_OnClick(view: View) {}
    fun bt_First_3_OnClick(view: View) {}
    fun bt_First_4_OnClick(view: View) {}
    fun bt_Second_5_OnClick(view: View) {}
    fun bt_First_6_OnClick(view: View) {}
    fun bt_Second_1_OnClick(view: View) {}
    fun bt_First_5_OnClick(view: View) {}
    fun bt_Second_4_OnClick(view: View) {}
    fun bt_Second_6_OnClick(view: View) {}
    fun bt_Second_2_OnClick(view: View) {}
    fun bt_Second_3_OnClick(view: View) {}

}