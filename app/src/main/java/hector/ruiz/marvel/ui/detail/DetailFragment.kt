package hector.ruiz.marvel.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import hector.ruiz.domain.Detail
import hector.ruiz.marvel.R
import hector.ruiz.marvel.databinding.DetailCharacterBinding
import hector.ruiz.marvel.extensions.loadImage
import hector.ruiz.presentation.detail.DetailViewModel
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: DetailCharacterBinding? = null
    private val binding get() = _binding
    private val args: DetailFragmentArgs by navArgs()
    private val detailViewModel: DetailViewModel by viewModels()

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var appearancesAdapter: AppearancesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailCharacterBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding?.root
    }

    private fun initRecyclerView() {
        binding?.characterDetailAppearancesList?.layoutManager = GridLayoutManager(
            context,
            COLUMNS_NUMBER,
            RecyclerView.VERTICAL,
            false
        )
        binding?.characterDetailAppearancesList?.adapter = appearancesAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.getCharacterDetail(args.characterId)

        detailViewModel.isLoading.observe(viewLifecycleOwner, {
            binding?.charactersDetailProgress?.isVisible = it
        })

        detailViewModel.character.observe(viewLifecycleOwner, { character ->
            binding?.apply {
                picasso.loadImage(
                    character?.thumbnail,
                    this.characterDetailImage,
                    characterDetailImageProgress
                )
                this.characterDetailName.text = character?.name
                setUpButton(
                    this.characterDetailComicsButton, R.plurals.number_comics, character?.comics
                )
                characterDetailSeriesButton.apply {
                    getButtonVisibility(character?.series?.returned).also { isVisible ->
                        this.takeIf { isVisible }.apply {
                            this?.text = getString(
                                R.string.detail_button_series,
                                character?.series?.returned
                            )
                            this?.setOnClickListener {
                                detailViewModel.getItemDetail(character?.series?.collectionURI)
                            }
                        }
                    }
                }
                setUpButton(
                    this.characterDetailStoriesButton, R.plurals.number_stories, character?.stories
                )
                setUpButton(
                    this.characterDetailEventsButton, R.plurals.number_events, character?.events
                )
            }
        })

        detailViewModel.appearances.observe(viewLifecycleOwner, {
            appearancesAdapter.notifyItemRangeRemoved(0, appearancesAdapter.itemCount)
            appearancesAdapter.setAppearances(it)
            appearancesAdapter.notifyItemRangeInserted(0, it.size)
        })
    }

    private fun setUpButton(button: MaterialButton, pluralResource: Int, detail: Detail?) {
        button.isVisible =
            getButtonVisibility(detail?.returned).also { isVisible ->
                button.takeIf { isVisible }.let {
                    button.text = getQuantity(pluralResource, detail?.returned)
                    button.setOnClickListener {
                        detailViewModel.getItemDetail(detail?.collectionURI)
                    }
                }
            }
    }

    private fun getQuantity(pluralResource: Int, quantity: Int?): String {
        val quantityAppearances = quantity ?: 0
        return getString(
            R.string.detail_button, context?.resources?.getQuantityString(
                pluralResource,
                quantityAppearances, quantityAppearances
            )
        )
    }

    private fun getButtonVisibility(quantity: Int?): Boolean {
        return if (quantity != null) {
            quantity > 0
        } else false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private companion object {
        const val COLUMNS_NUMBER = 2
    }
}
