package com.shaya.notepad.ui

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.shaya.notepad.BaseApplication
import com.shaya.notepad.R
import com.shaya.notepad.databinding.FragmentNotepadDetailBinding
import com.shaya.notepad.model.Item
import com.shaya.notepad.ui.viewmodel.ItemViewModel
import com.shaya.notepad.ui.viewmodel.ItemViewModelFactory


class NotepadDetailFragment : Fragment() {

    private lateinit var item: Item

    private val navigationArgs: NotepadDetailFragmentArgs by navArgs()

    private var _binding: FragmentNotepadDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ItemViewModel by activityViewModels{
        ItemViewModelFactory((activity?.application as BaseApplication).database.itemDao())
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
    _binding = FragmentNotepadDetailBinding.inflate(inflater, container, false)
    return binding.root
    //return inflater.inflate(R.layout.fragment_notepad_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val id = navigationArgs.id
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner){selectedItem ->
            item = selectedItem
            bindItem(item)
        }
    }

    private fun bindItem(item:Item){
        binding.apply{
            titleInput.setText(item.title)
            descriptionInput.setText(item.description)
            descriptionInput.movementMethod = ScrollingMovementMethod()
            deleteFab.setOnClickListener { showConfirmationDialog() }
            updateFab.setOnClickListener {updateItem()}
                /*val action = NotepadDetailFragmentDirections.actionNotepadDetailFragmentToNotepadAddFragment(getString(R.string.edit_fragment_title))
                findNavController().navigate(action)*/

        }
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteItem()
            }
            .show()
    }

    private fun deleteItem(){
        viewModel.deleteItem(item)
        findNavController().navigateUp()
    }

    private fun updateItem(){
        val action = NotepadDetailFragmentDirections.actionNotepadDetailFragmentToNotepadAddFragment(getString(R.string.edit_fragment_title), item.id)
        this.findNavController().navigate(action)
    }



}