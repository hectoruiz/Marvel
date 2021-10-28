package hector.ruiz.marvel.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import hector.ruiz.marvel.R
import hector.ruiz.marvel.databinding.LoadStateCharacterBinding

class CharacterLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<CharacterLoadStateAdapter.CharacterLoadStateViewHolder>() {

    class CharacterLoadStateViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = LoadStateCharacterBinding.bind(view)

        fun bind(loadState: LoadState, retry: () -> Unit) {
            binding.apply {
                binding.loadStateRetry.setOnClickListener {
                    retry.invoke()
                }
                this.loadStateRetry.isVisible = loadState !is LoadState.Loading
                this.loadStateError.isVisible = loadState !is LoadState.Loading
                this.loadStateProgress.isVisible = loadState is LoadState.Loading

                if (loadState is LoadState.Error) {
                    this.loadStateError.text = view.resources.getString(R.string.error_request)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: CharacterLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): CharacterLoadStateViewHolder {
        return CharacterLoadStateViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_character, parent, false)
        )
    }
}
