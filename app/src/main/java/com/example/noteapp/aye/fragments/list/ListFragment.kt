package com.example.noteapp.aye.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.aye.MainViewModel
import com.example.noteapp.aye.R
import com.example.noteapp.aye.databinding.FragmentListBinding
import com.example.noteapp.aye.hideKeyBoard
import com.example.noteapp.aye.observeOnce
import com.example.noteapp.aye.room_db.note_table.Note
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class ListFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentListBinding
    private val adapter: ListAdapter by lazy { ListAdapter() }
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.mViewModel = viewModel

        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = //LinearLayoutManager(requireContext())
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
        swipeToDelete(recyclerView)

        viewModel.fetchAllData().observe(viewLifecycleOwner) {
            viewModel.checkIfDatabaseEmpty(it)
            adapter.setData(it)
        }

        hideKeyBoard(requireActivity())

//        viewModel.emptyDatabase.observe(viewLifecycleOwner) {
//            showEmptyDatabase(it)
//        }

        setHasOptionsMenu(true)
        return binding.root
    }

//    private fun showEmptyDatabase(emptyDatabase: Boolean) {
//        if (emptyDatabase) {
//            binding.noDataImageView.visibility = View.VISIBLE
//            binding.noDataTextView.visibility = View.VISIBLE
//        } else {
//            binding.noDataImageView.visibility = View.INVISIBLE
//            binding.noDataTextView.visibility = View.INVISIBLE
//
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        val search: MenuItem = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView

        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all) {
            confirmItemRemoval()
        }

        if(item.itemId == R.id.menu_priority_high){
            viewModel.sortByHighPriority().observe(viewLifecycleOwner){
                adapter.setData(it)
            }
        }

        if(item.itemId == R.id.menu_priority_low){
            viewModel.sortByLowPriority().observe(viewLifecycleOwner){
                adapter.setData(it)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDelete = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = adapter.dataList[viewHolder.adapterPosition]
                //Delete Item
                viewModel.deleteData(itemToDelete)
              //  adapter.notifyItemRemoved(viewHolder.adapterPosition)
                //Restore Deleted Item
                restoreDeletedData(viewHolder.itemView, itemToDelete, viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(view: View, deletedItem: Note, position: Int) {
        val snackbar = Snackbar.make(
            view, "Deleted ${deletedItem.title}",
            Snackbar.LENGTH_LONG
        )

        snackbar.setAction("Undo") {
            viewModel.insertRecord(deletedItem)
        }
        snackbar.show()
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteAll()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {
        var searchQuery: String = query
        searchQuery = "%$query%"

        viewModel.searchDatabase(searchQuery).observeOnce(viewLifecycleOwner) {
            it?.let {
                adapter.setData(it)
            }
        }
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }
}