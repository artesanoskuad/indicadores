package com.artesanoskuad.indicadores.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.artesanoskuad.indicadores.data.IndicadoresRepository

class IndicadoresViewModelFactory(
    private val repository: IndicadoresRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IndicadoresViewModel::class.java)) {
            return IndicadoresViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}