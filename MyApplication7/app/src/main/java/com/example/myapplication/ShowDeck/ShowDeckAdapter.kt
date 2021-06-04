package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DeckCreatePack.DC_UsersDBHelper
import com.example.myapplication.SeeAllPack.OnClickListener
import com.example.myapplication.ShowDeck.ClickListener


class ShowDeckAdapter(private val names: List<String>, private val ClickListener: ClickListener, private val names1: List<String>, private val namesOfID: List<String>) :
    RecyclerView.Adapter<ShowDeckAdapter.MyViewHolder>() {
    lateinit var usersDBHelper : DC_UsersDBHelper

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var largeTextView: TextView? = null
        var smallTextView: TextView? = null
        var imDelete: ImageView? = null
        var imChange: ImageView? = null

        init {

            largeTextView = itemView.findViewById(R.id.textViewLarge)
            smallTextView = itemView.findViewById(R.id.textViewSmall)
            imDelete = itemView.findViewById(R.id.imageViewDelete)
            imChange= itemView.findViewById(R.id.imageViewChange)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.deck_show_item, parent, false)
        return MyViewHolder(itemView)    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.largeTextView?.text = names[position]
        holder.smallTextView?.text = names1[position]
        holder.imDelete?.setOnClickListener{
            ClickListener.OnClickedDelete(namesOfID[position])
        }
        holder.imChange?.setOnClickListener{
            ClickListener.OnClickedChenge(namesOfID[position])
        }
    }


    override fun getItemCount(): Int {
        return names.size
    }
}
