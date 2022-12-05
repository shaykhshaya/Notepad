package com.shaya.notepad.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shaya.notepad.BaseApplication
import com.shaya.notepad.R
import com.shaya.notepad.databinding.FragmentNotepadListBinding
import com.shaya.notepad.model.Item
import com.shaya.notepad.ui.adapter.ItemListAdapter
import com.shaya.notepad.ui.viewmodel.ItemViewModel
import com.shaya.notepad.ui.viewmodel.ItemViewModelFactory


class NotepadListFragment : Fragment() {

    private val viewModel: ItemViewModel by activityViewModels {
        ItemViewModelFactory((activity?.application as BaseApplication).database.itemDao())
    }

    private var _binding: FragmentNotepadListBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotepadListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ItemListAdapter(
            onClickListener = {
                val action = NotepadListFragmentDirections.actionNotepadListFragmentToNotepadDetailFragment(it.id)
                findNavController().navigate(action)
            }
        )

        viewModel.allItems.observe(this.viewLifecycleOwner){
            it.let {
                adapter.submitList(it)
            }
        }

        binding.apply {
            recyclerView.adapter = adapter
            addFab.setOnClickListener {
                val action = NotepadListFragmentDirections.actionNotepadListFragmentToNotepadAddFragment(getString(R.string.add_fragment_title), 0)
                findNavController().navigate(action)
        }
        }
    }


}