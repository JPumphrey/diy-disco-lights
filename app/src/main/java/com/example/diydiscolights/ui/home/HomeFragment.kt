package com.example.diydiscolights.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import com.example.diydiscolights.BuildConfig
import com.example.diydiscolights.R
import com.example.diydiscolights.model.FlashController
import com.example.diydiscolights.model.SetupState

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var flashController: FlashController

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val flashSwitch: Switch = root.findViewById(R.id.switch_flash)
        homeViewModel.flashing.observe(viewLifecycleOwner, Observer {
            flashSwitch.isChecked = it
        })

        val bpmBar: SeekBar = root.findViewById<SeekBar>(R.id.seek_bpm).also { bpmBar ->
            homeViewModel.bpm.observe(viewLifecycleOwner, Observer {
                bpmBar.progress = it / 5
            })
            bpmBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        homeViewModel.bpm.value = progress * 5
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}

            })
        }

        root.findViewById<TextView>(R.id.text_bpm).also { tv ->
            homeViewModel.bpm.observe(viewLifecycleOwner, Observer { tv.text = "BPM: $it" })
        }

        flashController = FlashController(
            BuildConfig.defaultip,
            BuildConfig.defaultusername,
            SetupState(listOf(1, 2)),
            homeViewModel.viewModelScope)

        flashSwitch.setOnCheckedChangeListener { _, isChecked ->
            homeViewModel.flashing.value = isChecked
        }

        flashController.observe(homeViewModel, viewLifecycleOwner)

        return root
    }
}
