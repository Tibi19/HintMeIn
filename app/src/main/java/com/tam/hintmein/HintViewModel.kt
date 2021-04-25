package com.tam.hintmein

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tam.hintmein.data.Hint
import com.tam.hintmein.data.HintDatabase
import kotlinx.coroutines.launch

class HintViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = HintDatabase.getInstance(application.applicationContext).hintDao
    private lateinit var hints: LiveData<MutableList<Hint>>

    init {
        viewModelScope.launch { hints = dao.getHints() }
    }

    fun addHint(hint: Hint) = viewModelScope.launch { dao.insert(hint) }
    fun updateHint(hint: Hint) = viewModelScope.launch { dao.update(hint) }
    fun deleteHint(hint: Hint) = viewModelScope.launch { dao.delete(hint) }
    fun getHints(): LiveData<MutableList<Hint>> = hints

}