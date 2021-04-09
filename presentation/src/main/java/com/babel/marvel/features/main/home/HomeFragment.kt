package com.babel.marvel.features.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.babel.marvel.R
import com.babel.marvel.base.BaseFragment
import com.babel.marvel.databinding.FragmentHomeBinding
import com.babel.marvel.domain.datastate.DataState
import com.babel.marvel.domain.viewstate.CharactersListViewState
import com.babel.marvel.events.MarvelEventState
import com.babel.marvel.features.main.MainViewModel
import com.babel.marvel.features.main.adapter.ListCharacterAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@FlowPreview
@ExperimentalCoroutinesApi
class HomeFragment : BaseFragment<CharactersListViewState>(R.layout.fragment_home) {

    override val viewModel: MainViewModel by sharedViewModel()

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.setEventState(
            MarvelEventState.LoadingDataEvent
        )

        binding.rvCompleteList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        return binding.root
    }

    override fun onUpdateView(data: CharactersListViewState) {
        data.characterFields?.listCharacters?.let { list ->
            binding.rvCompleteList.adapter?.let {
                (it as ListCharacterAdapter).notifyDataSetChanged()
            } ?: kotlin.run {
                binding.rvCompleteList.adapter = ListCharacterAdapter(list) {
                    navRegistration(it)
                }
            }
        }
    }

    override fun onErrorView(data: DataState.ERROR<CharactersListViewState>) {
        // Do extra stuff if necessary
    }

    private fun navRegistration(idCharacter: Int) {
        viewModel.registerIdSelected(idCharacter)
        viewModel.navigate(R.id.action_loginFragment_to_registerFragment, viewModel.getCurrentViewStateOrNew())
    }
}
