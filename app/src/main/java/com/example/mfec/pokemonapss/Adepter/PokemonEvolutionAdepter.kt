package com.example.mfec.pokemonapss.Adepter

import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mfec.pokemonapss.Common.Common
import com.example.mfec.pokemonapss.Model.Evolution
import com.example.mfec.pokemonapss.R
import com.example.mfec.pokemonapss.R.id.chip
import com.robertlevonyan.views.chip.Chip

internal class PokemonEvolutionAdepter(internal var context: Context, var evolutionList: List<Evolution>?):
RecyclerView.Adapter<PokemonEvolutionAdepter.MyViewHoder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHoder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.chip_item,parent,false)
        return MyViewHoder(itemView)
    }

    override fun getItemCount(): Int {
        return  evolutionList!!.size
    }

    override fun onBindViewHolder(holder: MyViewHoder, position: Int) {
        holder.chip.chipText = evolutionList!![position].num
        holder.chip.changeBackgroundColor(Common.getColorByType(Common.findPokemonByNum(evolutionList!![position].num!!)!!.type!![0]))
    }

    init {
        if (evolutionList == null)
            evolutionList = ArrayList()
    }

    inner class MyViewHoder(itemView: View):RecyclerView.ViewHolder(itemView){
        internal  var chip : Chip

        init {
            chip = itemView.findViewById(R.id.chip) as Chip
            chip.setOnChipClickListener {

                LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(Intent(Common.KEY_NUM_EVOLUTION).putExtra("num", evolutionList!![adapterPosition].num))
            }
        }


    }
}