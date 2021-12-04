package com.artesanoskuad.indicadores.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artesanoskuad.indicadores.data.IndicadoresRepository
import com.artesanoskuad.indicadores.data.model.Indicador
import com.artesanoskuad.indicadores.data.model.Indicadores
import com.artesanoskuad.indicadores.presentation.IndicadoresViewState.ServerErrorIndicadoresViewState
import com.artesanoskuad.indicadores.presentation.IndicadoresViewState.ShowAllIndicadoresViewState
import kotlinx.coroutines.launch

class IndicadoresViewModel(
    private val repository: IndicadoresRepository
) : ViewModel() {

    private val mutableLiveData = MutableLiveData<IndicadoresViewState>()
    fun state(): LiveData<IndicadoresViewState> = mutableLiveData

    fun getIndicadores() {
        viewModelScope.launch {
            val indicadores = repository.getAllIndicadores()
            handleState(indicadores)
        }
    }

    private fun handleState(indicadores: Indicadores?) {
        if (indicadores != null) {
            mutableLiveData.postValue(
                ShowAllIndicadoresViewState(
                    indicadores.version,
                    indicadores.autor,
                    indicadores.fecha.substring(0,10),
                    getListIndicadores(indicadores)
                )
            )
        } else {
            mutableLiveData.postValue(ServerErrorIndicadoresViewState)
        }
    }

    private fun getListIndicadores(indicadores: Indicadores): List<Indicador> {
        with(indicadores) {
            return listOf(
                uf,
                ivp,
                dolar,
                dolarIntercambio,
                euro,
                ipc,
                utm,
                imacec,
                tpm,
                libraCobre,
                tasaDesempleo,
                bitcoin
            ).map {
                getIndicadorFechaFormateada(it)
            }
        }
    }

    private fun getIndicadorFechaFormateada(indicador: Indicador): Indicador {
        with(indicador) {
            return Indicador(
                codigo,
                nombre,
                unidadMedida,
                fecha.substring(0, 10),
                valor
            )
        }
    }
}
