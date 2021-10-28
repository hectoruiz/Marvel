package hector.ruiz.marvel.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import hector.ruiz.marvel.R
import hector.ruiz.marvel.databinding.ListCharacterBinding
import hector.ruiz.marvel.extensions.snackBarIndefinite
import hector.ruiz.marvel.extensions.snackBarLong
import hector.ruiz.presentation.list.ListViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: ListCharacterBinding? = null
    private val binding get() = _binding
    private val listViewModel: ListViewModel by viewModels()

    @Inject
    lateinit var characterAdapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ListCharacterBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding?.root
    }

    private fun initRecyclerView() {
        binding?.characterList?.adapter =
            characterAdapter.also {
                it.addLoadStateListener { loadStates ->
                    binding?.charactersProgress?.isVisible =
                        loadStates.refresh is LoadState.Loading
                    if (loadStates.refresh is LoadState.Error) {
                        snackBarIndefinite(R.string.error_request)
                    }
                }
            }.withLoadStateFooter(CharacterLoadStateAdapter { characterAdapter.retry() })

        characterAdapter.onDetailClick = {
            it?.let {
                val action = ListFragmentDirections.actionListFragmentToDetailFragment(it)
                findNavController().navigate(action)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            whenStarted {
                listViewModel.characterList.observe(viewLifecycleOwner, {
                    characterAdapter.submitData(lifecycle, it)
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
