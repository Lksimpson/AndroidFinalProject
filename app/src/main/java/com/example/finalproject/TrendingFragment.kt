package com.example.finalproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import java.util.*

private const val TAG = "TrendingFragment"

class TrendingFragment : Fragment() {

    interface Callbacks {
        //fun onFavoritesSelected(gameId: UUID)
        fun onFavoritesSelected()
        fun onTrendingSelected()
        fun onSelectFavoriteSelected()
    }
    private var callbacks: Callbacks? = null

    companion object {
        fun newInstance(): TrendingFragment {
            return TrendingFragment()
        }
    }

    private lateinit var  favoritesBtn: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.homepage, container, false)

        favoritesBtn = view.findViewById(R.id.favorites_btn)
        favoritesBtn.setOnClickListener {

            callbacks?.onFavoritesSelected()

        }

        return view
        //return super.onCreateView(inflater, container, savedInstanceState)
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

}