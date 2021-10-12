package com.example.finalproject

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationServices

private const val TAG = "FavoritesListFragment"

class FavoritesListFragment : Fragment(){

    private var callbacks: TrendingFragment.Callbacks? = null
    private lateinit var favoritesRecyclerView: RecyclerView
    private lateinit var thumbnailDownloader: ThumbnailDownloader<MovieHolder>
    private var adapter: MovieAdapter? = MovieAdapter(emptyList())
    private val favoritesListViewModel: MoviesAppViewModel by lazy {
        ViewModelProviders.of(this).get(MoviesAppViewModel::class.java)
    }

    companion object {
        fun newInstance(): FavoritesListFragment {
            return FavoritesListFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as TrendingFragment.Callbacks?
    }

    private lateinit var  HomePageBtn: Button


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
        val view = inflater.inflate(R.layout.favorites_page, container, false)
        favoritesRecyclerView =
            view.findViewById(R.id.movieList) as RecyclerView
        favoritesRecyclerView.layoutManager = LinearLayoutManager(context)

        favoritesRecyclerView.adapter = adapter

        HomePageBtn = view.findViewById(R.id.movie_list_btn)
        HomePageBtn.setOnClickListener {
            callbacks?.onTrendingSelected()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoritesListViewModel.favoritesListLiveData.observe(
            viewLifecycleOwner,
            Observer { favorites ->
                favorites?.let {
                    Log.i(TAG, "Got favorites ${favorites.size}")
                    updateUI(favorites)
                }
            })
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private inner class MovieHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var movie: MovieItem


        private val titleTextView: TextView = itemView.findViewById(R.id.movie_title)

        private var toggleButton : ToggleButton = itemView.findViewById(R.id.toggleButton)
        private var deleteButton: ImageButton = itemView.findViewById(R.id.delete_btn)
        private val poster: ImageView = itemView.findViewById(R.id.imageView2) as ImageView

        val bindDrawable: (Drawable) -> Unit = poster::setImageDrawable
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: MovieItem) {
            this.movie = movie
            titleTextView.text = movie.original_title

            toggleButton.setOnClickListener {
                favoritesListViewModel.movieLiveData.value?.let { it1 ->
                    it1.video = "Watched"
                    favoritesListViewModel.updateFavorite(
                        it1
                    )
                }
            }

            deleteButton.setOnClickListener {
                favoritesListViewModel.movieLiveData.value?.let { it1 ->
                    favoritesListViewModel.deleteFavorite(
                        it1
                    )
                }
            }

            movie.poster_path = "https://image.tmdb.org/t/p/" + "w92/" + movie.poster_path
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

            val view = layoutInflater.inflate(R.layout.list_item_favorite, parent, false)
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

    private fun updateUI(movies: List<MovieItem>) {
        adapter = MovieAdapter(movies)
        favoritesRecyclerView.adapter = adapter
    }
}