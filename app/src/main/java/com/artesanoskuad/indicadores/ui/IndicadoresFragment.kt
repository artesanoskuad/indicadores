package com.artesanoskuad.indicadores.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.artesanoskuad.indicadores.data.IndicadoresRepository
import com.artesanoskuad.indicadores.data.model.Indicador
import com.artesanoskuad.indicadores.data.remote.RetrofitClient
import com.artesanoskuad.indicadores.databinding.FragmentIndicadoresBinding
import com.artesanoskuad.indicadores.presentation.IndicadoresViewModel
import com.artesanoskuad.indicadores.presentation.IndicadoresViewModelFactory
import com.artesanoskuad.indicadores.presentation.IndicadoresViewState
import com.artesanoskuad.indicadores.presentation.IndicadoresViewState.ServerErrorIndicadoresViewState
import com.artesanoskuad.indicadores.presentation.IndicadoresViewState.ShowAllIndicadoresViewState

class IndicadoresFragment : Fragment() {

    private var rawBinding: FragmentIndicadoresBinding? = null
    private val binding get() = rawBinding!!

    private val indicadoresApi = RetrofitClient.getIndicadoresApi()
    private val repository = IndicadoresRepository(indicadoresApi)
    private val indicadoresViewModelFactory = IndicadoresViewModelFactory(repository)
    private val indicadoresViewModel: IndicadoresViewModel by activityViewModels {
        indicadoresViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rawBinding = FragmentIndicadoresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        binding.rvIndicadores.layoutManager = LinearLayoutManager(context)
    }

    private fun setupViewModel() {
        indicadoresViewModel.state().observe(viewLifecycleOwner) {
            it?.let { safeSate ->
                handleUI(safeSate)
            }
        }

        indicadoresViewModel.getIndicadores()
    }

    private fun handleUI(safeSate: IndicadoresViewState) {
        when(safeSate){
            is ShowAllIndicadoresViewState -> showIndicadores(safeSate.version, safeSate.author, safeSate.fecha, safeSate.indicadores)
            is ServerErrorIndicadoresViewState -> showServerError()
        }
    }

    private fun showIndicadores(
        version: String,
        author: String,
        fecha: String,
        indicadores: List<Indicador>
    ) {
        with(binding){
            tvVersion.text = version
            tvAuthor.text = author
            tvFecha.text = fecha
            rvIndicadores.adapter = IndicadoresAdapter(indicadores)
        }
    }

    private fun showServerError() {
        Toast.makeText(context, "Error en el servidor, vuelve a intentar nuevamente", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rawBinding = null
    }
}