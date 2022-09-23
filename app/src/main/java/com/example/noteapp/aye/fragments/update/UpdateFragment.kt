package com.example.noteapp.aye.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.noteapp.aye.MainViewModel
import com.example.noteapp.aye.R
import com.example.noteapp.aye.data.Priority
import com.example.noteapp.aye.databinding.FragmentUpdateBinding
import com.example.noteapp.aye.room_db.note_table.Note


class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding
    private val args by navArgs<UpdateFragmentArgs>()
    private val viewModel: MainViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)


        binding.currentTitleEt.setText(args.currentitem.title)
        binding.currentDescriptionEt.setText(args.currentitem.description)
        binding.currentPrioritiesSpinner.setSelection(viewModel.parsePriorityToInt(args.currentitem.priority!!))
        binding.currentPrioritiesSpinner.onItemSelectedListener = getColor()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    private fun getColor(): AdapterView.OnItemSelectedListener {
        val listener: AdapterView.OnItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        (parent?.getChildAt(0) as TextView).setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red
                            )
                        )
                    }
                    1 -> {
                        (parent?.getChildAt(0) as TextView).setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.yellow
                            )
                        )
                    }
                    2 -> {
                        (parent?.getChildAt(0) as TextView).setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.green
                            )
                        )
                    }
                }
            }
        }

        return listener
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_save) {
            updateItem()
        } else if (item.itemId == R.id.menu_delete) {
            confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteData(args.currentitem)
            findNavController().navigateUp()
        }
        builder.setNegativeButton("No"){_,_->}
        builder.setTitle("Delete ${args.currentitem.title}")
        builder.setMessage("Are you sure you want to delete?")
        builder.show()
    }

    private fun updateItem() {
        val title = binding.currentTitleEt.text.toString()
        val description = binding.currentDescriptionEt.text.toString()
        val getPriority = binding.currentPrioritiesSpinner.selectedItem.toString()

        val validation = viewModel.verifyDataUser(title, description)
        if (validation) {

            val updateItem = args.currentitem
            updateItem.title = title
            updateItem.description = description
            updateItem.priority = viewModel.parsePriority(getPriority)

            viewModel.updateData(updateItem)
            Toast.makeText(requireContext(), "Yehey", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        } else {

        }
    }

}