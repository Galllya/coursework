package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DeckCreatePack.DC_UsersDBHelper
import com.example.myapplication.SeeAllPack.OnClickListener


class SeeAllAdapter(private val names: List<String>, private val OnClickListener: OnClickListener, private val names1: List<String>) :
    RecyclerView.Adapter<SeeAllAdapter.MyViewHolder>() {
    lateinit var usersDBHelper : DC_UsersDBHelper


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var largeTextView: TextView? = null
        var smallTextView: TextView? = null


        init {

            largeTextView = itemView.findViewById(R.id.textViewLarge)
            smallTextView = itemView.findViewById(R.id.textViewSmall)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.see_all_item, parent, false)
        return MyViewHolder(itemView)    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.largeTextView?.text = names[position]
        holder.smallTextView?.text = names1[position]
        holder.largeTextView?.setOnClickListener{
            OnClickListener.OnClicked(names[position])
        }
    }

    override fun getItemCount(): Int {
        return names.size
    }



}
