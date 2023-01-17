package com.example.myapplication.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapters.NoteAdapter
import com.example.myapplication.data.NotesProvider
import com.example.myapplication.dialogs.AddNoteDialog
import com.example.myapplication.dialogs.EditNoteDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class NotesFragment : Fragment(R.layout.fragment_notes) {

    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var addButton: MaterialButton
    private val noteAdapter = NoteAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        setListeners()

        setObservers()

    }

    private fun init() {
        notesRecyclerView = requireView().findViewById(R.id.notesRecyclerView)
        addButton = requireView().findViewById(R.id.addButton)
        notesRecyclerView.adapter = noteAdapter
    }

    private fun setListeners() {
        addButton.setOnClickListener {
            AddNoteDialog(requireContext()).show()
        }
        noteAdapter.onItemClick = { note ->
            EditNoteDialog(requireContext(), note).show()
        }
        noteAdapter.onItemLongClick = { note ->
            AlertDialog.Builder(requireContext())
                .setTitle("Delete selected item?")
                .setMessage(note.toString())
                .setPositiveButton("Yes") { dialog, _ ->
                    NotesProvider.deleteNote(note)
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }
    }

    private fun setObservers() {
        NotesProvider.observeAllNotes { notes ->
            noteAdapter.submitList(notes.sortedBy { it.createdAt })
        }
    }

}