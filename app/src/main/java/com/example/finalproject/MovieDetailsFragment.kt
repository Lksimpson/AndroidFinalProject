package com.example.finalproject

import android.content.Context
import android.graphics.Movie
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "MovieDetailsFragment"


private const val ARG_DB_ID = "db_id"
private const val ARG_TITLE = "movie_title"
private const val ARG_OVERVIEW = "movie_overview"
private const val ARG_POSTERPATH = "movie_posterpath"
private const val ARG_RATING = "movie_rating"

class MovieDetailsFragment : Fragment(){

    private var callbacks: TrendingFragment.Callbacks? = null

    private lateinit var movie: MovieItem
    private lateinit var titleField: TextView
    private lateinit var genreField: TextView
    private lateinit var directorField: TextView
    private lateinit var otherField: TextView
    private lateinit var trendingButton: Button
    private lateinit var favoritesButton: Button
    private lateinit var photoView: ImageView
    //private lateinit var  favoritesBtn: Button

    private val favoritesListViewModel: MoviesAppViewModel by lazy {
        ViewModelProviders.of(this).get(MoviesAppViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as TrendingFragment.Callbacks?
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieId: UUID = arguments?.getSerializable(ARG_DB_ID) as UUID
        val title: String = arguments?.getSerializable(ARG_TITLE) as String
        val overview: String = arguments?.getSerializable(ARG_OVERVIEW) as String
        val rating: String = arguments?.getSerializable(ARG_RATING) as String
        val posterpath: String = arguments?.getSerializable(ARG_POSTERPATH) as String
        favoritesListViewModel.loadMovie(movieId,title,overview,rating,posterpath)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        favoritesListViewModel.favoritesListLiveData.observe(
            viewLifecycleOwner,
            Observer { favorites ->
                favorites?.let {
                    Log.i(TAG, "Got favorites ${favorites.size}")
                    updateUI(favorites)
                }
            })

        val view = inflater.inflate(R.layout.movie_details, container, false)
        titleField = view.findViewById(R.id.movie_title) as TextView
        genreField = view.findViewById(R.id.description) as TextView
//        directorField = view.findViewById(R.id.director) as TextView
        otherField = view.findViewById(R.id.rating) as TextView

        favoritesButton = view.findViewById(R.id.add_to_fav_btn)


        //check if movie is in favorites list
//        var flag = 0
//        for (i in favoritesListViewModel.favoritesListLiveData.value!!) {
//            if (i.db_id == favoritesListViewModel.movieLiveData.value?.db_id)
//                flag = 1
//        }
//
//        if (flag == 0)//not in list, add
//        {
//
//        }
//        else//in list, update
//        {
//            favoritesButton.visibility = View.GONE
//
//        }


        favoritesButton.setOnClickListener {
            // check if movie is in favorites list
            var flag = 0
            for (i in favoritesListViewModel.favoritesListLiveData.value!!) {
                if (i.db_id == favoritesListViewModel.movieLiveData.value?.db_id)
                    flag = 1
            }
            if (flag == 0)//not in list, add
                favoritesListViewModel.movieLiveData.value?.let { it1 ->
                    favoritesListViewModel.addFavorite(
                        it1
                    )
                }
            else//in list, update
                favoritesListViewModel.movieLiveData.value?.let { it1 ->
                    favoritesListViewModel.updateFavorite(
                        it1
                    )
                }
        }
//        favoritesRecyclerView =
//            view.findViewById(R.id.movieList) as RecyclerView
//        favoritesRecyclerView.layoutManager = LinearLayoutManager(context)
//
//        favoritesRecyclerView.adapter = adapter
//
//        favoritesBtn = view.findViewById(R.id.movie_list_btn)
//        favoritesBtn.setOnClickListener {
//
//            callbacks?.onTrendingSelected()
//
//        }
        return view
    }

    //favoritesBtn.visibility = View.GONE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = arguments?.getSerializable(ARG_DB_ID) as UUID
        val title = arguments?.getSerializable(ARG_TITLE) as String
        val overview = arguments?.getSerializable(ARG_OVERVIEW) as String
        val rating = arguments?.getSerializable(ARG_RATING) as String
        val posterpath = arguments?.getSerializable(ARG_POSTERPATH) as String
        favoritesListViewModel.loadMovie(movieId,title,overview,rating,posterpath)
        favoritesListViewModel.movieItemLiveData.observe(
            viewLifecycleOwner,
            Observer { movie ->
                movie?.let {
                    this.movie = movie[0]
                    titleField.text = title
                    genreField.text = overview
                    otherField.text = rating
//                    directorField.text = posterpath
                   // updateUI()
                    var flag = 0
                    for (i in favoritesListViewModel.favoritesListLiveData.value!!) {
                        if (i.db_id == favoritesListViewModel.movieLiveData.value?.db_id)
                            flag = 1
                    }

                    if (flag == 0)//not in list, add
                    {

                    }
                    else//in list, update
                    {
                        favoritesButton.visibility = View.GONE

                    }
                }
            })
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

//    private inner class MovieHolder(view: View)
//        : RecyclerView.ViewHolder(view), View.OnClickListener {
//
//        private lateinit var movie: MovieItem
//
//        private val titleTextView: TextView = itemView.findViewById(R.id.movie_title)
//
//
//
//        // var teamLogoImageView: ImageView = itemView.findViewById(R.id.team_logo)
//        init {
//            itemView.setOnClickListener(this)
//        }
//
//        fun bind(movie: MovieItem) {
//            this.movie = movie
//            titleTextView.text = movie.title
////            dateTextView.text = game.date.toString()
////            scoreTextView.text = game.teamAScore.toString()+":"+game.teamBScore.toString()
////            //conditionally switch the image drawable to display winning team logo
////            if (game.teamAScore > game.teamBScore){
////                teamLogoImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_teama))
////            } else {
////                teamLogoImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_teamb))
////            }
//        }
//
//        override fun onClick(v: View?) {
//            Toast.makeText(context, "${movie.tmdb_id} clicked!", Toast.LENGTH_SHORT).show()
//            callbacks?.onSelectFavoriteSelected()
//        }
//    }
//
//    private inner class MovieAdapter(var movies: List<MovieItem>)
//        : RecyclerView.Adapter<MovieHolder>() {
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
//            val layoutInflater = LayoutInflater.from(context)
//
//            val view = layoutInflater.inflate(R.layout.list_item_movie, parent, false)
//            return MovieHolder(view)
//        }
//
//        override fun getItemCount() = movies.size
//
//        override fun onBindViewHolder(holder: MovieHolder, position: Int) {
//            val movie = movies[position]
//            holder.bind(movie)
//        }
//    }

    private fun updateUI(movies: List<MovieItem>) {
//        adapter = MovieAdapter(movies)
//        favoritesRecyclerView.adapter = adapter
    }

    fun newInstance(movie: MovieItem): Fragment {
        val args = Bundle().apply {
            putSerializable(ARG_DB_ID, movie.db_id)
            putSerializable(ARG_TITLE, movie.title)
            putSerializable(ARG_OVERVIEW, movie.overview)
            putSerializable(ARG_RATING, movie.vote_average.toString())
            putSerializable(ARG_POSTERPATH, movie.poster_path)
        }
        return MovieDetailsFragment().apply {
            arguments = args
        }
    }
}