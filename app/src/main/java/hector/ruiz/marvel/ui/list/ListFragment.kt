package hector.ruiz.marvel.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hector.ruiz.marvel.databinding.ListCharacterBinding
import hector.ruiz.presentation.list.ListViewModel
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
        binding?.characterList?.adapter = characterAdapter
        characterAdapter.onDetailClick = {
            it?.let {
                val action = ListFragmentDirections.actionListFragmentToDetailFragment(it)
                findNavController().navigate(action)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listViewModel.getCharacterList()

        listViewModel.isLoading.observe(viewLifecycleOwner, {
            binding?.charactersProgress?.isVisible = it
        })

        listViewModel.characterList.observe(viewLifecycleOwner, {
            characterAdapter.setList(it)
            characterAdapter.notifyItemRangeInserted(characterAdapter.itemCount, it.size)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
