package com.example.pomodoro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.BaseFragment
import com.example.pomodoro.databinding.FragmentHistoryBinding

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    private val expandableAdapter = HistoryAdapter()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHistoryBinding {
        return FragmentHistoryBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.expandableListView.apply {
            setAdapter(expandableAdapter)
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val headers = listOf("header 1","header 2","header 3")
        expandableAdapter.headers = headers
        val map = mutableMapOf<String, List<String>>()
        headers.forEach{
            map[it] = listOf("asd","asd","asd")
        }
        expandableAdapter.items =  map
    }

}