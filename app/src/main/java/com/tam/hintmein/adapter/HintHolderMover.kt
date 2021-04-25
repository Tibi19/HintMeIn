package com.tam.hintmein.adapter

import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tam.hintmein.HintViewModel
import com.tam.hintmein.data.Hint
import java.util.*

/**
 * Offers RecyclerView functionality to reposition items by drag and drop.
 * Updates Hint positions in the database to remember position.
 */

// List of hints to be updated in the database because their positions have changed.
private val movedHintsToUpdate: MutableList<Hint> = mutableListOf()

class HintHolderMover(hintViewModel: HintViewModel) : ItemTouchHelper (
    object : ItemTouchHelper.SimpleCallback(UP or DOWN, 0) {

        override fun onMove(
            recyclerView: RecyclerView,
            draggedHolder: RecyclerView.ViewHolder,
            targetHolder: RecyclerView.ViewHolder
        ): Boolean {
            // Swap dragged item and target item
            val dataSet = hintViewModel.getHints().value!!
            val draggedHint = dataSet[draggedHolder.adapterPosition]
            val targetHint = dataSet[targetHolder.adapterPosition]
            Collections.swap(dataSet, draggedHolder.adapterPosition, targetHolder.adapterPosition)

            // Update hints' positions so they can be updated in the database
            // Hints have already been swapped in their list, so their state position can be their index in the list
            draggedHint.position = dataSet.indexOf(draggedHint)
            targetHint.position = dataSet.indexOf(targetHint)

            // Add hints to the moved hints list only if they haven't been moved or added already
            if ( !movedHintsToUpdate.contains(draggedHint) ) movedHintsToUpdate.add(draggedHint)
            if ( !movedHintsToUpdate.contains(targetHint) ) movedHintsToUpdate.add(targetHint)
            recyclerView.adapter?.notifyItemMoved(draggedHolder.adapterPosition, targetHolder.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) { }
    }
) {
    fun updateMovedHints(hintViewModel: HintViewModel) {
        movedHintsToUpdate.forEach { hintViewModel.updateHint(it) }
        movedHintsToUpdate.clear()
    }
}
