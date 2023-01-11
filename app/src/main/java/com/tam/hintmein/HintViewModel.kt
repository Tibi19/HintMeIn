package com.tam.hintmein

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tam.hintmein.data.Hint
import com.tam.hintmein.data.HintDatabase
import com.tam.hintmein.utils.HINT_HEADER
import com.tam.hintmein.utils.wrapTextContainingComma
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

    fun getHintsAsCsvContent(): String? {
        val hints = this.hints.value ?: return null
        var content = HINT_HEADER
        hints.forEach { hint ->
            val domain = wrapTextContainingComma(hint.domain)
            val username = wrapTextContainingComma(hint.username)
            val hintText = wrapTextContainingComma(hint.text)
            val contentLine = "$domain,$username,$hintText"
            content += "\n$contentLine"
        }
        return content
    }

}