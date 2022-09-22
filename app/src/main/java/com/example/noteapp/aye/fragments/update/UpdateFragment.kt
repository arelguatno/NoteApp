package com.example.noteapp.aye.fragments.update

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.noteapp.aye.R
import com.example.noteapp.aye.data.Priority
import com.example.noteapp.aye.databinding.FragmentUpdateBinding


class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding
    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)


        binding.currentTitleEt.setText(args.currentitem.title)
        binding.currentDescriptionEt.setText(args.currentitem.description)
        binding.currentPrioritiesSpinner.setSelection(parsePriority(args.currentitem.priority!!))
        binding.currentPrioritiesSpinner.onItemSelectedListener = getColor()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu,menu)
    }

    private fun parsePriority(priority: Priority): Int{
        return when(priority){
            Priority.High -> 0
            Priority.Medium -> 1
            Priority.Low -> 2
        }
    }

    private fun getColor(): AdapterView.OnItemSelectedListener {
        val listener: AdapterView.OnItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    0 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.red)) }
                    1 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow)) }
                    2 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.green)) }
                }
            }
        }

        return listener
    }

}