package com.example.mfec.pokemonapss.Adepter

import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.mfec.pokemonapss.Common.Common

import com.example.mfec.pokemonapss.Interface.IItemClickListener
import com.example.mfec.pokemonapss.Model.Pokemon

import com.example.mfec.pokemonapss.R


class PokemonListAdepter(internal var context: Context,
                         internal var pokemonList:List<Pokemon>):RecyclerView.Adapter<PokemonListAdepter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.pokemon_list_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return  pokemonList.size
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        Glide.with(context).load(pokemonList[position].img).apply(
            RequestOptions()
            .placeholder(R.drawable.placeholder)
                //.override(600, 200)
            //.centerCrop()
           // .transforms(CenterCrop())
            )
            .into(holder.img_pokemon)

        holder.text_pokemon.text = pokemonList[position].name


        holder.setItemClickListener(object : IItemClickListener{
            override fun onClick(view: View, position: Int) {
                //Toast.makeText(context,"data "+pokemonList[position],Toast.LENGTH_LONG).show()
                LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(Intent(Common.KEY_ENABLE_HOME).putExtra("num",pokemonList[position].num))
            }
        })


    }

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        internal var img_pokemon:ImageView
        internal var text_pokemon:TextView

        //face 2
        internal var itemClickListener:IItemClickListener?=null

        fun setItemClickListener(iItemClickListener: IItemClickListener){
            this.itemClickListener = iItemClickListener
        }

        init {
            img_pokemon = itemView.findViewById(R.id.pokemon_image) as ImageView
            text_pokemon = itemView.findViewById(R.id.pokemon_name) as TextView


            itemView.setOnClickListener { view->itemClickListener!!.onClick(view,adapterPosition) }

        }

    }
}