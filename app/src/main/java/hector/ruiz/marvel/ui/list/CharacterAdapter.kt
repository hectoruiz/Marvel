package hector.ruiz.marvel.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hector.ruiz.domain.Character
import hector.ruiz.marvel.R
import hector.ruiz.marvel.databinding.ItemCharacterBinding
import hector.ruiz.marvel.extensions.loadImage
import javax.inject.Inject

class CharacterAdapter @Inject constructor(private val picasso: Picasso) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    var onDetailClick: ((Int?) -> Unit)? = null
    private var characters: List<Character?> = emptyList()

    fun setList(characters: List<Character?>) {
        this.characters = characters
    }

    inner class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCharacterBinding.bind(view)

        init {
            binding.characterDetail.setOnClickListener {
                onDetailClick?.invoke(characters[adapterPosition]?.id)
            }
        }

        fun bind(character: Character?) {
            with(binding) {
                characterProgress.isVisible = true
                picasso.loadImage(
                    character?.thumbnail,
                    this.characterImage,
                    this.characterProgress
                )
                this.characterName.text = character?.name
                this.characterDescription.visibility =
                    if (character?.description?.isNotBlank() == true) {
                        this.characterDescription.text = character.description
                        VISIBLE
                    } else {
                        GONE
                    }
            }
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
}
