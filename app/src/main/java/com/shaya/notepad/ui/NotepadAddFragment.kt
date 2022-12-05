package com.shaya.notepad.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shaya.notepad.BaseApplication
import com.shaya.notepad.R
import com.shaya.notepad.databinding.FragmentNotepadAddBinding
import com.shaya.notepad.databinding.FragmentNotepadListBinding
import com.shaya.notepad.model.Item
import com.shaya.notepad.ui.viewmodel.ItemViewModel
import com.shaya.notepad.ui.viewmodel.ItemViewModelFactory


class NotepadAddFragment : Fragment() {

    private val navigationArgs: NotepadAddFragmentArgs by navArgs()

    private var _binding: FragmentNotepadAddBinding? = null
    private val binding get() = _binding!!

    lateinit var item: Item

    private val viewModel: ItemViewModel by activityViewModels{
        ItemViewModelFactory((activity?.application as BaseApplication).database.itemDao())
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotepadAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.id
        if (id>0){
            viewModel.retrieveItem(id).observe(this.viewLifecycleOwner){selectedItem ->
                item = selectedItem
                bindItem(item)
        }
    } else {
            binding.saveFab.setOnClickListener {
                addItem()
            }
        }
}

    private fun bindItem(item: Item)
    {
    binding.apply{
    titleInput.setText(item.title)
    descriptionInput.setText(item.description)
    saveFab.setOnClickListener { updateItem() }
    }
    }

    private fun updateItem(){
        viewModel.updateItem(
            id = navigationArgs.id,
            title = binding.titleInput.text.toString(),
            description = binding.descriptionInput.text.toString()
        )
        findNavController().navigate(
            R.id.action_notepadAddFragment_to_notepadListFragment
        )
    }


    private fun addItem(){
        viewModel.addItem(
            binding.titleInput.text.toString(),
            binding.descriptionInput.text.toString()
        )
        findNavController().navigate(R.id.action_notepadAddFragment_to_notepadListFragment)
    }




}