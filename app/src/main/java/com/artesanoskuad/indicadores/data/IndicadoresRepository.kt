package com.artesanoskuad.indicadores.data

import com.artesanoskuad.indicadores.data.model.Indicador
import com.artesanoskuad.indicadores.data.model.Indicadores
import com.artesanoskuad.indicadores.data.remote.IndicadoresApi
import java.lang.RuntimeException

class IndicadoresRepository(
    private val api: IndicadoresApi
) {
    suspend fun getAllIndicadores(): Indicadores? {
        val indicadores = api.obtenerIndicadores()
        if(indicadores.isSuccessful){
            return indicadores.body()
        } else {
            throw RuntimeException("No se obtuvieron indicadores")
        }
    }
}