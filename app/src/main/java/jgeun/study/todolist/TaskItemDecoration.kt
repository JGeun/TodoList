package jgeun.study.todolist

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TaskItemDecoration(
	private val spacing: Int
) : RecyclerView.ItemDecoration(){

	override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
		super.getItemOffsets(outRect, view, parent, state)

		val position = parent.getChildAdapterPosition(view)

		if (position == 0) return

		outRect.top = spacing
	}
}