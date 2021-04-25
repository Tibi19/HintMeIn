package com.tam.hintmein.utils

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuInflater
import androidx.lifecycle.ViewModel
import com.tam.hintmein.HintViewModel

data class HintEnvironment(
    val context: Activity,
    val inflater: LayoutInflater,
    val viewModel: HintViewModel
)