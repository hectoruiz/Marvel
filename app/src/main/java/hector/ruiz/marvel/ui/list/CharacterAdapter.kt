package hector.ruiz.marvel.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import hector.ruiz.domain.Character
import hector.ruiz.domain.Thumbnail
import hector.ruiz.marvel.R
import hector.ruiz.marvel.databinding.ItemCharacterBinding
import javax.inject.Inject

class CharacterAdapter @Inject constructor(private val picasso: Picasso) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    var onDetailClick: ((Int?) -> Unit)? = null
    private var characters: List<Character?> = emptyList()

    fun setList(characters: List<Character?>) {
        this.characters = characters
    }

    inner class CharacterViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCharacterBinding.bind(view)

        init {
            binding.characterDetail.setOnClickListener {
                onDetailClick?.invoke(characters[adapterPosition]?.id)
            }
        }

        fun bind(character: Character?) {
            with(binding) {
                characterProgress.isVisible = true
                picasso
                    .load(getImageUrl(character?.thumbnail))
                    .centerInside()
                    .fit()
                    .placeholder(R.drawable.user_placeholder)
                    .error(R.drawable.user_placeholder_error)
                    .into(this.characterImage, object : Callback {
                        override fun onSuccess() {
                            characterProgress.isVisible = false
                        }

                        override fun onError(e: Exception?) {
                            characterProgress.isVisible = false
                            e?.printStackTrace()
                        }
                    })
                this.characterName.text = character?.name
                this.characterEpisodes.text =
                    view.context.getString(
                        R.string.character_comics,
                        getQuantityComics(character?.comics?.returned)
                    )
                this.characterDescription.visibility =
                    if (character?.description?.isNotBlank() == true) {
                        this.characterDescription.text = character.description
                        VISIBLE
                    } else {
                        GONE
                    }
            }
        }

        private fun getImageUrl(thumbnail: Thumbnail?) =
            thumbnail?.path?.replace(
                NON_ENCRYPTED_REQUEST, ENCRYPTED_REQUEST
            ) + "." + thumbnail?.extension

        private fun getQuantityComics(comicsNumber: Int?): String? {
            val quantityComics = comicsNumber ?: 0
            return view.context?.resources?.getQuantityString(
                R.plurals.number_episodes,
                quantityComics, quantityComics
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CharacterViewHolder(
            layoutInflater.inflate(
                R.layout.item_character,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int = characters.size

    companion object {
        private const val NON_ENCRYPTED_REQUEST = "http"
        private const val ENCRYPTED_REQUEST = "https"
    }
}
