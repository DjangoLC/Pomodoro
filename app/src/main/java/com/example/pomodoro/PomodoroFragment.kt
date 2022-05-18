package com.example.pomodoro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.BaseFragment
import com.example.pomodoro.databinding.FragmentPomodoroBinding

class PomodoroFragment : BaseFragment<FragmentPomodoroBinding>() {

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentPomodoroBinding {
        return FragmentPomodoroBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //start the count down when click in button
        binding.btnPlay.setOnClickListener {
            binding.fancyTimer.start()
        }

        binding.btnPause.setOnClickListener {
            binding.fancyTimer.pause()
        }

        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }

        binding.btnHistory.setOnClickListener {
            findNavController().navigate(R.id.historyFragment)
        }
        binding.fancyTimer.setTime(60000)
    }

}