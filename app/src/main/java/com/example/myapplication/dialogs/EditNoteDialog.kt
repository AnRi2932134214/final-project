package com.example.myapplication.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.myapplication.R
import com.example.myapplication.data.NotesProvider
import com.example.myapplication.model.Note

class EditNoteDialog(context: Context, private val note: Note) : Dialog(context) {

    private lateinit var titleEditText: EditText
    private lateinit var bodyEditText: EditText
    private lateinit var cancelButton: Button
    private lateinit var saveButton: Button
    private lateinit var favoritesButton: Button

    init {
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_edit_note)

        init()

        setListeners()

    }

    private fun init() {
        titleEditText = findViewById(R.id.titleEditText)
        bodyEditText = findViewById(R.id.bodyEditText)
        cancelButton = findViewById(R.id.cancelButton)
        saveButton = findViewById(R.id.saveButton)
        favoritesButton = findViewById(R.id.favoritesButton)

        titleEditText.setText(note.title)
        bodyEditText.setText(note.body)

        note.favorite?.let {
            favoritesButton.text = when (it) {
                true -> "Unlike"
                false -> "Like"
            }
        }

    }

    private fun setListeners() {
        cancelButton.setOnClickListener {
            this.dismiss()
        }
        saveButton.setOnClickListener {
            NotesProvider.editNote(
                note,
                mapOf(
                    "title" to titleEditText.text.toString(),
                    "body" to bodyEditText.text.toString()
                )
            )
            this.dismiss()
        }
        favoritesButton.setOnClickListener {
            NotesProvider.setNoteFavorite(
                note = note,
                isFavorite = try { !note.favorite!! } catch (ex: NullPointerException) { false }
            )
            this.dismiss()
        }
    }

}