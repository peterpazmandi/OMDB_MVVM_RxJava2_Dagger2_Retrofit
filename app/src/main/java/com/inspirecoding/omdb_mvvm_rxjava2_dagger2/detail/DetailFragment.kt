package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.detail

import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.R
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Movie
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.utils.Status
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.utils.fromHtmlWithParams
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.utils.gone
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.utils.visible
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.viewmodelprovider.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.detail_fragment.iv_poster
import kotlinx.android.synthetic.main.detail_fragment.tv_title
import kotlinx.android.synthetic.main.detail_fragment.tv_year
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DetailFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: DetailViewModel

    private val safeArgs : DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        sharedElementEnterTransition = TransitionInflater
            .from(context)
            .inflateTransition(android.R.transition.move)
        postponeEnterTransition(250, TimeUnit.MILLISECONDS)

        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)

        val movie = safeArgs.movie

        viewModel.subscribeObservableMovieResult(movie.Title)

        iv_backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        iv_poster.transitionName = movie.Poster

        Glide
            .with(this)
            .load(movie.Poster)
            .centerCrop()
            .placeholder(R.drawable.image_not_found)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(iv_poster)

        tv_title.text = movie.Title
        tv_year.text = movie.Year

        iv_poster.transitionName = movie.Poster
        tv_title.transitionName = movie.Title

        setupObserver()
    }

    private fun setupObserver() {

        viewModel.movieResult.observe(viewLifecycleOwner, Observer { _result ->

            when(_result.status)
            {
                Status.SUCCESS -> {
                    _result.data?.let {
                        updateUi(it)
                    }

                    progressBar.gone()

                }
                Status.LOADING -> {

                    progressBar.visible()

                }
                Status.ERROR -> {
                    _result.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                    progressBar.gone()

                }
            }

        })

    }

    private fun updateUi(movie: Movie) {

        tv_rating.text = resources.getString(R.string.rating, movie.imdbRating)
        tv_description.text = movie.Plot

        tv_directors.text = context?.fromHtmlWithParams(R.string.directors, movie.Director)
        tv_actors.text = context?.fromHtmlWithParams(R.string.actors, movie.Actors)
        tv_awards.text = context?.fromHtmlWithParams(R.string.awards, movie.Awards)
    }

}