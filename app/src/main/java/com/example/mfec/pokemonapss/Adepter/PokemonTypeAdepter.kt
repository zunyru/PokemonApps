package com.example.mfec.pokemonapss.Adepter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mfec.pokemonapss.Common.Common
import com.example.mfec.pokemonapss.Interface.IItemClickListener
import com.example.mfec.pokemonapss.R
import com.robertlevonyan.views.chip.Chip
import kotlinx.android.synthetic.main.chip_item.view.*


class PokemonTypeAdepter(internal var context: Context,internal var typeList: List<String>):
    RecyclerView.Adapter<PokemonTypeAdepter.MyViewHoder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHoder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.chip_item,parent,false)
        return MyViewHoder(itemView)
    }

    override fun getItemCount(): Int {
        return  typeList.size
    }

    override fun onBindViewHolder(holder: MyViewHoder, position: Int) {
        holder.chip.chipText = typeList[position]
        holder.chip.changeBackgroundColor(Common.getColorByType(typeList[position]))
    }


    inner class MyViewHoder(itemView: View):RecyclerView.ViewHolder(itemView){
            internal  var chip : Chip

            init {
                chip = itemView.findViewById(R.id.chip) as Chip
                chip.setOnChipClickListener { Toast.makeText(context,"Click",Toast.LENGTH_LONG).show() }
            }


    }
}

