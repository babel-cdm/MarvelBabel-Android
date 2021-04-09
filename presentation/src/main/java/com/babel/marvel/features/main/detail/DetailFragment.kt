package com.babel.marvel.features.main.detail

import android.annotation.SuppressLint
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
import com.babel.marvel.domain.Constants.Companion.DOT_SYMBOL
import com.babel.marvel.domain.Constants.Companion.FIRST_INDEX
import com.babel.marvel.domain.datastate.DataState
import com.babel.marvel.domain.viewstate.CharactersListViewState
import com.babel.marvel.domain.viewstate.Items
import com.babel.marvel.events.MarvelEventState
import com.babel.marvel.features.main.adapter.ItemListAdapter
import com.bumptech.glide.Glide
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class DetailFragment : BaseFragment<CharactersListViewState>(R.layout.fragment_detail) {

    override val viewModel: DetailViewModel by viewModel()

    lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        viewModel.setEventState(
            viewModel.getCurrentViewStateOrNew().idSelected?.let {
                MarvelEventState.ShowCharacterEvent(it)
            } ?: MarvelEventState.None
        )
        return binding.root
    }

    override fun onUpdateView(data: CharactersListViewState) {
        data.characterFields?.listCharacters?.let { singleCharacter ->
            singleCharacter[FIRST_INDEX].apply {
                Glide.with(requireView())
                    .load(thumbnail.path + DOT_SYMBOL + thumbnail.extension)
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

    override fun onErrorView(data: DataState.ERROR<CharactersListViewState>) {
        // Do extra stuff if necessary
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

    @SuppressLint("ClickableViewAccessibility")
    private fun setAdapterAndVisibility(view: RecyclerView, arrayValues: List<Items>) {
        if (arrayValues.isNotEmpty()) {
            view.visibility = View.VISIBLE
            view.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            view.setOnTouchListener { _, _ -> true }
            view.adapter = ItemListAdapter(arrayValues)
        }
    }
}
