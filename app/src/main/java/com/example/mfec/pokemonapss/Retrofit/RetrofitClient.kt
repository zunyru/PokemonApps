package com.example.mfec.pokemonapss.Retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var ourInstances:Retrofit?=null
    val instances:Retrofit
    get() {
        if (ourInstances == null)
            ourInstances = Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return ourInstances!!
    }
}