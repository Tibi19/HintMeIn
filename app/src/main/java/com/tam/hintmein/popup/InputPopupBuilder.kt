package com.tam.hintmein.popup

import android.app.AlertDialog
import com.tam.hintmein.data.Hint
import com.tam.hintmein.databinding.PopupHintInputBinding
import com.tam.hintmein.utils.Constants
import com.tam.hintmein.utils.HintEnvironment

const val ADD_TITLE = "Add a Hint"
const val EDIT_TITLE = "Edit Hint"

class InputPopupBuilder{

    companion object {

        private lateinit var environment: HintEnvironment
        private lateinit var binding: PopupHintInputBinding
        private lateinit var dialog: AlertDialog

        fun addHint(hintEnvironment: HintEnvironment) {
            environment = hintEnvironment
            setupBinding(ADD_TITLE)
            setupDialog()
            setupCancelButton()
            setupAddButton()
            dialog.show()
        }

        fun editHint(hint: Hint?, hintEnvironment: HintEnvironment) {
            environment = hintEnvironment
            setupBinding(EDIT_TITLE)
            setupInitialState(hint)
            setupDialog()
            setupCancelButton()
            hint?.let { setupEditButton(it) }
            dialog.show()
        }

        // Setup initial state of the view with parameter's state
        private fun setupInitialState(hint: Hint?) {
            val errorMessage = Constants.NOTHING_FOUND
            binding.etDomain.setText(hint?.domain ?: errorMessage)
            binding.etUsername.setText(hint?.username ?: errorMessage)
            binding.etHintText.setText(hint?.text ?: errorMessage)
        }

        private fun setupBinding(title: String) {
            binding = PopupHintInputBinding.inflate(environment.inflater)
            binding.tvTitle.text = title
        }

        private fun setupDialog() {
            val builder = AlertDialog.Builder(environment.context).setView(binding.root)
            dialog = builder.create()
            dialog.setCancelable(true)
        }

        private fun setupCancelButton() = binding.btnCancelInput.setOnClickListener { dialog.dismiss() }

        // Sets up the submit button with add functionality.
        private fun setupAddButton() = binding.btnSubmitInput.setOnClickListener {
            val viewModel = environment.viewModel
            viewModel.addHint(
                Hint(
                    domain = binding.etDomain.text.toString(),
                    username = binding.etUsername.text.toString(),
                    text = binding.etHintText.text.toString(),
                    position = viewModel.getHints().value!!.size
                )
            )
            dialog.dismiss()
        }

        // Sets up the submit button with edit functionality.
        private fun setupEditButton(hint: Hint) = binding.btnSubmitInput.setOnClickListener {
            hint.domain = binding.etDomain.text.toString()
            hint.username = binding.etUsername.text.toString()
            hint.text = binding.etHintText.text.toString()
            environment.viewModel.updateHint(hint)
            dialog.dismiss()
        }

    }

}