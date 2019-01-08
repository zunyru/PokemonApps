package com.example.mfec.pokemonapss


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mfec.pokemonapss.Adepter.PokemonListAdepter
import com.example.mfec.pokemonapss.Common.Common
import com.example.mfec.pokemonapss.Common.ItemOffsetDecoration
import com.example.mfec.pokemonapss.Model.Pokemon
import com.example.mfec.pokemonapss.Retrofit.IPokemonList
import com.example.mfec.pokemonapss.Retrofit.RetrofitClient
import com.mancj.materialsearchbar.MaterialSearchBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_pokemon_list.*



class PokemonList : Fragment() {

    internal var compositesisposable = CompositeDisposable()
    internal var iPokemonList:IPokemonList

    internal lateinit var recycler_view:RecyclerView

    internal lateinit var adepter:PokemonListAdepter

    //search
    internal lateinit var search_adepter:PokemonListAdepter
    internal var last_suggest:MutableList<String> = ArrayList()

    internal lateinit var search_bar:MaterialSearchBar


    init {
        val retrofit = RetrofitClient.instances
        iPokemonList = retrofit.create(IPokemonList::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val itemView =  inflater.inflate(R.layout.fragment_pokemon_list, container, false)


        recycler_view = itemView.findViewById(R.id.pokemon_recyclerview) as RecyclerView

        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = GridLayoutManager(activity,2)
        val itemDecoration = ItemOffsetDecoration(activity!!,R.dimen.spacing)
        recycler_view.addItemDecoration(itemDecoration)


        //set up searchbar
        search_bar = itemView.findViewById(R.id.search_bar) as MaterialSearchBar
        search_bar.setHint("Search Pokemon Name")
        search_bar.setCardViewElevation(10)
        search_bar.addTextChangeListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val suggest = ArrayList<String>()
                for (search in last_suggest)
                    if(search.toLowerCase().contains(search_bar.text.toLowerCase()))
                        suggest.add(search)
                search_bar.lastSuggestions = suggest

            }
        })
        search_bar.setOnSearchActionListener(object :MaterialSearchBar.OnSearchActionListener{
            override fun onButtonClicked(buttonCode: Int) {

            }

            override fun onSearchStateChanged(enabled: Boolean) {
                if(!enabled)
                    pokemon_recyclerview.adapter = adepter
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                startSearch(text.toString())
            }
        })


        fetchData()

        return  itemView
    }

    private fun startSearch(text: String) {
          if(Common.pokemonList.size > 0){
              val result = ArrayList<Pokemon>()
              for (pokemon in Common.pokemonList)
                  if(pokemon.name!!.toLowerCase().contains(text.toLowerCase()))
                      result.add(pokemon)
              search_adepter = PokemonListAdepter(activity!!,result)
              pokemon_recyclerview.adapter = search_adepter
          }

    }

    private fun fetchData() {
        compositesisposable.add(iPokemonList.listPokemon
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{pokemonDex ->
                Common.pokemonList = pokemonDex.pokemon!!
                adepter =  PokemonListAdepter(activity!!,Common.pokemonList)

                recycler_view.adapter = adepter

                //search
                last_suggest.clear()
                for (pokemon in Common.pokemonList)
                    last_suggest.add(pokemon.name!!)
                search_bar.visibility = View.VISIBLE
                search_bar.lastSuggestions = last_suggest

            }
        )
    }


}
