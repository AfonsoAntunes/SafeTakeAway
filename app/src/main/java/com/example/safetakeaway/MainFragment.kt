package com.example.safetakeaway

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safetakeaway.databinding.ListOrderFragmentBinding
import com.example.safetakeaway.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppData.fragment = this
        (activity as MainActivity).atualMenu = R.menu.main_menu

        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonOrder.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_listOrderFragment)
        }

        binding.buttonExit.setOnClickListener {
            System.exit(0)
        }
    }
}