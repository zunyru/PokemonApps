package com.example.mfec.pokemonapss

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.content.LocalBroadcastManager
import android.view.MenuItem
import com.example.mfec.pokemonapss.Common.Common
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val showDetail = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {

                if(intent!!.action!!.toString() == Common.KEY_ENABLE_HOME){
                      supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                      supportActionBar!!.setDisplayShowHomeEnabled(true)

                    //Replace Fragment
                    val detailFragment = PokemonDetail.getInstance()
                    val num = intent.getStringExtra("num")
                    val bundle = Bundle()
                    bundle.putString("num",num)
                    detailFragment.arguments = bundle

                    val fragmentTransection = supportFragmentManager.beginTransaction()
                    fragmentTransection.replace(R.id.list_pokemon_fragment,detailFragment)
                    fragmentTransection.addToBackStack("detail")
                    fragmentTransection.commit()

                    //
                    val pokemon = Common.findPokemonByNum(num)
                    toobar.title = pokemon!!.name
                }

        }

    }

    private val showEvolution = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {

            if(intent!!.action!!.toString() == Common.KEY_NUM_EVOLUTION){

                //Replace Fragment
                val detailFragment = PokemonDetail.getInstance()
                val bundle = Bundle()
                val num = intent.getStringExtra("num")
                bundle.putString("num",num)
                detailFragment.arguments = bundle

                val fragmentTransection = supportFragmentManager.beginTransaction()
                fragmentTransection.remove(detailFragment)
                fragmentTransection.replace(R.id.list_pokemon_fragment,detailFragment)
                fragmentTransection.addToBackStack("detail")
                fragmentTransection.commit()


                //
                val pokemon = Common.findPokemonByNum(num)
                toobar.title = pokemon!!.name
            }

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toobar.setTitle("POKEMON YRU")
        setSupportActionBar(toobar)

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(showDetail, IntentFilter(Common.KEY_ENABLE_HOME))

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(showEvolution, IntentFilter(Common.KEY_NUM_EVOLUTION))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){
            android.R.id.home ->{
                toobar.title = "POKEMON YRU"

                supportFragmentManager.popBackStack("detail",FragmentManager.POP_BACK_STACK_INCLUSIVE)

                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setDisplayShowHomeEnabled(true)
            }
        }

        return true
    }
}
