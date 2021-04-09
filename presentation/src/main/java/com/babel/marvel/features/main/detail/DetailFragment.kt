package com.babel.marvel.features.main.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.babel.marvel.R
import com.babel.marvel.base.BaseFragment
import com.babel.marvel.databinding.FragmentDetailBinding
import com.babel.marvel.domain.datastate.DataState
import com.babel.marvel.domain.viewstate.CharactersListViewState
import com.babel.marvel.domain.viewstate.Items
import com.babel.marvel.events.MarvelEventState
import com.babel.marvel.features.main.adapter.ItemListAdapter
import com.bumptech.glide.Glide
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@FlowPreview
@ExperimentalCoroutinesApi
class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by sharedViewModel()

    lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            viewModel.setViewState(it.getSerializable("VM") as CharactersListViewState)
        }

        viewModel.setEventState(
            viewModel.getCurrentViewStateOrNew().idSelected?.let {
                MarvelEventState.ShowCharacterEvent(it)
            } ?: MarvelEventState.None
        )

        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(
            viewLifecycleOwner,
            { dataState ->
                dataStateChangeListener?.onDataStateChangeListener(dataState)
                when (dataState) {
                    is DataState.SUCCESS -> {
                        dataState.data?.let { viewState ->
                            viewState.characterFields?.listCharacters?.let { singleCharacter ->
                                singleCharacter[0].apply {
                                    Glide.with(requireView())
                                        .load(thumbnail.path + "." + thumbnail.extension)
                                        .error(R.drawable.ic_marvel)
                                        .placeholder(R.drawable.ic_marvel)
                                        .into(binding.ivDetailImage)
                                    if (name.isNotEmpty()) {
                                        binding.tvDetailTitle.visibility = View.VISIBLE
                                        binding.tvDetailTitle.text = name
                                    }
                                    setTextAndVisibility(binding.tvDetailTitle, name)
                                    setTextAndVisibility(binding.tvDetailInfo, description)
                                    setTextAndVisibility(binding.tvDetailSeries, getString(R.string.series_amount, series.items.size.toString()), series.items)
                                    setTextAndVisibility(binding.tvDetailComics, getString(R.string.comics_amount, comics.items.size.toString()), comics.items)
                                    setTextAndVisibility(binding.tvDetailEvents, getString(R.string.events_amount, events.items.size.toString()), events.items)
                                    setTextAndVisibility(binding.tvDetailStories, getString(R.string.stories_amount, stories.items.size.toString()), stories.items)
                                    setAdapterAndVisibility(binding.lvDetailSeries, series.items)
                                    setAdapterAndVisibility(binding.lvDetailComic, comics.items)
                                    setAdapterAndVisibility(binding.lvDetailStories, stories.items)
                                    setAdapterAndVisibility(binding.lvDetailEvents, events.items)
                                }
                            }
                        }
                    }
                }
            }
        )
    }

    private fun setTextAndVisibility(view: TextView, text: String, arrayValues: List<Items>? = null) {
        if (text.isNotEmpty()) {
            view.visibility =
                if ((arrayValues != null && arrayValues.isNotEmpty()) || arrayValues == null)
                    View.VISIBLE
                else
                    View.GONE
            view.text = text
        }
    }

    private fun setAdapterAndVisibility(view: RecyclerView, arrayValues: List<Items>) {
        if (arrayValues.isNotEmpty()) {
            view.visibility = View.VISIBLE
            view.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            view.setOnTouchListener { _, _ -> true }
            view.adapter = ItemListAdapter(arrayValues)
        }
    }
}
