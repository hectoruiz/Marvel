package hector.ruiz.marvel.extensions

import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import hector.ruiz.domain.Thumbnail
import hector.ruiz.marvel.R

fun Picasso.loadImage(
    thumbnail: Thumbnail?,
    appCompatImageView: AppCompatImageView,
    circularProgressIndicator: CircularProgressIndicator
) {
    this
        .load(getImageUrl(thumbnail))
        .centerInside()
        .fit()
        .placeholder(R.drawable.user_placeholder)
        .error(R.drawable.user_placeholder_error)
        .into(appCompatImageView, object : Callback {
            override fun onSuccess() {
                circularProgressIndicator.isVisible = false
            }

            override fun onError(e: Exception?) {
                circularProgressIndicator.isVisible = false
                e?.printStackTrace()
            }
        })
}

private fun getImageUrl(thumbnail: Thumbnail?) =
    thumbnail?.path?.replace("http", "https") + "." + thumbnail?.extension
