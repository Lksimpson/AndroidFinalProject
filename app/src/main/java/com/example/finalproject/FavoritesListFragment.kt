package com.example.finalproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "FavoritesListFragment"

class FavoritesListFragment : Fragment(){

    private var callbacks: TrendingFragment.Callbacks? = null
    private lateinit var favoritesRecyclerView: RecyclerView
    private var adapter: MovieAdapter? = MovieAdapter(emptyList())
    private val favoritesListViewModel: MoviesAppViewModel by lazy {
        ViewModelProviders.of(this).get(MoviesAppViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as TrendingFragment.Callbacks?
    }

    private lateinit var  favoritesBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.favorites_page, container, false)
        favoritesRecyclerView =
            view.findViewById(R.id.movieList) as RecyclerView
        favoritesRecyclerView.layoutManager = LinearLayoutManager(context)

        favoritesRecyclerView.adapter = adapter

        favoritesBtn = view.findViewById(R.id.movie_list_btn)
        favoritesBtn.setOnClickListener {

            callbacks?.onTrendingSelected()

        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        favoritesListViewModel.gameListLiveData.observe(
//            viewLifecycleOwner,
//            Observer { movies ->
//                movies?.let {
//                    Log.i(TAG, "Got games ${movies.size}")
//                    updateUI(movies)
//                }
//            })
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private inner class MovieHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var movie: MovieItem

        private val titleTextView: TextView = itemView.findViewById(R.id.movie_title)



       // var teamLogoImageView: ImageView = itemView.findViewById(R.id.team_logo)
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: MovieItem) {
            this.movie = movie
            titleTextView.text = movie.title
//            dateTextView.text = game.date.toString()
//            scoreTextView.text = game.teamAScore.toString()+":"+game.teamBScore.toString()
//            //conditionally switch the image drawable to display winning team logo
//            if (game.teamAScore > game.teamBScore){
//                teamLogoImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_teama))
//            } else {
//                teamLogoImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_teamb))
//            }
        }

        override fun onClick(v: View?) {
            Toast.makeText(context, "${movie.tmdb_id} clicked!", Toast.LENGTH_SHORT).show()
            callbacks?.onSelectFavoriteSelected()
        }
    }

    private inner class MovieAdapter(var movies: List<MovieItem>)
        : RecyclerView.Adapter<MovieHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
            val layoutInflater = LayoutInflater.from(context)

            val view = layoutInflater.inflate(R.layout.list_item_movie, parent, false)
            return MovieHolder(view)
        }

        override fun getItemCount() = movies.size

        override fun onBindViewHolder(holder: MovieHolder, position: Int) {
            val movie = movies[position]
            holder.bind(movie)
        }
    }

    private fun updateUI(movies: List<MovieItem>) {
        adapter = MovieAdapter(movies)
        favoritesRecyclerView.adapter = adapter
    }
}