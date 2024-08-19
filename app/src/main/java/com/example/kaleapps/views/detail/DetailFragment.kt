package com.example.kaleapps.views.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.core.domain.model.Movie
import com.example.kaleapps.R
import com.example.kaleapps.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var detailMovie: Movie
    private var isFavorite by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments?.getParcelable<Movie>(EXTRA_MOVIE)
        if (movie != null){
            detailMovie = movie
        }
        binding.apply {
            Glide.with(requireContext()).load(movie?.image).into(movieImg)
            movieTitle.text = movie?.title
            overviewContent.text = movie?.overview
            backButton.setOnClickListener {
                it.findNavController().popBackStack()
            }
        }
        movie?.id?.let {
            viewModel.getFavoriteState(it).observe(viewLifecycleOwner){ fav ->
                binding.fabAddFav.apply {
                    if (fav == true){
                        setImageResource(R.drawable.baseline_favorite_24)
                    }else{
                        setImageResource(R.drawable.baseline_favorite_border_24)
                    }
                    isFavorite = fav
                }
            }
        }
        fabFavorite()
    }

    private fun fabFavorite() {
        binding.fabAddFav.apply {
            setOnClickListener{
                if (!isFavorite){
                    viewModel.insertFavorite(detailMovie)
                    setImageResource(R.drawable.baseline_favorite_24)
                    isFavorite = true
                }else{
                    viewModel.deleteFavorite(detailMovie)
                    setImageResource(R.drawable.baseline_favorite_border_24)
                    isFavorite = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
    }
}