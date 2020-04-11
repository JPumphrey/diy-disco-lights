package com.example.diydiscolights.ui.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.diydiscolights.R

class SetupFragment : Fragment() {

    private lateinit var setupViewModel: SetupViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setupViewModel =
                ViewModelProviders.of(this).get(SetupViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_setup, container, false)
        val textView: TextView = root.findViewById(R.id.text_setup)
        setupViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
