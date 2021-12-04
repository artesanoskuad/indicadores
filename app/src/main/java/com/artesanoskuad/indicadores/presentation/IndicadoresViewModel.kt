package com.artesanoskuad.indicadores.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artesanoskuad.indicadores.data.IndicadoresRepository
import com.artesanoskuad.indicadores.data.model.Indicador
import com.artesanoskuad.indicadores.data.model.Indicadores

class IndicadoresViewModel(
    private val repository: IndicadoresRepository
) : ViewModel() {

    private val mutableLiveData = MutableLiveData<Indicadores>()
    fun state() : LiveData<Indicadores> = mutableLiveData


}