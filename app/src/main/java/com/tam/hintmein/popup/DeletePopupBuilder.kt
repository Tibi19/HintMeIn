package com.tam.hintmein.popup

import android.app.AlertDialog
import com.tam.hintmein.data.Hint
import com.tam.hintmein.databinding.PopupCheckDeleteBinding
import com.tam.hintmein.utils.HintEnvironment

class DeletePopupBuilder {

    companion object {

        fun deleteHint(hint: Hint?, hintEnvironment: HintEnvironment) {
            val binding = PopupCheckDeleteBinding.inflate(hintEnvironment.inflater)
            val builder = AlertDialog.Builder(hintEnvironment.context).setView(binding.root)
            val dialog = builder.create()
            dialog.setCancelable(true)

            binding.btnCancelDelete.setOnClickListener { dialog.dismiss() }
            hint?.let {
                binding.btnConfirmDelete.setOnClickListener {
                    hintEnvironment.viewModel.deleteHint(hint)
                    dialog.dismiss()
                }
            }

            dialog.show()
        }

    }

}