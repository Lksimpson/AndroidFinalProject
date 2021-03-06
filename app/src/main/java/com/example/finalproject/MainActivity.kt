package com.example.finalproject

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(),
    TrendingFragment.Callbacks{

    companion object {
        const val TABLE_NAME: String="table_favorites"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            val fragment = TrendingFragment.newInstance()
            //val fragment = GameListFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }

    }
    override fun onFavoritesSelected() {
        Log.d(TAG, "MainActivity.onFavoritesSelected: ")
        val fragment = FavoritesListFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onTrendingSelected() {
        Log.d(TAG, "MainActivity.onTrendingSelected:" )
        val fragment = TrendingFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onMovieSelected(movie: MovieItem) {
        Log.d(TAG, "MainActivity.onMovieSelected:" )
        val fragment = MovieDetailsFragment().newInstance(movie)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}