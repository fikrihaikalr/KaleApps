package com.example.favorite.ui.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.core.ui.MovieAdapter
import com.example.kaleapps.di.FavoriteModule
import com.example.kaleapps.views.detail.DetailFragment
import com.example.favorite.R
import com.example.favorite.databinding.FragmentFavoriteBinding
import com.example.favorite.di.DaggerFavoriteComponent
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject
class FavoriteFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FavoriteViewModel by viewModels {
        factory
    }
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    FavoriteModule::class.java
                )
            ).build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back()
        movieAdapter = MovieAdapter {
            findNavController().navigate(
                com.example.kaleapps.R.id.action_favoriteFragment_to_detailFragment,
                Bundle().apply {
                    putParcelable(DetailFragment.EXTRA_MOVIE, it)
                })
        }
        binding.rvFav.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
            setHasFixedSize(true)
        }
        viewModel.getFavorite.observe(viewLifecycleOwner) {
            movieAdapter.differ.submitList(it)
            if (it.isNotEmpty()){
                binding.rvFav.visibility = View.VISIBLE
                binding.empty.tvEmpty.visibility = View.GONE
            } else {
                binding.rvFav.visibility = View.GONE
                binding.empty.tvEmpty.visibility = View.VISIBLE
            }
        }
    }

    private fun back() {
        binding.back.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}