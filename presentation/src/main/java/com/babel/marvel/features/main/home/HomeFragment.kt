package com.babel.marvel.features.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.babel.marvel.R
import com.babel.marvel.base.BaseFragment
import com.babel.marvel.databinding.FragmentHomeBinding
import com.babel.marvel.domain.datastate.DataState
import com.babel.marvel.events.MarvelEventState
import com.babel.marvel.features.main.MainViewModel
import com.babel.marvel.features.main.adapter.ListCharacterAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@FlowPreview
@ExperimentalCoroutinesApi
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val viewModel: MainViewModel by sharedViewModel()

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setEventState(
            MarvelEventState.LoadingDataEvent
        )

        binding.rvCompleteList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

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
                            viewState.characterFields?.listCharacters?.let { list ->
                                binding.rvCompleteList.adapter?.let {
                                    (it as ListCharacterAdapter).notifyDataSetChanged()
                                } ?: kotlin.run {
                                    binding.rvCompleteList.adapter = ListCharacterAdapter(list) {
                                        navRegistration(it)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }

    private fun navRegistration(idCharacter: Int) {
        viewModel.registerIdSelected(idCharacter)
        val bundle = Bundle()
        bundle.putSerializable("VM", viewModel.getCurrentViewStateOrNew())
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment, bundle)
    }
}
