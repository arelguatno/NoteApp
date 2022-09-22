package com.example.noteapp.aye.fragments.add

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.text.TextUtils
import android.view.*
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.noteapp.aye.MainViewModel
import com.example.noteapp.aye.R
import com.example.noteapp.aye.data.Priority
import com.example.noteapp.aye.databinding.FragmentAddBinding


class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        binding.prioritiesSpinner.onItemSelectedListener = getColor()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val mTitle = binding.titleEt.text.toString()
        val mPriority = binding.prioritiesSpinner.selectedItem.toString()
        val mDescription = binding.descriptionEt.text.toString()

        val validation = viewModel.verifyDataUser(mTitle, mDescription)
        if (validation) {

            val newData = com.example.noteapp.aye.room_db.note_table.Note(
                title = mTitle,
                priority = viewModel.parsePriority(mPriority),
                description = mDescription
            )
            viewModel.insertRecord(newData)
            findNavController().navigateUp()
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