package com.example.mfec.pokemonapss


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.mfec.pokemonapss.Adepter.PokemonEvolutionAdepter
import com.example.mfec.pokemonapss.Adepter.PokemonTypeAdepter
import com.example.mfec.pokemonapss.Common.Common
import com.example.mfec.pokemonapss.Model.Pokemon


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PokemonDetail : Fragment() {

    internal lateinit var pokemon_img:ImageView

    internal lateinit var pokemon_name:TextView
    internal lateinit var pokemon_heigth:TextView
    internal lateinit var pokemon_weigth:TextView

    lateinit var recycler_type:RecyclerView
    lateinit var recycler_weakness:RecyclerView
    lateinit var recycler_prev_evolution:RecyclerView
    lateinit var recycler_next_evolution:RecyclerView


    companion object {
        internal var instance:PokemonDetail?=null
        fun getInstance():PokemonDetail{
            if(instance == null)
                instance = PokemonDetail()
            return instance!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var itemView = inflater.inflate(R.layout.fragment_pokemon_detail, container, false)

        val pokemon = Common.findPokemonByNum(arguments!!.getString("num"))

        pokemon_img = itemView.findViewById(R.id.pokemon_image) as ImageView

        pokemon_name = itemView.findViewById(R.id.name) as TextView
        pokemon_heigth = itemView.findViewById(R.id.heigth) as TextView
        pokemon_weigth = itemView.findViewById(R.id.weigth) as TextView

        recycler_next_evolution = itemView.findViewById(R.id.recyclerview_next_evolution) as RecyclerView
        recycler_next_evolution.setHasFixedSize(true)
        recycler_next_evolution.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

        recycler_prev_evolution = itemView.findViewById(R.id.recyclerview_prev_evolution) as RecyclerView
        recycler_prev_evolution.setHasFixedSize(true)
        recycler_prev_evolution.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

        recycler_type = itemView.findViewById(R.id.recyclerview_type) as RecyclerView
        recycler_type.setHasFixedSize(true)
        recycler_type.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

        recycler_weakness = itemView.findViewById(R.id.recyclerview_weakness) as RecyclerView
        recycler_weakness.setHasFixedSize(true)
        recycler_weakness.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

        setDetailPokemon(pokemon)

        return itemView
    }

    private fun setDetailPokemon(pokemon: Pokemon?) {

        Glide.with(activity!!).load(pokemon!!.img).into(pokemon_img)

        pokemon_name.text = pokemon.name
        pokemon_heigth.text = "Height : " + pokemon.height
        pokemon_weigth.text = "Weight : " + pokemon.weight

        val typeAdapter = PokemonTypeAdepter(activity!!, pokemon.type!!)
        recycler_type.adapter = typeAdapter

        val weaknessAdapter = PokemonTypeAdepter(activity!!, pokemon.weaknesses!!)
        recycler_weakness.adapter = weaknessAdapter

        if (pokemon.prev_evolution != null) {
            val preEvolution = PokemonEvolutionAdepter(activity!!, pokemon.prev_evolution!!)
            recycler_prev_evolution.adapter = preEvolution
        }

        if (pokemon.next_evolution != null) {
            val nextEvolution = PokemonEvolutionAdepter(activity!!, pokemon.next_evolution!!)
            recycler_next_evolution.adapter = nextEvolution
        }

    }

}
