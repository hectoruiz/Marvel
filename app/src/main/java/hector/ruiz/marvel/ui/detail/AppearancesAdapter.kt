package hector.ruiz.marvel.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hector.ruiz.domain.Character
import hector.ruiz.marvel.R
import hector.ruiz.marvel.databinding.ItemAppearancesBinding
import hector.ruiz.marvel.extensions.loadImage
import javax.inject.Inject

class AppearancesAdapter @Inject constructor(private val picasso: Picasso) :
    RecyclerView.Adapter<AppearancesAdapter.AppearancesViewHolder>() {

    private var appearances: List<Character?> = emptyList()

    fun setAppearances(appearances: List<Character?>) {
        this.appearances = appearances
    }

    inner class AppearancesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemAppearancesBinding.bind(view)

        fun bind(appearance: Character?) {
            binding.apply {
                this.appearancesProgress.isVisible = true
                picasso
                    .loadImage(
                        appearance?.thumbnail,
                        this.appearancesImage,
                        this.appearancesProgress
                    )
                this.appearancesName.text = appearance?.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppearancesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AppearancesViewHolder(
            layoutInflater.inflate(
                R.layout.item_appearances,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AppearancesViewHolder, position: Int) {
        val appearance = appearances[position]
        holder.bind(appearance)
    }

    override fun getItemCount(): Int = appearances.size
}
