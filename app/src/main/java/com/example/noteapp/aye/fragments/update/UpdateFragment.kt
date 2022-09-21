package com.example.noteapp.aye.fragments.update

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.noteapp.aye.R
import com.example.noteapp.aye.databinding.FragmentUpdateBinding


class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu,menu)
    }

}