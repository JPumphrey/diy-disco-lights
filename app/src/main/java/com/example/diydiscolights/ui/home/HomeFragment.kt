package com.example.diydiscolights.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import com.example.diydiscolights.BuildConfig
import com.example.diydiscolights.R
import com.example.diydiscolights.model.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var model: Model

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val scope = homeViewModel.viewModelScope

        model = Model(BuildConfig.defaultip, BuildConfig.defaultusername)
        val switchOn: Button = root.findViewById(R.id.switchOn)
        val switchOff: Button = root.findViewById(R.id.switchOff)

        switchOn.setOnClickListener {
            scope.launch(Dispatchers.IO) {
                try {
                    model.switchOn(1)
                } catch (t: Throwable) {
                    Log.e("jmp", "got error ", t)
                }
            }
        }

        switchOff.setOnClickListener {
            scope.async(Dispatchers.IO) {
                try {
                    model.switchOff(1)
                } catch (t: Throwable) {
                    Log.e("jmp", "got error ", t)
                }
            }
        }

        return root
    }
}
