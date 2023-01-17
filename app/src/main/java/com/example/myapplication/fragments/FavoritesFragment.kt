package com.example.myapplication.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapters.NoteAdapter
import com.example.myapplication.data.NotesProvider
import com.example.myapplication.dialogs.EditNoteDialog
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private lateinit var favoritesRecyclerView: RecyclerView
    private val noteAdapter = NoteAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        setListeners()

        setObservers()

    }

    private fun init() {
        favoritesRecyclerView = requireView().findViewById(R.id.favoritesRecyclerView)
        favoritesRecyclerView.adapter = noteAdapter
    }

    private fun setListeners() {
        noteAdapter.onItemClick = { note ->
            EditNoteDialog(requireContext(), note).show()
        }
        noteAdapter.onItemLongClick = { note ->
            AlertDialog.Builder(requireContext())
                .setTitle("Delete selected item?")
                .setMessage("\n".plus(note.toString()))
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
            noteAdapter.submitList(
                notes.filter {
                    it.favorite ?: false
                }.sortedBy {
                    it.createdAt
                }
            )
        }
    }

}