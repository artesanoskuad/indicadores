package com.artesanoskuad.indicadores.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.artesanoskuad.indicadores.data.model.Indicador
import com.artesanoskuad.indicadores.databinding.ItemIndicadoresBinding

class IndicadoresAdapter(
    private val list: List<Indicador>
) : RecyclerView.Adapter<IndicadoresViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicadoresViewHolder {
        val binding = ItemIndicadoresBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IndicadoresViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IndicadoresViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}