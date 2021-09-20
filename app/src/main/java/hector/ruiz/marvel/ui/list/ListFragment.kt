package hector.ruiz.marvel.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import hector.ruiz.marvel.databinding.ListCharacterBinding
import hector.ruiz.usecase.usecases.GetCharactersUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: ListCharacterBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var getCharactersUseCase: GetCharactersUseCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ListCharacterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch {
            getCharactersUseCase()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
