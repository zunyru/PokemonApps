package com.example.mfec.pokemonapss.Retrofit

import com.example.mfec.pokemonapss.Model.Pokedex
import io.reactivex.Observable
import retrofit2.http.GET

interface IPokemonList {

    @get:GET("pokedex.json")
    val listPokemon:Observable<Pokedex>
}