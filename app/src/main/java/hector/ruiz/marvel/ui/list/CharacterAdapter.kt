package hector.ruiz.marvel.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hector.ruiz.domain.Character
import hector.ruiz.marvel.R
import hector.ruiz.marvel.databinding.ItemCharacterBinding
import hector.ruiz.marvel.extensions.loadImage
import javax.inject.Inject

class CharacterAdapter @Inject constructor(private val picasso: Picasso) :
    PagingDataAdapter<Character, CharacterAdapter.CharacterViewHolder>(DiffUtilCallback()) {

    var onDetailClick: ((Int?) -> Unit)? = null

    inner class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCharacterBinding.bind(view)

        init {
            binding.characterDetail.setOnClickListener {
                onDetailClick?.invoke(getItem(bindingAdapterPosition)?.id)
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
        val character = getItem(position)
        holder.bind(character)
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
}
