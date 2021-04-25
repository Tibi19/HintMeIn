package com.tam.hintmein.popup

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.tam.hintmein.data.Hint
import com.tam.hintmein.databinding.PopupHintTextBinding

const val MISSING_HINT = "No hint found :("

class ShowPopupBuilder {

    companion object {

        fun showHint(context: Context, inflater: LayoutInflater, hint: Hint?) {
            val binding = PopupHintTextBinding.inflate(inflater)
            binding.tvHintText.text = hint?.text ?: MISSING_HINT

            val builder = AlertDialog.Builder(context).setView(binding.root)
            val dialog = builder.create()
            dialog.setCancelable(true)
            dialog.show()
        }

    }

}