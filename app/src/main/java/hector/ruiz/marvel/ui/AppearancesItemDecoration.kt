package hector.ruiz.marvel.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class AppearancesItemDecoration(private val itemOffset: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = itemOffset
        outRect.right = itemOffset
        outRect.bottom = itemOffset
        outRect.top = parent.getChildLayoutPosition(view).takeIf {
            it == 0 || it == 1
        }.also {
            itemOffset
        } ?: 0
    }
}
