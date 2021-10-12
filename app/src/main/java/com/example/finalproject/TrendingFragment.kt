package com.example.finalproject

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.KeyEvent
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import android.view.inputmethod.EditorInfo

import android.widget.TextView

import android.widget.TextView.OnEditorActionListener





private const val TAG = "TrendingFragment"

class TrendingFragment : Fragment() {

    interface Callbacks {
        //fun onFavoritesSelected(gameId: UUID)
        fun onFavoritesSelected()
        fun onTrendingSelected()
        fun onMovieSelected(movie: MovieItem)
      //  abstract fun doMySearch(query: String)
    }

    private var callbacks: Callbacks? = null
    //private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var fullListRecyclerView: RecyclerView
    private lateinit var thumbnailDownloader: ThumbnailDownloader<MovieHolder>
    private var adapter: TrendingFragment.MovieAdapter? = MovieAdapter(emptyList())
    private val favoritesListViewModel: MoviesAppViewModel by lazy {
        ViewModelProviders.of(this).get(MoviesAppViewModel::class.java)
    }

    companion object {
        fun newInstance(): TrendingFragment {
            return TrendingFragment()
        }
    }

    private lateinit var  favoritesBtn: Button
    private lateinit var  searchBtn: Button
    private lateinit var searchBar: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        val responseHandler = Handler()
        thumbnailDownloader =
            ThumbnailDownloader(responseHandler) { photoHolder, bitmap ->
                val drawable = BitmapDrawable(resources, bitmap)
                photoHolder.bindDrawable(drawable)
            }
        lifecycle.addObserver(thumbnailDownloader.fragmentLifecycleObserver)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLifecycleOwner.lifecycle.addObserver(
            thumbnailDownloader.viewLifecycleObserver
        )

        val view = inflater.inflate(R.layout.homepage, container, false)

        fullListRecyclerView =
            view.findViewById(R.id.movieList) as RecyclerView
        fullListRecyclerView.layoutManager = LinearLayoutManager(context)

        fullListRecyclerView.adapter = adapter

        favoritesBtn = view.findViewById(R.id.favorites_btn)
        favoritesBtn.setOnClickListener {
            callbacks?.onFavoritesSelected()
        }
        searchBtn = view.findViewById(R.id.search_btn)
        searchBtn.setOnClickListener {
            Log.i(TAG, "search bar text")
            Log.i(TAG, searchBar.query.toString())
            favoritesListViewModel.movieItemLiveData = tmdbFetchr().getSearch(searchBar.query.toString())
        }

        searchBar = view.findViewById(R.id.searchView) as SearchView
        searchBar.setSubmitButtonEnabled(true)
        searchBar.setOnSearchClickListener() {
            Log.i(TAG, "Got movies $")
        }
        searchBar.setOnSearchClickListener() { searchBar.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_SEARCH && event.action == KeyEvent.ACTION_UP) {
                //Perform Code
                Log.i(TAG, "soconfused44")
                return@OnKeyListener true
            }
            false
        })}
        //searchBar.setOnClickListener { Log.i(TAG, "soconfused") }


        searchBar.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_SEARCH && event.action == KeyEvent.ACTION_UP) {
                //Perform Code
                Log.i(TAG, "soconfused")
                return@OnKeyListener true
            }
            false
        })

        return view
        //return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoritesListViewModel.movieItemLiveData.observe(
            viewLifecycleOwner,
            Observer { movies ->
                movies?.let {
                    Log.i(TAG, "Got movies $movies")
                    fullListRecyclerView.adapter = MovieAdapter(movies)
                    //updateUI(movies)
//                    this.game = games.get(1)
//
//                    photoAFile = bbViewModel.getPhotoFile(game)
//                    photoAUri = FileProvider.getUriForFile(requireActivity(),
//                        "com.example.group27project1.fileprovider",
//                        photoAFile)
//
//                    this.game = games.get(2)
//                    photoBFile = bbViewModel.getPhotoFile(game)
//                    photoBUri = FileProvider.getUriForFile(requireActivity(),
//                        "com.example.group27project1.fileprovider",
//                        photoBFile)
//
//                    //updateUI(games)
//                    updatePhotoView()
//
//                    gameWeatherTextView.setText("1")

                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        thumbnailDownloader.clearQueue()
        viewLifecycleOwner.lifecycle.removeObserver(
            thumbnailDownloader.viewLifecycleObserver
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(
            thumbnailDownloader.fragmentLifecycleObserver
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onActivityResult(requestCode: Int,  resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode != Activity.RESULT_OK) {
//            return
//        }
//        if (requestCode == REQUEST_CODE_SAVE) {
//            bbViewModel.isScoreSaved =
//                data?.getBooleanExtra(EXTRA_SCORES_SAVED, false) ?: false
//        }
//
//        if (requestCode == REQUEST_PHOTO) {
//            updatePhotoView()
//        }
    }

    private inner class MovieHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var movie: MovieItem

        private val titleTextView: TextView = itemView.findViewById(R.id.movie_title)
        private val descTextView: TextView = itemView.findViewById(R.id.movie_desc)
        private val voteavgTextView: TextView = itemView.findViewById(R.id.movie_voteavg)
        private val poster: ImageView = itemView.findViewById(R.id.movie_poster) as ImageView

        val bindDrawable: (Drawable) -> Unit = poster::setImageDrawable

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: MovieItem) {
            this.movie = movie
            titleTextView.text = movie.title
            descTextView.text = movie.overview
            voteavgTextView.text = movie.vote_average.toString()

            this.movie.poster_path = "https://image.tmdb.org/t/p/" + "w92/" + this.movie.poster_path
//            val url = URL(this.movie.poster_path)
//            val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
//            poster.setImageBitmap(bmp)

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
            Toast.makeText(context, "${movie.original_title} clicked!", Toast.LENGTH_SHORT).show()
            callbacks?.onMovieSelected(movie)
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

            val placeholder: Drawable = ContextCompat.getDrawable(requireContext(), R.drawable.tmdblogo) ?: ColorDrawable()
            holder.bindDrawable(placeholder)
            thumbnailDownloader.queueThumbnail(holder, movie.poster_path)
        }

    }



}