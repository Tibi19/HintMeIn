package com.tam.hintmein

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tam.hintmein.adapter.HintAdapter
import com.tam.hintmein.adapter.HintHolderMover
import com.tam.hintmein.databinding.ActivityMainBinding
import com.tam.hintmein.popup.InputPopupBuilder
import com.tam.hintmein.utils.HintEnvironment

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

    // Update the moved hints in onPause
    // If done while the app is being used, there might be UI bugs
    override fun onPause() {
        super.onPause()
        hintMover.updateMovedHints(hintEnvironment.viewModel)
    }
}