package com.example.finalproject

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Movie
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
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
import java.io.File
import java.util.*

private const val TAG = "MovieDetailsFragment"


private const val ARG_DB_ID = "db_id"
private const val ARG_TITLE = "movie_title"
private const val ARG_OVERVIEW = "movie_overview"
private const val ARG_POSTERPATH = "movie_posterpath"
private const val ARG_RATING = "movie_rating"
private const val REQUEST_PHOTO = 2

class MovieDetailsFragment : Fragment(){

    private var callbacks: TrendingFragment.Callbacks? = null

    private lateinit var movie: MovieItem
    private lateinit var titleField: TextView
    private lateinit var genreField: TextView
    private lateinit var directorField: TextView
    private lateinit var otherField: TextView
    private lateinit var trendingButton: Button
    private lateinit var favoritesButton: Button
    //private lateinit var photoView: ImageView
    private lateinit var photoFile: File
    private lateinit var photoUri: Uri

    private lateinit var AddfavoritesButton: Button

    private lateinit var reactionView: ImageView
    private lateinit var reactionText: TextView
    private lateinit var cameraButton: ImageButton

    private lateinit var  favoritesBtn: Button
    private lateinit var  HomePageBtn: Button

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
                    //updateUI(favorites)
                }
            })

        val view = inflater.inflate(R.layout.movie_details, container, false)
        titleField = view.findViewById(R.id.movie_title) as TextView
        genreField = view.findViewById(R.id.description) as TextView
//        directorField = view.findViewById(R.id.director) as TextView
        otherField = view.findViewById(R.id.rating) as TextView

        AddfavoritesButton = view.findViewById(R.id.add_to_fav_btn)

        reactionText = view.findViewById(R.id.reaction) as TextView
        reactionView = view.findViewById(R.id.uploaded_photo) as ImageView
        cameraButton = view.findViewById(R.id.camera_btn) as ImageButton

        favoritesBtn = view.findViewById(R.id.favorites_btn)
        HomePageBtn = view.findViewById(R.id.movies_btn)

        favoritesBtn.setOnClickListener {
            callbacks?.onFavoritesSelected()
        }
        HomePageBtn.setOnClickListener {
            callbacks?.onTrendingSelected()
        }

        AddfavoritesButton.setOnClickListener {
            // check if movie is in favorites list
            var flag = 0
            for (i in favoritesListViewModel.favoritesListLiveData.value!!) {
                if (i.original_title == favoritesListViewModel.movieLiveData.value?.original_title)
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

        return view
    }

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
                    this.movie = favoritesListViewModel.movieLiveData.value!!
                    titleField.text = title
                    genreField.text = overview
                    otherField.text = rating
                    photoFile = favoritesListViewModel.getPhotoFile(this.movie)
                    photoUri = FileProvider.getUriForFile(requireActivity(),
                        "com.example.finalproject.android.movieapp.fileprovider",
                        photoFile)
//                    directorField.text = posterpath
                   // updateUI()
                    var flag = 0
                    for (i in favoritesListViewModel.favoritesListLiveData.value!!) {
                        if (i.db_id == favoritesListViewModel.movieLiveData.value?.db_id)
                            flag = 1
                    }

                    if (flag == 0)//not in list, add
                    {
                        reactionView.visibility = View.GONE
                        reactionText.visibility = View.GONE
                        cameraButton.visibility = View.GONE
                    }
                    else//in list, update
                    {
                        AddfavoritesButton.visibility = View.GONE
                        titleField.visibility = View.VISIBLE
                    }
                }
            })
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().revokeUriPermission(photoUri,
            Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    }


    private fun updateUI(movies: List<MovieItem>) {
        updatePhotoView()
    }

    private fun updatePhotoView() {
        if (photoFile.exists()) {
            val bitmap = getScaledBitmap(photoFile.path, requireActivity())
            reactionView.setImageBitmap(bitmap)
        } else {
            reactionView.setImageDrawable(null)
        }
    }

    fun newInstance(movie: MovieItem): Fragment {
        val args = Bundle().apply {
            putSerializable(ARG_DB_ID, movie.db_id)
            putSerializable(ARG_TITLE, movie.original_title)
            putSerializable(ARG_OVERVIEW, movie.overview)
            putSerializable(ARG_RATING, movie.vote_average.toString())
            putSerializable(ARG_POSTERPATH, movie.poster_path)
        }
        return MovieDetailsFragment().apply {
            arguments = args
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onResume() called")

        cameraButton.apply {
            val packageManager: PackageManager = requireActivity().packageManager
            val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val resolvedActivity: ResolveInfo? =
                packageManager.resolveActivity(captureImage,
                    PackageManager.MATCH_DEFAULT_ONLY)
            if (resolvedActivity == null) {
         //       isEnabled = false
            }
            setOnClickListener {
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                val cameraActivities: List<ResolveInfo> =
                    packageManager.queryIntentActivities(captureImage,
                        PackageManager.MATCH_DEFAULT_ONLY)
                for (cameraActivity in cameraActivities) {
                    requireActivity().grantUriPermission(
                        cameraActivity.activityInfo.packageName,
                        photoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                }
                startActivityForResult(captureImage, REQUEST_PHOTO)
            }
        }

    }

    override fun onActivityResult(requestCode: Int,  resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PHOTO) {
            requireActivity().revokeUriPermission(photoUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            updatePhotoView()
        }
    }
}