package com.assignment.rdash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.rdash.data.DesignLayout
import com.assignment.rdash.data.RDashAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DesignLayoutsViewModel @Inject constructor(private val rDashAPI: RDashAPI): ViewModel() {

    private val _designLayouts = MutableStateFlow<ArrayList<DesignLayout>>(ArrayList())
    val designLayouts = _designLayouts.asStateFlow()

    init {
        getDesignLayouts()
    }

    private fun getDesignLayouts() {
        viewModelScope.launch {
            val response = rDashAPI.getDesignLayouts().data
            _designLayouts.emit(response)
        }
    }

}