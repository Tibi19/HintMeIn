package com.tam.hintmein.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.tam.hintmein.R
import com.tam.hintmein.data.Hint
import com.tam.hintmein.databinding.RvHintRowBinding
import com.tam.hintmein.popup.DeletePopupBuilder
import com.tam.hintmein.popup.InputPopupBuilder
import com.tam.hintmein.popup.ShowPopupBuilder
import com.tam.hintmein.utils.Constants
import com.tam.hintmein.utils.HintEnvironment

class HintAdapter(private val hintEnvironment: HintEnvironment) : RecyclerView.Adapter<HintAdapter.HintHolder>() {

    private var hints: List<Hint>? = null

    inner class HintHolder(val binding: RvHintRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HintHolder {
        return HintHolder(
            RvHintRowBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: HintHolder, position: Int) {
        val hint = hints?.get(position)
        val binding = holder.binding
        setViewState(binding, hint)
        setViewOnClick(binding, hint)
        setViewOptions(binding, hint)
    }



    private fun setViewState(binding: RvHintRowBinding, hint: Hint?) {
        val errorMessage = Constants.ERROR
        binding.tvDomain.text = hint?.domain ?: errorMessage
        binding.tvUsername.text = hint?.username ?: errorMessage

        val maxTextWidth = (Resources.getSystem().displayMetrics.widthPixels * 0.85).toInt()
        // Limit the width of the texts, otherwise the options button might be pushed out of screen
        binding.tvDomain.maxWidth = maxTextWidth
        binding.tvUsername.maxWidth = maxTextWidth
    }

    private fun setViewOnClick(binding: RvHintRowBinding, hint: Hint?) = binding.cvHint.setOnClickListener{
        ShowPopupBuilder.showHint(
            hint = hint,
            context = hintEnvironment.context,
            inflater = hintEnvironment.inflater
        )
    }

    private fun setViewOptions(binding: RvHintRowBinding, hint: Hint?) = binding.tvOptions.setOnClickListener{
        val menu = PopupMenu(hintEnvironment.context, it)
        menu.inflate(R.menu.menu_hint)
        menu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_delete -> {
                    DeletePopupBuilder.deleteHint(hint, hintEnvironment)
                    true
                }
                R.id.action_edit -> {
                    InputPopupBuilder.editHint(hint, hintEnvironment)
                    true
                }
                else -> false
            }
        }
        menu.show()
    }

    fun setHints(hints: List<Hint>?) = hints?.let {
        this.hints = it
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = hints?.size ?: 0
}