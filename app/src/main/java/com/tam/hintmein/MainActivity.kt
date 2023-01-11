package com.tam.hintmein

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tam.hintmein.adapter.HintAdapter
import com.tam.hintmein.adapter.HintHolderMover
import com.tam.hintmein.databinding.ActivityMainBinding
import com.tam.hintmein.popup.InputPopupBuilder
import com.tam.hintmein.utils.HintEnvironment
import com.tam.hintmein.utils.getHintsCsvName
import java.io.IOException

const val CREATE_HINTS_CSV_REQUEST_CODE = 111

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var hintEnvironment: HintEnvironment
    private lateinit var hintMover: HintHolderMover

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHintEnvironment()
        setupHintRecycler()
        setupAddHint()
        setupExport()
    }

    private fun setupHintEnvironment() {
        val hintViewModel: HintViewModel by viewModels()
        hintEnvironment = HintEnvironment(
            this,
            layoutInflater,
            hintViewModel
        )
    }

    private fun setupHintRecycler() {
        val hintRecycler = binding.rvHint
        val hintAdapter = HintAdapter(hintEnvironment)

        hintRecycler.adapter = hintAdapter
        hintRecycler.layoutManager = LinearLayoutManager(applicationContext)
        setupModelObserver(hintAdapter)

        hintMover = HintHolderMover(hintEnvironment.viewModel)
        hintMover.attachToRecyclerView(hintRecycler)
    }

    private fun setupModelObserver(adapter: HintAdapter) = hintEnvironment.viewModel.getHints().observe(
        this,
        Observer { adapter.setHints(it) }
    )

    private fun setupAddHint() = binding.fabAddHint.setOnClickListener { InputPopupBuilder.addHint(hintEnvironment) }

    private fun setupExport() =
        binding.btnExport.setOnClickListener {
            val exportIntent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "text/csv"
                putExtra(Intent.EXTRA_TITLE, getHintsCsvName())
            }
            startActivityForResult(exportIntent, CREATE_HINTS_CSV_REQUEST_CODE)
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != CREATE_HINTS_CSV_REQUEST_CODE) return
        val hintsCsvContent = hintEnvironment.viewModel.getHintsAsCsvContent() ?: return

        data?.data?.let { intentUri ->
            try {
                val outputStream = contentResolver.openOutputStream(intentUri)
                outputStream?.write(hintsCsvContent.toByteArray())
                outputStream?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
    }

    // Update the moved hints in onPause
    // If done while the app is being used, there might be UI bugs
    override fun onPause() {
        super.onPause()
        hintMover.updateMovedHints(hintEnvironment.viewModel)
    }
}